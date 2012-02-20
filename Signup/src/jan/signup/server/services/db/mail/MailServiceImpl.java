package jan.signup.server.services.db.mail;

import jan.signup.server.AppProps;
import jan.signup.server.services.db.UAREntity;
import jan.signup.server.services.ldap.LdapUser;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class MailServiceImpl implements MailService {

	private final static String USER_ATTR = "user";
	private final static String TOKEN_URL_ATTR = "tokenUrl";
	private final static String PASSWD_ATTR = "passwd";
	
	private AppProps props = AppProps.getDefaultInstance();
	
	private String smtpHost;
	private String smtpUser;
	private String smtpPasswd;
	private String senderAddress;
	VelocityEngine ve;
	
	public MailServiceImpl() {
		this.smtpHost = props.getMailSmtpHost();
		this.smtpUser = props.getMailSmtpUser();
		this.smtpPasswd = props.getMailSmtpPwd();
		this.senderAddress = props.getMailSenderAddress();
		ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath"); 
        ve.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
	}

	
	
	@Override
	public void sendMailConfirm(UAREntity user, String tokenUrl) {
		String subject = props.getMailConfirmSubject();
		HashMap<String, Object> env = new HashMap<String, Object>();
		env.put(USER_ATTR, user);
		env.put(TOKEN_URL_ATTR,tokenUrl);
		String content = getContent(props.getMailConfirmTemplate(), env);
		sendMail(subject, content, user.getEmail());
	}

	@Override
	public void sendAdminConfirm(UAREntity user, String tokenUrl) {
		String subject = props.getAdminConfirmSubject();
		HashMap<String, Object> env = new HashMap<String, Object>();
		env.put(USER_ATTR, user);
		env.put(TOKEN_URL_ATTR, tokenUrl);
		String content = getContent(props.getAdminConfirmTemplate(), env);
		sendMail(subject, content, props.getAminAddr());
		
	}

	@Override
	public void sendResetPasswordConfirm(LdapUser user, String tokenUrl) {
		String subject = props.getResetPasswordSubject();
		HashMap<String, Object> env = new HashMap<String, Object>();
		env.put(USER_ATTR, user);
		env.put(TOKEN_URL_ATTR, tokenUrl);
		String content = getContent(props.getResetPasswordTemplate(), env);
		sendMail(subject, content, user.getForwardAddress());
	}
	
	@Override
	public void sendCreated(LdapUser user, String passwd) {
		String subject = props.getAccountCreatedSubject();
		HashMap<String, Object> env = new HashMap<String, Object>();
		env.put(USER_ATTR, user);
		env.put(PASSWD_ATTR, passwd);
		String content = getContent(props.getAccountCreatedTemplate(), env);
		sendMail(subject, content, user.getForwardAddress());
	}
	@Override
	public void sendCreatedImap(LdapUser user, String passwd) {
		String subject = props.getAccountCreatedImapSubject();
		HashMap<String, Object> env = new HashMap<String, Object>();
		env.put(USER_ATTR, user);
		env.put(PASSWD_ATTR, passwd);
		String content = getContent(props.getAccountCreatedImageTemplate(), env);
		sendMail(subject, content, user.getForwardAddress());
	}

	
	
	
	private String getContent(String template, HashMap<String, Object> env){
		VelocityContext context = new VelocityContext();
		if(env != null && env.keySet().size() > 1){
			for (String key : env.keySet()){
				context.put(key, env.get(key));
			}
		}
		StringWriter writer = new StringWriter();
		ve.mergeTemplate(template, "UTF-8",context, writer);
		return writer.toString();
	}
	
	
	
	private void sendMail(String subject, String content, String destination){
		new MailSendingThread(subject, content, destination).start();
	}
	class MailSendingThread extends Thread{
		private String subject;
		private String content;
		private String destination;
		private Logger myLogger;
		public MailSendingThread(String subject, String content, String destination){
			this.subject = subject;
			this.content = content;
			this.destination = destination;
			setDaemon(true);
			myLogger = Logger.getLogger(MailSendingThread.class);
		}
		
		public void run(){
			try {
				InternetAddress from = new InternetAddress(senderAddress);
				InternetAddress to = new InternetAddress(destination);
				Authenticator authenticator = new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpUser, smtpPasswd);
					}
				};
				Properties props = new Properties();
				props.setProperty("mail.smtp.auth", "true");
				props.setProperty("mail.smtp.host",  smtpHost);
				props.setProperty("mail.smtp.submitter", smtpUser);
				
				Session session = Session.getInstance(props,authenticator);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(from);
				message.setSubject(subject);
				message.addRecipient(RecipientType.TO, to);
				
				Multipart multipart = new MimeMultipart("alternative");
				
				// Create your text message part
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText(content);
				multipart.addBodyPart(messageBodyPart);

				// Add the text part to the multipart
				message.setContent(multipart);
				Transport.send(message);
			} catch (Exception e) {
				myLogger.error("Error sending mail",e);
			}

		}
	}
	
}
