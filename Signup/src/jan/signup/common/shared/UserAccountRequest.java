package jan.signup.common.shared;

import java.io.Serializable;

public class UserAccountRequest implements Serializable{
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String email;
	private String unit;
	
	private boolean alreadyCreated;
	private String ldapParent;
	private String emailNew;
	private String domain;
	
	public UserAccountRequest(){
		super();
	}

	
	
	public boolean isAlreadyCreated() {
		return alreadyCreated;
	}



	public void setAlreadyCreated(boolean alreadyCreated) {
		this.alreadyCreated = alreadyCreated;
	}



	public String getLdapParent() {
		return ldapParent;
	}



	public void setLdapParent(String ldapParent) {
		this.ldapParent = ldapParent;
	}



	public String getEmailNew() {
		return emailNew;
	}



	public void setEmailNew(String emailNew) {
		this.emailNew = emailNew;
	}




	public UserAccountRequest(String firstName, String lastName, String email,
			String unit, boolean alreadyCreated, String ldapParent,
			String emailNew,String domain) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.unit = unit;
		this.alreadyCreated = alreadyCreated;
		this.ldapParent = ldapParent;
		this.emailNew = emailNew;
		this.setDomain(domain);
	}



	public UserAccountRequest(String firstName, String lastName,
			String email, String unit) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.unit = unit;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "UserAccountRequest [firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", unit=" + unit + "]";
	}



	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}