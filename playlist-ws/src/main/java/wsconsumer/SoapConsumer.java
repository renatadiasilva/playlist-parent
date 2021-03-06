package wsconsumer;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chartlyrics.api.Apiv1SoapProxy;
import com.chartlyrics.api.GetLyricResult;

public class SoapConsumer {

	private static final Logger log = LoggerFactory.getLogger(SoapConsumer.class);
	
	public String getLyricFromSoap(String artist, String title) {
		
		try {
			Apiv1SoapProxy soap = new Apiv1SoapProxy();
			GetLyricResult result = null;
			int count = 1;
			
			//Ir Buscar a lyric de uma musica por artista e titulo - chartlyrics
			while(count<=5){
				try {
					result = soap.searchLyricDirect(artist, title);
					log.info("Find the lyric by Soap in "+count+" times");
					log.info("Lyric:"+result.getLyric()+"#");
					if (result != null) return result.getLyric();
				} catch (RemoteException e) {
					count++;
					log.info("Remote exception: "+count);
				}
				
			}
			
		} catch (Exception e2) {
			return null;
		}
		
		return null;
		
	}

}
