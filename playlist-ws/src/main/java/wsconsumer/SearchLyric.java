package wsconsumer;

import javax.xml.bind.JAXBException;

//This class searches the lyric of a song on the web
public class SearchLyric {

	private SoapConsumer soap;
	private RestConsumer rest;
	private String lyric;

	public String getLyric(String artist, String title){

		//First: try to search the lyric by Soap Web Service - chartlyrics.com
		lyric = soap.getLyricFromSoap(artist, title);
		if(!lyric.equals(null)){
			return lyric;
		}else{
			//Second: try to search the lyric by Rest Web Service - chartlyrics.com
			try {
				lyric = rest.getChartLyric(artist, title);
				if (lyric.equals(null) || lyric.equals("")){
					lyric = rest.getWikiaLyric(artist, title);
				}
			} catch (JAXBException e) {
				//Third: try to search the lyric by Rest Web Service - api.wikia.com
				try {
					lyric = rest.getWikiaLyric(artist, title);
				} catch (JAXBException e1) {
					return null;
				}
			} 
			return lyric;
		}


	}
}
