package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import dei.uc.pt.aor.Lyric;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@Stateless
public class LyricDAO extends GenericDAO<Lyric> {
	
	public LyricDAO() {
		super(Lyric.class);
	}

	public Lyric findLyricByUserAndSong(User u, Song s) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("owner", u);
		parameters.put("music", s);
		List<Lyric> list = super.findSomeResults("Lyric.lyricByUserAndSong", parameters);
		if (list.size() == 1) return list.get(0);
		return null;
	}
	
}