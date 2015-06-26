package wsconsumer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="GetLyricResult", namespace="http://api.chartlyrics.com/")
public class ChartLyric {
	
	@XmlElement(name="Lyric")
	private String lyric;

	
	public String getSongLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	



	
	
	
}
