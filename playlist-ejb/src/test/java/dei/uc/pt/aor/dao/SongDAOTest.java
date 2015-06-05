package dei.uc.pt.aor.dao;

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
	    u = new User("teste", "123", "teste@teste.com");
		dao.songsOfUser(u);
		verify(q).getResultList();
		verify(q).setParameter("ownerId", u);
		System.out.println("Checked query Song.songsOfUser.");
	}

	public void TestSongsByArtistTitle() {
		String exp = "exp";
		when(em.createNamedQuery("Song.songsByArtistTitle")).thenReturn(q);
		dao.songsByArtistTitle(exp, exp);
		verify(q).getResultList();
		verify(q).setParameter("t", exp);
		verify(q).setParameter("a", exp);
		System.out.println("Checked query Song.songsByArtistTitle");
	}
	
}