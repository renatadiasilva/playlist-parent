package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	
	public List<Playlist> playlistsOfUser(User u, int order) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		switch (order) {
		case 1: return super.findSomeResults("Playlist.playlistOfUserByNameAsc", parameters);
		case 2: return super.findSomeResults("Playlist.playlistOfUserByNameDesc", parameters);
		case 3: return super.findSomeResults("Playlist.playlistOfUserByDateAsc", parameters);
		case 4: return super.findSomeResults("Playlist.playlistOfUserByDateDesc", parameters);
		case 5: return super.findSomeResults("Playlist.playlistOfUserBySizeAsc", parameters);
		case 6: return super.findSomeResults("Playlist.playlistOfUserBySizeDesc", parameters);
		default: return null;
		}
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

	public List<Playlist> playlistsOfUserContainingSong(User u, Song s) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		parameters.put("idS", s.getId());
		return super.findSomeResults("Playlist.playlistsOfUserContainingSong", parameters);

	}

}