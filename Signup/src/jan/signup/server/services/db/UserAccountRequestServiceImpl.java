package jan.signup.server.services.db;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class UserAccountRequestServiceImpl implements UserAccountRequestService {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
	private EntityManager em;

	public UserAccountRequestServiceImpl(){
		em = emf.createEntityManager();
	}
	
	public EntityManager getEm() {
		return em;
	}
	

	@Override
	public UAREntity createUser(String firstName, String lastName, String email,String unit) {
		EntityTransaction txt = em.getTransaction();
		txt.begin();
		UAREntity entity = new UAREntity(firstName, lastName, unit, email);
		UUID randomUUID = UUID.randomUUID();
		entity.setToken(randomUUID.toString());
		entity.setWorkflowStatus(StatusType.NEW);
		em.persist(entity);
		txt.commit();
		return entity;
	}

	@Override
	public UAREntity getByTokenAndState(String token, StatusType state) {
		EntityTransaction txt = em.getTransaction();
		txt.begin();
		String queryStr = "select a from UAREntity a where " +
			"a.token = :token ";
		if(state != null){
			queryStr += "and a.workflowStatus = :state";
		}
			
		TypedQuery<UAREntity> query = em.createQuery(queryStr,UAREntity.class);
		query.setParameter("token", token);
		if(state != null){
			query.setParameter("state", state);
		}
		List<UAREntity> result = query.getResultList();
		txt.commit();
		if(result.size() != 1){
			return null;
		}
		return result.get(0);
	
	}

	@Override
	public void changeToken(UAREntity req) {
		EntityTransaction txt = em.getTransaction();
		txt.begin();

		UUID randomUUID = UUID.randomUUID();
		req.setToken(randomUUID.toString());
		em.merge(req);
		txt.commit();
	}

	@Override
	public void setState(UAREntity req, StatusType state) {
		EntityTransaction txt = em.getTransaction();
		txt.begin();
		req.setWorkflowStatus(state);
		em.merge(req);
		txt.commit();
}


	@Override
	public UAREntity getByToken(String token) {
		return getByTokenAndState(token, null);
		
	}

	@Override
	public UAREntity getByEmail(String email) {
		EntityTransaction txt = em.getTransaction();
		txt.begin();
		TypedQuery<UAREntity> query = em.createQuery("select a from UAREntity a where email = :email", UAREntity.class);
		query.setParameter("email", email);
		txt.commit();
		List<UAREntity> res = query.getResultList();
		if(res.size() != 1){
			return null;
		}
		return res.get(0);
	}

	@Override
	public void update(UAREntity res) {
		em.merge(res);
	}

}
