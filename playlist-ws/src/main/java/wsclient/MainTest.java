package wsclient;

import java.rmi.RemoteException;

import com.chartlyrics.api.Apiv1SoapProxy;
import com.chartlyrics.api.GetLyricResult;

public class MainTest {

	public static void main(String[] args) {
		
		Apiv1SoapProxy soap = new Apiv1SoapProxy();
		String artist = "amy winehouse";
		String song = "back to black";
//		String artist = "jonh legend";
//		String song = "all of me";
		GetLyricResult result = null;
		boolean search = false;
		
		//Ir Buscar a lyric de uma musica por artista e titulo
		while(!search){
			try {
				result = soap.searchLyricDirect(artist, song);
				search = true;
				
			} catch (RemoteException e) {
//				e.printStackTrace();
				System.out.println("Connecting...");
			}
			
		}
		
		System.out.println("Lyric: \n"+result.getLyric());
		
	}

}
