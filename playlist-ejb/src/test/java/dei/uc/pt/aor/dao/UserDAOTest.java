package dei.uc.pt.aor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import dei.uc.pt.aor.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
	
	@Mock
	EntityManager em;

	@Mock
	User u;
	
	@Mock
	Query q;
	
	@InjectMocks
	UserDAO dao;	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		u = new User("teste", "123", "teste@teste.com");
		em.persist(u);
	}

	@Test
	public void TestFindUserByEmail() {
		String email = "teste@teste.com";
		String NAMEDQUERY = "User.findUserByEmail";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		User result = dao.findUserByEmail(email);
		when(q.getSingleResult()).thenReturn(result);
		
		verify(q).getResultList();
		verify(q).setParameter("email", email);
		verify(em).createNamedQuery(NAMEDQUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}

	@Test
	public void TestUsersWithNameStartingBy() {
		String exp = "%";
		String NAMEDQUERY = "User.findUserStartingBy";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<User> results = dao.usersWithNameStartingBy(exp);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(q).setParameter("exp", exp);
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}

}
