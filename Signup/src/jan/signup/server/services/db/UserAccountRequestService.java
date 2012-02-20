package jan.signup.server.services.db;

import javax.persistence.EntityManager;

public interface UserAccountRequestService {

	public UAREntity createUser(String firstName, String lastName, String email, String unit);
	
	public UAREntity getByTokenAndState(String token, StatusType state);
	
	public void changeToken(UAREntity req);
	
	public void setState(UAREntity req, StatusType state);

	public UAREntity getByToken(String token);

	public UAREntity getByEmail(String email);
	
	public EntityManager getEm();

	public void update(UAREntity res);
}
