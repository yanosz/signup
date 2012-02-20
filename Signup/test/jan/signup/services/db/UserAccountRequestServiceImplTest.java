package jan.signup.services.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import jan.signup.server.services.db.StatusType;
import jan.signup.server.services.db.UAREntity;
import jan.signup.server.services.db.UserAccountRequestService;
import jan.signup.server.services.db.UserAccountRequestServiceImpl;

import org.junit.Before;
import org.junit.Test;

public class UserAccountRequestServiceImplTest {

	
	private UserAccountRequestService service;
	
	@Before
	public void setUp(){
		service = new UserAccountRequestServiceImpl();
	}
	
	@Test
	public void testCreateUser() {
		UAREntity req = service.createUser("Jan", "Jans Nachname", "a@a.com", "Einheit");
		assertNotNull(req);
		String token = req.getToken();
		assertNotNull(token);
		assertNotNull(service.getByTokenAndState(token, StatusType.NEW));
		assertNotNull(service.getByEmail("a@a.com"));
		assertNotNull(service.getByToken(token));
		service.changeToken(req);
		assertNotSame(token, req.getToken());
		service.setState(req, StatusType.ADMIN_CONFIRMED);
		assertNotNull(service.getByTokenAndState(req.getToken(), StatusType.ADMIN_CONFIRMED));
	}

}
