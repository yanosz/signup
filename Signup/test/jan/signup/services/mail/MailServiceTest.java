package jan.signup.services.mail;

import jan.signup.server.services.db.UAREntity;
import jan.signup.server.services.db.mail.MailService;
import jan.signup.server.services.db.mail.MailServiceImpl;

import org.junit.Test;

public class MailServiceTest {
	
	MailService service = new MailServiceImpl();
	
	@Test
	public void sendMailConfirm(){
		UAREntity user = createUser();
		service.sendMailConfirm(user, "http://jluehr.de");
	}

	@Test
	public void sendAdminConfirm(){
		UAREntity user = createUser();
		service.sendAdminConfirm(user, "http://jluehr.de");
	}
	
//	@Test
//	public void sendWelcome(){
//		LdapUser user = new LdapUser("cn=jan.luehr,dc=de",null,"Jan","Lühr","jluehr@gmx.net","jan.luehr");
//		service.sendCreated(user, "123geheim");
//	}
//	
//	@Test
//	public void sendResetPasswd(){
//		LdapUser user = new LdapUser("cn=jan.luehr,dc=de",null,"Jan","Lühr","jluehr@gmx.net","jan.luehr");
//		service.sendResetPasswordConfirm(user, "http://jluehr.de?token=123");
//			
//	}
	
	private UAREntity createUser() {
		UAREntity user = new UAREntity();
		user.setFirstName("Jan");
		user.setLastName("Lühr");
		user.setUnit("Zugtrupp");
		user.setEmail("jluehr@gmx.net");
		return user;
	}

}
