package jan.signup.server.services.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class UAREntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String firstName;
	private String lastName;
	
	private String unit;
	private String ldapParent;
	private String emailNew;
	private String domain;
	

	@Column(unique=true)
	private String email;
	private String token;
	
	@Enumerated(EnumType.STRING)
	private StatusType workflowStatus;
	
	public UAREntity(){
		
	}

	public UAREntity(String firstName, String lastName, String unit,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.unit = unit;
		this.email = email;
		this.workflowStatus = StatusType.NEW;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StatusType getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(StatusType workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public Long getId() {
		return id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	
	

}
