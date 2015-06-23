package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dei.uc.pt.aor.SimpleUser;

@Stateless
public class SimpleUserFacade implements Serializable, ISimpleUserFacade{
	
	/**
	 * We should generate one !!!
	 */
	private static final long serialVersionUID = 2247848378581085739L;
	
	@PersistenceContext(unitName="bdtest")
	private EntityManager entityManager;
	
	
	@Override
	public SimpleUser create(SimpleUser entity) {
		entityManager.persist(entity);
	    return entity;
	}

	@Override
	public SimpleUser update(SimpleUser entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void delete(SimpleUser entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimpleUser find(long pk) {
		// findById
		TypedQuery<SimpleUser> query = entityManager.createNamedQuery("SimpleUser.findById",SimpleUser.class).setParameter("id", pk);
		
		if(!query.getResultList().isEmpty())
			return query.getResultList().get(0);
		else
			return null;
	}

	@Override
	public Collection<SimpleUser> findAll() {
		// THE NOT SO GOOD WAY
		//Query query = entityManager.createQuery("SELECT user FROM SimpleUser user");
		// SimpleUser.findAll
		
		// ALMOST GOOD WAY  (NamedQuery)
		// Query query = entityManager.createNamedQuery("SimpleUser.findAll");
		// THE GOOD WAY
		TypedQuery<SimpleUser> query = entityManager.createNamedQuery("SimpleUser.findAll",SimpleUser.class);
		
		return query.getResultList();
	}

}
