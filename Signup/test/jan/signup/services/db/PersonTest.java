package jan.signup.services.db;
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
public class PersonTest {
    private EntityManagerFactory emf;
 
    private EntityManager em;
 
    @Before
    public void initEmfAndEm() {
        BasicConfigurator.configure();
        Logger.getLogger("org").setLevel(Level.ERROR);
 
        emf = Persistence.createEntityManagerFactory("pu");
        em = emf.createEntityManager();
    }
 
    @After
    public void cleanup() {
        em.close();
    }
 
    @Test
    public void emptyTest() {
    }
}