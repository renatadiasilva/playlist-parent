package dei.uc.pt.aor.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericDAO<T> {
	
	@PersistenceContext(unitName = "Proj4")
	protected EntityManager em;

	private Class<T> entityClass;
	
	private static final Logger log = LoggerFactory.getLogger(GenericDAO.class);

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void save(T entity) {
		try {
			em.persist(entity);
		} catch (Exception e) {
        	String errorMsg = "Error persisting Entity to BD: "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
	}

	protected void delete(Object id, Class<T> classe) {
		try {
			T entityToBeRemoved = em.getReference(classe, id);
			em.remove(entityToBeRemoved);
		} catch (Exception e) {
        	String errorMsg = "Error removing Entitiy to BD: "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
	}
	
	public T update(T entity) {
		try {
			return em.merge(entity);
		} catch (Exception e) {
        	String errorMsg = "Error updating BD: "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
	
		return null;
	}

	public T find(int entityID) {
		try {
			return em.find(entityClass, entityID);
		} catch (Exception e) {
        	String errorMsg = "Error in seaching(FIND) BD: "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			return em.createQuery(cq).getResultList();
		} catch (Exception e) {
        	String errorMsg = "Error in seaching(FINDALL) BD: "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<T> findAllByOrder(String namedQuery) {
		List<T> results = new ArrayList<T>();
		
		try {
			Query query = em.createNamedQuery(namedQuery);
			results = query.getResultList();
		} catch (Exception e) {
        	String errorMsg = "Error while running query"+
					"(FINDALLBYORDER): "+e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		
		return results;
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			
			result = (T) query.getSingleResult();
		} catch (Exception e) {
        	String errorMsg = "Error while running query"+
					"(FINDONERESULT): "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
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
        	String errorMsg = "Error while running query"+
					"(FINDSOMERESULT): "+
					e.getMessage();
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		
		return results;
	}
	
	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}