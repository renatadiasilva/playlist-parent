package dei.uc.pt.aor.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import dei.uc.pt.aor.User;

public abstract class GenericDAO<T> {
	
	@PersistenceContext(unitName = "Entities")
	private EntityManager em;

	//logger??
	
	private Class<T> entityClass;
	
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void save(T entity) {
		em.persist(entity);
	}

	protected void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);
		em.remove(entityToBeRemoved);
	}
	
	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		// order by???
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			
			result = (T) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findSomeResults(String namedQuery, Map<String, Object> parameters) {
		List<T> results = new ArrayList<T>();

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			
			results = query.getResultList();
			
		} catch (Exception e) {
			// passar para log??
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		
		return results;
	}
	
	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}