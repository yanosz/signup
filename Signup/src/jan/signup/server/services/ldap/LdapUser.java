package jan.signup.server.services.ldap;

import java.io.Serializable;

/**
 * Basic class for all ldap-Request. Atttributes will be added when necessary.
 * @author jan
 *
 */
public class LdapUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private String dn;
	private String password;
	private String firstName;
	private String lastName;
	private String forwardAddress;
	private String mailAdress;
	private String privateMailAdress;
	
	private String cn;
	
	
	public LdapUser(String dn, String firstName, String lastName,
			String forwardAddress, String mailAdress, String privateMailAdress,
			String cn) {
		super();
		this.dn = dn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.forwardAddress = forwardAddress;
		this.mailAdress = mailAdress;
		this.privateMailAdress = privateMailAdress;
		this.cn = cn;
	}

	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public String getPrivateMailAdress() {
		return privateMailAdress;
	}

	public void setPrivateMailAdress(String privateMailAdress) {
		this.privateMailAdress = privateMailAdress;
	}


	public LdapUser(){}
	
	public LdapUser(String dn, String cn,String password) {
		super();
		this.dn = dn;
		this.cn = cn;
		this.password = password;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getForwardAddress() {
		return forwardAddress;
	}

	public void setForwardAddress(String forwardAddress) {
		this.forwardAddress = forwardAddress;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	
	
	
}
