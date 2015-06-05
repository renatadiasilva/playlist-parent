package dei.uc.pt.aor.dao;

import java.util.Date;
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
import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistDAOTest {

	@Mock
	EntityManager em;

	@Mock
	Playlist p;
	
	@Mock
	Song s;
	
	@Mock
	User u;
	
	@Mock
	Query q;
	
	@InjectMocks
	PlaylistDAO dao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        u = new User("teste", "123", "teste@teste.com");
        s = new Song("T", "A", "Al", 2000, "C", u);
        p = new Playlist("P", new Date(), u);
		em.persist(u);
		em.persist(s);
		em.persist(p);
		p.addSong(s);
	}	 

	@Test
	public void TestPlaylistSameName() {
		String name = "P";
		String NAMEDQUERY = "Playlist.playlistSameName";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);

		List<Playlist> results = dao.playlistSameName(u, name);
		when(q.getResultList()).thenReturn(results);

		verify(q).getResultList();
		verify(q).setParameter("ownerId", u);
		verify(q).setParameter("name", name);
		verify(em).createNamedQuery(NAMEDQUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}	

	@Test
	public void TestGetSongsOfPlaylist() {
		String NAMEDQUERY="Playlist.songsOfPlaylist";
		String QUERY = "select s from Playlist p join p.songs s where p.id = :id";
		when(em.createQuery(QUERY)).thenReturn(q);

		List<Song> results = dao.getSongs(p);
		when(q.getResultList()).thenReturn(results);

		verify(q).getResultList();
		verify(q).setParameter("id", p.getId());
		verify(em).createQuery(QUERY);

		System.out.println("Checked successfully query "+NAMEDQUERY+".");
	}
	
	@Test
	public void TestPlaylistsOfUserByNameAsc() {
		int order = 1;
		String NAMEDQUERY = "Playlist.playlistOfUserByNameAsc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}

	@Test
	public void TestPlaylistsOfUserByNameDesc() {
		int order = 2;
		String NAMEDQUERY = "Playlist.playlistOfUserByNameDesc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}

	@Test
	public void TestPlaylistsOfUserByDateAsc() {
		int order = 3;
		String NAMEDQUERY = "Playlist.playlistOfUserByDateAsc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}

	@Test
	public void TestPlaylistsOfUserByDateDesc() {
		int order = 4;
		String NAMEDQUERY = "Playlist.playlistOfUserByDateDesc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}
	
	@Test
	public void TestPlaylistsOfUserBySizeAsc() {
		int order = 5;
		String NAMEDQUERY = "Playlist.playlistOfUserBySizeAsc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}

	@Test
	public void TestPlaylistsOfUserBySizeDesc() {
		int order = 6;
		String NAMEDQUERY = "Playlist.playlistOfUserBySizeDesc";
		when(em.createNamedQuery(NAMEDQUERY)).thenReturn(q);
		
		List<Playlist> results = dao.playlistsOfUser(u, order);
		when(q.getResultList()).thenReturn(results);
		
		verify(q).getResultList();
		verify(em).createNamedQuery(NAMEDQUERY);
		
		System.out.println("Checked successfully query "+NAMEDQUERY+".");
		
	}

}