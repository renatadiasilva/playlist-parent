package wsconsumer;

import java.rmi.RemoteException;

import com.chartlyrics.api.Apiv1SoapProxy;
import com.chartlyrics.api.GetLyricResult;

public class SoapConsumer {

	public String getLyricFromSoap(String artist, String title) {
		
		Apiv1SoapProxy soap = new Apiv1SoapProxy();
		GetLyricResult result = null;
		boolean search = false;
		
		//Ir Buscar a lyric de uma musica por artista e titulo
		while(!search){
			try {
				result = soap.searchLyricDirect(artist, title);
				search = true;
				
			} catch (RemoteException e) {
//				e.printStackTrace();
				System.out.println("Connecting...");
			}
			
		}
		
		return result.getLyric();
		
	}

}
