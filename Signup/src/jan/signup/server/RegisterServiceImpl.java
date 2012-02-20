package jan.signup.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import jan.signup.common.client.rpc.RegisterService;
import jan.signup.common.shared.CreateType;
import jan.signup.common.shared.UserAccountRequest;
import jan.signup.server.services.db.StatusType;
import jan.signup.server.services.db.UAREntity;
import jan.signup.server.services.db.UserAccountRequestService;
import jan.signup.server.services.db.UserAccountRequestServiceImpl;
import jan.signup.server.services.db.mail.MailService;
import jan.signup.server.services.db.mail.MailServiceImpl;
import jan.signup.server.services.ldap.LdapAccountService;
import jan.signup.server.services.ldap.LdapAccountServiceImpl;
import jan.signup.server.services.ldap.LdapUser;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RegisterServiceImpl extends RemoteServiceServlet implements
		RegisterService {

	private UserAccountRequestService dbService;
	private LdapAccountService ldapService;
	private Logger log = Logger.getLogger(RegisterServiceImpl.class);
	private MailService mailService;
	private AppProps props;
	
	public RegisterServiceImpl(){
		dbService = new UserAccountRequestServiceImpl();
		ldapService = new LdapAccountServiceImpl();
		mailService = new MailServiceImpl();
		props = AppProps.getDefaultInstance();
	}
	
	
	@Override
	public void register(UserAccountRequest req) {
		log.info("register("+req+")");
		UAREntity user = dbService.createUser(req.getFirstName(), req.getLastName(), req.getEmail(), req.getUnit());
		mailService.sendMailConfirm(user, createTokenUrl(props.getMailConfirmUrl(),user.getToken()));
		log.info("register('"+req+"') - Token: " + user.getToken());
		
	}

	@Override
	public boolean confirmResetPassword(String token, String newPassword) {
		log.warn("confirmResetPassword(token="+token+")");
		UAREntity res = dbService.getByTokenAndState(token, StatusType.PASSWORD_RESET);
		if(res == null){
			log.error("TOKEN NOT FOULD ! - confirmResetPassword(token="+token+")");
			return false;
		}
		try{
			LdapUser ldapUser = ldapService.getByEmail(res.getEmail());
			ldapUser.setPassword(newPassword);
			ldapService.setPassword(ldapUser);
			dbService.setState(res, StatusType.ADMIN_CONFIRMED);
			return true;
			
		}catch(Exception e){
			log.error("confirmResetPassword('"+token+"')",e);
			return false;
		}
	}


	@Override
	public boolean confirmEmail(final String token) {
		UAREntity req = dbService.getByTokenAndState(token, StatusType.NEW);
		if(req == null){
			log.error("TOKEN NOT FOUND - confirmEmail(token='"+token+"')");
			return false;
		}
		dbService.changeToken(req);
		log.info("confirmEmail('"+token + "') - New Token: " + req.getToken());
		dbService.setState(req, StatusType.EMAIL_CONFIRMED);
		mailService.sendAdminConfirm(req, createTokenUrl(props.getAdminConfirmUrl(),req.getToken()));
		return true;
	}

	@Override
	public UserAccountRequest loadUserAccountRequest(String token) {
		UAREntity res = dbService.getByToken(token);
		if(res == null){
			log.error("TOKEN NOT FOUND - loadUserAccountRequest('"+token + "')");
			return null;
		}
		String firstName = res.getFirstName();
		String lastName = res.getLastName();
		String domain = res.getDomain();
		String email = res.getEmail();
		String unit = res.getUnit();
		String ldapParent = res.getLdapParent();
		String emailNew = res.getEmailNew();
		if(emailNew == null){
			emailNew = ldapService.buildCn(firstName, lastName);
		}
		
		boolean alreadyCreated = 
				(res.getWorkflowStatus() == StatusType.ADMIN_CONFIRMED) || (res.getWorkflowStatus() == StatusType.ADMIN_REJECT);
		UserAccountRequest req = new UserAccountRequest(firstName, lastName, email, unit, alreadyCreated, ldapParent, emailNew, domain);
		return req;
	}


	@Override
	public void confirmUser(String token, UserAccountRequest request, CreateType createType,String parentDn) {
		UAREntity res = dbService.getByTokenAndState(token, StatusType.EMAIL_CONFIRMED);
		if(res == null){
			log.error("TOKEN NOT FOUND OR WRONG STATUS - confirmUser('"+token+"'," + request + "," + createType);
			return;
		}
		res.setDomain(request.getDomain());
		res.setLdapParent(request.getLdapParent());
		res.setEmailNew(request.getEmailNew());
		dbService.update(res);
		
		if(createType != CreateType.REJECT){
			LdapUser user = new LdapUser();
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setForwardAddress(request.getEmail());
			user.setMailAdress(request.getEmailNew() + "@" + request.getDomain());
			user.setPassword(generatePassword());
			user.setPrivateMailAdress(request.getEmail());
			if(createType == CreateType.ACCEPT){
				ldapService.createUser(user,parentDn);
				mailService.sendCreated(user, user.getPassword());
			}else{
				ldapService.createImapUser(user,parentDn);
				mailService.sendCreatedImap(user, user.getPassword());
			}
			dbService.setState(res, StatusType.ADMIN_CONFIRMED);
		}else{
			dbService.setState(res,StatusType.ADMIN_REJECT);
		}
	}

	@Override
	public boolean resetPassword(String email) {
		LdapUser ldapUser = ldapService.getByEmail(email);
		
		if(ldapUser != null){
			UAREntity dbUser = dbService.getByEmail(email);
			//Ldap-User not in staging
			if(dbUser == null){
				dbUser = dbService.createUser(ldapUser.getFirstName(), ldapUser.getLastName(), ldapUser.getForwardAddress(), "n/a");
			}
			log.warn("resetPassword('"+email+"')");	
			dbService.setState(dbUser, StatusType.PASSWORD_RESET);
			dbService.changeToken(dbUser);
			mailService.sendResetPasswordConfirm(ldapUser, createTokenUrl(props.getResetConfirmUrl(), dbUser.getToken()));
			return true;
		}else{
			return false;
		}
	}
	

	private String createTokenUrl(String base, String token) {
		try {
			String tokenEnc = URLEncoder.encode(token, "UTF-8");
			return base + "&token=" + tokenEnc; 
		} catch (UnsupportedEncodingException e) {
			log.error("Error encoding tokens:",e);
			throw new RuntimeException(e);
		}
	}
	
	private String generatePassword(){
		return RandomStringUtils.random(10,"abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789");
	}


	@Override
	public List<String> getValidParents() {
		return ldapService.getValidParents();
	}


	@Override
	public List<String> getValidDomains() {
		String validDomains = props.getValidDomains();
		List<String> result = Arrays.asList(validDomains.split(", "));
		return result;
	} 

}
