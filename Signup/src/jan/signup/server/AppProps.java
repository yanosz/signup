package jan.signup.server;

import java.util.Properties;

import org.apache.log4j.Logger;

public class AppProps {

	private static Logger logger = Logger.getLogger(AppProps.class);
	
	Properties props;
	
	private static Properties defaultProperties;
	
	public AppProps(Properties props){
		this.props = props;
	}
	
	public String getMailConfirmSubject() {
		return props.getProperty("mail.mailConfirmSubject","");
	}

	public synchronized static AppProps getDefaultInstance(){
		if(defaultProperties == null){
			defaultProperties = new Properties();
			try {
				defaultProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
			} catch (Exception e) {
				logger.warn("Unable to load app.properties",e);
			}
			
		}
		return new AppProps(defaultProperties);
	}

	public String getMailConfirmTemplate() {
		return props.getProperty("mail.mailConfirmTemplate", "mailconfirm.vm");
	}

	public String getMailSmtpHost() {
		return props.getProperty("mail.smtpHost","thw-koeln.de");
	}

	public String getMailSmtpUser() {
		return props.getProperty("mail.smtpUser","");
	}

	public String getMailSmtpPwd() {
		return props.getProperty("mail.smtpPasswd","");
	}

	public String getMailSenderAddress() {
		return props.getProperty("mail.senderAddress","");
	}

	public String getAdminConfirmSubject() {
		return props.getProperty("mail.adminConfirmSubject");
	}

	public String getAdminConfirmTemplate() {
		return props.getProperty("mail.adminConfirmTemplate");
	}
	public String getAminAddr(){
		return props.getProperty("mail.adminAddr");
	}

	public String getAccountCreatedSubject() {
		return props.getProperty("mail.createdSubject");
	}

	public String getAccountCreatedTemplate() {
		return props.getProperty("mail.createdTemplate");
	}

	public String getResetPasswordSubject() {
		return props.getProperty("mail.resetPasswordSubject");
	}

	public String getResetPasswordTemplate() {
		return props.getProperty("mail.resetPasswordTemplate");
	}

	public String getMailConfirmUrl() {
		return props.getProperty("env.mailConfirmUrl","");
	}
	public String getResetConfirmUrl(){
		return props.getProperty("env.resetConfirmUrl");
	}

	public String getAdminConfirmUrl() {
		return props.getProperty("env.adminConfirmUrl");
	}

	public String getLdapUrl() {
		return props.getProperty("ldap.url");
	}

	public String getLdapBaseDN() {
		return props.getProperty("ldap.baseDn");
	}

	public String getLdapBindDN() {
		return props.getProperty("ldap.bindDn");
	}

	public String getBindPw() {
		return props.getProperty("ldap.bindPw");
	}

	public String getLdapIncubatorDn() {
		return props.getProperty("ldap.incubatorDn");
	}

	public String getLdapUidAttribute() {
		return props.getProperty("ldap.uidAttr");
	}

	public String getLdapGidNumber() {
		return props.getProperty("ldap.gidNumer");
	}

	public String getHomeDirBase() {
		return props.getProperty("env.homeDirBase");
	}

	public String getLdapFilterForValidParents() {
		return props.getProperty("ldap.validParentsFilter");
	}

	public String getAccountCreatedImageTemplate() {
		return props.getProperty("mail.createdImageTemplate");
		
	}

	public String getAccountCreatedImapSubject() {
		 return props.getProperty("mail.createdImapSubject");
	}

	public String getValidDomains() {
		return props.getProperty("env.validMaildomains","");
	}

		
}
