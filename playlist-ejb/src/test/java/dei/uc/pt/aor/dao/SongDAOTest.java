package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@RunWith(MockitoJUnitRunner.class)
public class SongDAOTest {
	
	@Mock
	EntityManager em;

	@Mock
	Song s;
	
	@Mock
	User u;
	
	@Mock
	Query q;
	
	@InjectMocks
	SongDAO dao;	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        u = new User("teste", "123", "teste@teste.com");
        s = new Song("T", "A", "Al", 2000, "C", u);
	}

	@Test
	public void TestAllSongs() {
		when(em.createNamedQuery("Song.allSongs")).thenReturn(q);
		dao.findAllByOrder();
		verify(q).getResultList();
		System.out.println("Checked query Song.allSongs.");
	}

	@Test
	public void TestSongsOfUser() {
		when(em.createNamedQuery("Song.songsOfUser")).thenReturn(q);
		dao.songsOfUser(u);
		verify(q).getResultList();
		verify(q).setParameter("ownerId", u);
		System.out.println("Checked query Song.songsOfUser.");
	}

//	public void testaGetMusicaFromId(){
//		long id=0;
//			when(em.createQuery("from Musica p where p.id = :id")).thenReturn(q);
//			ejb.getMusicaFromId(id);
//			verify(q).setParameter("id", id);	
//	}
//

	
//    when(mockedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(mockedQuery);

//	public List<Song> findAllByOrder() {
//	return super.findAllByOrder("Song.allSongs");
//}

	
//	@NamedQuery(name="Song.songsOfUser",
//			query="SELECT s FROM Song s WHERE s.owner = :ownerId ORDER BY s.title"),
//	@NamedQuery(name="Song.songsByArtistTitle",
//			query="SELECT s FROM Song s WHERE UPPER(s.title) LIKE :t AND UPPER(s.artist) LIKE :a ORDER BY s.title"),

	
//
//	@SuppressWarnings("unchecked")
//	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
//		T result = null;
//
//		try {
//			Query query = em.createNamedQuery(namedQuery);
//
//			if (parameters != null && !parameters.isEmpty()) {
//				populateQueryParameters(query, parameters);
//			}
//			
//			result = (T) query.getSingleResult();
//		} catch (Exception e) {
//			System.out.println("Error while running query: " + e.getMessage());
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
//	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
//		
//		for (Entry<String, Object> entry : parameters.entrySet()) {
//			query.setParameter(entry.getKey(), entry.getValue());
//		}
//	}


//public List<Song> songsOfUser(User u) {
//	Map<String, Object> parameters = new HashMap<String, Object>();
//	parameters.put("ownerId", u);
//	return super.findSomeResults("Song.songsOfUser", parameters);
//}
//
//public List<Song> songsByArtistTitle(String expt, String expa) {
//	Map<String, Object> parameters = new HashMap<String, Object>();
//	parameters.put("t", expt);
//	parameters.put("a", expa);
//	return super.findSomeResults("Song.songsByArtistTitle", parameters);
//}
//
		
}