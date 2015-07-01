package wsconsumer;

import java.rmi.RemoteException;

import com.chartlyrics.api.Apiv1SoapProxy;
import com.chartlyrics.api.GetLyricResult;

public class SoapConsumer {

	public String getLyricFromSoap(String artist, String title) {
		
		Apiv1SoapProxy soap = new Apiv1SoapProxy();
		GetLyricResult result = null;
		int count = 1;
		
		//Ir Buscar a lyric de uma musica por artista e titulo - chartlyrics
		while(count<=5){
			try {
				result = soap.searchLyricDirect(artist, title);
				count=6;
				
			} catch (RemoteException e) {
//				e.printStackTrace();
				System.out.println("Connecting...");
				count++;
				if(count==6){
					return null;
				}
			}
			
		}
		
		return result.getLyric();
		
	}

}
