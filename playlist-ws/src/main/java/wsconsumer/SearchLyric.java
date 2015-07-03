package wsconsumer;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchLyric {

	private static final Logger log = LoggerFactory.getLogger(SearchLyric.class);

	private SoapConsumer soap = new SoapConsumer();
	private RestConsumer rest = new RestConsumer();
	private String lyric = null;
	
	
	//searches the lyric of a song on the web
	public String getLyric(String artist, String title){
		

		//First: try to search the lyric by Soap Web Service - chartlyrics.com
		log.info("Searching song lyrics (SOAP - chartlyrics)");
		lyric = soap.getLyricFromSoap(artist, title);
		if(lyric != null && (lyric.compareTo("")!=0)){
			return lyric;
		}else{
			//Second: try to search the lyric by Rest Web Service - chartlyrics.com
			try {
				log.info("Searching song lyrics (REST - chartlyrics)");
				lyric = rest.getChartLyric(artist, title);
				if (lyric==null || lyric.compareTo("")==0){
					lyric = rest.getWikiaLyric(artist, title);
				}
			} catch (Exception e) {
				log.error("crashing : "+e);
				//Third: try to search the lyric by Rest Web Service - api.wikia.com
				try {
					log.info("Searching song lyrics (REST - Wikilyrics)");
					lyric = rest.getWikiaLyric(artist, title);
				} catch (JAXBException e1) {
					return null;
				}
			} 
			return lyric;
		}

	}
}
