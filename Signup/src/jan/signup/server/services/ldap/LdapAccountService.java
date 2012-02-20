package jan.signup.server.services.ldap;

import java.util.List;

import javax.naming.NamingException;

public interface LdapAccountService {
	
	/**
	 * Changes the users's password the the specified one
	 * @param user
	 * @throws NamingException 
	 */
	public void setPassword(LdapUser user);

	/**
	 * Creates the user as an ordinary User
	 * @param user
	 * @throws NamingException 
	 */
	public void createUser(LdapUser user,String parentDn);
	
	/**
	 * Creates the user as an Imap-User
	 * @param user
	 */
	public void createImapUser(LdapUser user, String parentDn);
	
	/**
	 * Loads an Ldap-user by its CN
	 * @param cn
	 * @return
	 * @throws NamingException 
	 */
	public LdapUser getByCN(String cn);
	
	/**
	 * Lpads an ldap user by its email
	 * @throws NamingException 
	 */
	public LdapUser getByEmail(String email);
	
	/**
	 * Get all parents for creating accounts
	 * @return
	 */
	public List<String> getValidParents();
	
	public String buildCn(String firstName, String lastName);
	
}
