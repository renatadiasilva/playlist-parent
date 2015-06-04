package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@Stateless
public class PlaylistDAO extends GenericDAO<Playlist> {
	
	public PlaylistDAO() {
		super(Playlist.class);
	}

	public void delete(Playlist playlist) {
			super.delete(playlist.getId(), Playlist.class);
	}
	
	public List<Playlist> playlistsOfUser(User u) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		return super.findSomeResults("Playlist.playlistOfUserByNameAsc", parameters);
	}
	
	public List<Playlist> playlistSameName(User u, String name) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		parameters.put("name", name);
		return super.findSomeResults("Playlist.playlistSameName", parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Song> getSongs(Playlist p) {

		Query cq = em.createQuery("select s from Playlist p join p.songs s where p.id = :id");
		cq.setParameter("id", p.getId());
		return cq.getResultList();
		
	}

	@SuppressWarnings("unchecked")
	public void removeSong(Playlist p, Song s) {
		
		Query cq = em.createQuery("select p, s from Playlist p join p.songs s where p.id = :idP and s.id = :idS");
		cq.setParameter("idP", p.getId());
		cq.setParameter("idS", s.getId());
		List<Object[]> listobjs = cq.getResultList();
		Playlist playlist = (Playlist) listobjs.get(0)[0];
		Song song = (Song) listobjs.get(0)[1];
		playlist.removeSong(song);
		p.setSize(playlist.getSize());
	}

	public void addSong(Playlist p, Song s) {

		Query cq = em.createQuery("select p from Playlist p where p.id = :idP");
		cq.setParameter("idP", p.getId());
		Playlist playlist = (Playlist) cq.getSingleResult();
		cq = em.createQuery("select s from Playlist p join p.songs s where p.id = :idP and s.id = :idS");
		cq.setParameter("idP", p.getId());
		cq.setParameter("idS", s.getId());

		try{
			cq.getSingleResult();
		} catch (NoResultException nre){
			playlist.addSong(s);
			p.setSize(playlist.getSize());
		}
		
	}

}