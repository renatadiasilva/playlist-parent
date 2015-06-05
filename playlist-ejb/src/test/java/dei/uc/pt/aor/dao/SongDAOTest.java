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
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@RunWith(MockitoJUnitRunner.class)
public class SongDAOTest {
	
	@Mock
	EntityManager em;

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
 	}

	@Test
	public void TestAllSongs() {
		String NAMEDQUERY = "Song.allSongs";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Song> results = dao.findAllByOrder();
		when(q.getResultList()).thenReturn(results);
			
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}
	
	@Test
	public void TestSongsOfUser() {
		String NAMEDQUERY = "Song.songsOfUser";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);

		List<Song> results = dao.songsOfUser(u);
		when(q.getResultList()).thenReturn(results);

		verify(q).getResultList();
		verify(q).setParameter("ownerId", u);
		verify(em).createNamedQuery(NAMEDQUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}

	@Test
	public void TestSongsByArtistTitle() {
		String exp = "%";
		String NAMEDQUERY = "Song.songsByArtistTitle";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);

		List<Song> results = dao.songsByArtistTitle(exp, exp);
		when(q.getResultList()).thenReturn(results);

		verify(q).getResultList();
		verify(q).setParameter("t", exp);
		verify(q).setParameter("a", exp);
		verify(em).createNamedQuery(NAMEDQUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}
	
}