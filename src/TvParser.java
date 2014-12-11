
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TvParser {
	
	static String url = "http://www.staseraintv.com/film_in_tv_stasera.html";

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//array di oggetti MovieEntry che definisce la lista di film di stasera
		ArrayList<MovieEntry> filmdioggi = new ArrayList<MovieEntry>();
		//array di stringhe temporaneo
		ArrayList<String> temp = new ArrayList<String>();
		List<Element> elements = new ArrayList<Element>();
		List<Element> elements2 = new ArrayList<Element>();
		
		
		try {
			Document doc = Jsoup.connect(url).get();

			//Punto debole: seleziono dell'intera pagina parsata solo la sezione centrale tramite questi attributi
			//se cambiano le dimensioni bisogna risistemare
			elements = doc.getElementsByAttributeValue("style", "width: 659px; height: 53px;");
			
			for (int i=0;i<elements.size();i++){
				MovieEntry film = new MovieEntry();
				Element current = elements.get(i);
				elements2 = current.getElementsByTag("th");
				//Prendo le info: <canale,orario,numerocanale>
				//e le aggiungo in una lista di stringhe
				for (int j=0;j<elements2.size();j++){
					Element current2 = elements2.get(j);
					temp.add(current2.text());
					
				}
				elements2=current.getElementsByTag("td");
				//Prendo il titolo e lo aggiungo nella lista di stringhe
				
				Element current2 = elements2.get(0);
				temp.add(current2.text());	
				//converto in un oggetto MovieEntry
				reverse(temp,film);
				//aggiungo nell'array di MovieEntry
				filmdioggi.add(film);
				//resetto l'array di stringhe temporaneo
				temp.clear();
			}
		
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//stampa risultato
		for (int i=0;i<filmdioggi.size();i++){
			MovieEntry mv = filmdioggi.get(i);
			System.out.println("Film "+i+" :");
			System.out.println(" Titolo: "+mv.getTitle());
			System.out.println(" Orario: "+mv.getHour());
			System.out.println(" Canale: "+mv.getChannel());
			System.out.println(" Numero: "+mv.getNumber());
			System.out.println("-------------");
		}

	}
	
	private static void reverse(ArrayList<String> temp, MovieEntry film){
		//costruisce un oggetto MovieEntry partendo da una lista di stringhe
		if (temp.size()!=4){
			System.out.println("ERRORE");
			return;
		}
		film.setChannel(temp.get(0));
		film.setHour(temp.get(1));
		film.setNumber(temp.get(2));
		film.setTitle(temp.get(3));
	}
	
}

class MovieEntry {
	private String title;
	private String hour;
	private String channel;
	private String number;
	public MovieEntry(){
		title="";
		hour="";
		channel="";
		number="";
	}
	public String getTitle(){
		return title;
	}
	public String getHour(){
		return hour;
	}
	public String getChannel(){
		return channel;
	}
	public String getNumber(){
		return number;
	}
	public void setTitle(String s){
		title=s;
	}
	public void setHour(String s){
		hour=s;
	}
	public void setChannel(String s){
		channel=s;
	}
	public void setNumber(String s){
		number=s;
	}
}