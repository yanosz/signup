package jan.signup.services.ldap;

import jan.signup.server.services.ldap.LdapAccountService;
import jan.signup.server.services.ldap.LdapAccountServiceImpl;

import org.junit.Test;

public class LdapAccountServiceTest {
	
	private LdapAccountService service = new LdapAccountServiceImpl();
	
	@Test
	public void testSmoke(){
		service.getByEmail("jan@jluehr.de");
	}
}
