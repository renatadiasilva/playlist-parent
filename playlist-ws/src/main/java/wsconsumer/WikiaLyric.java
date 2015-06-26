package wsconsumer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="LyricsResult")
public class WikiaLyric {
	
	@XmlElement(name="lyrics")
	private String lyrics;

	
	public String getSongLyric() {
		return lyrics;
	}

	public void setLyric(String lyrics) {
		this.lyrics = lyrics;
	}
	



	
	
	
}
