package dei.uc.pt.aor;

public interface LyricFacade {

	public abstract Lyric addLyric(String text, Song s);
	public abstract Lyric getLyricSongUser(User u, Song s);
	public abstract void editLyric(User u, Lyric lyric);
	public abstract Lyric getLyricById(Long id);
}
