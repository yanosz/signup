package jan.signup.server.services.db.mail;

import jan.signup.server.services.db.UAREntity;
import jan.signup.server.services.ldap.LdapUser;

public interface MailService {
	public void sendMailConfirm(UAREntity user, String tokenUrl);
	public void sendAdminConfirm(UAREntity user, String tokenUrl);
	public void sendCreated(LdapUser user, String passwd);
	public void sendCreatedImap(LdapUser user, String passwd);
	public void sendResetPasswordConfirm(LdapUser user, String tokenUrl);
}
