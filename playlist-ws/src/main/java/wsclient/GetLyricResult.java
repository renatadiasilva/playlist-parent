package wsclient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="GetLyricResult", namespace="http://api.chartlyrics.com")
public class GetLyricResult {
	
	@XmlElement(name="Lyric")
	private String lyric;
	

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	
	
	
}
