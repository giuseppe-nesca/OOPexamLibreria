package libreria;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class Libreria {
	
	Map<String, Editore> editori =  new TreeMap<>();
	Map<String, Libro> libriPerTitolo = new HashMap<>();
	Map<String, Libro> libriPerAutore = new HashMap<>();
	Map<String, Map<String,Libro>> autori = new HashMap<>();
 
    public Editore creaEditore(String nome, int tempoConsegna, String email){
        Editore e = new Editore( nome, tempoConsegna, email );
        editori.put(nome, e);
        return e;
    }

    public Editore getEditore(String nome){
        return editori.get(nome);
    }

    public Collection getEditori(){
        return editori.values();
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
        
    	Editore editore;
    	Libro libro;
    	
    	try {
			editore = editori.get(nomeEditore);
			if ( editore == null ){
				throw new EditoreInesistente();
			}
		} catch (EditoreInesistente e) {
			System.out.println("editore inesistente.");
			return null;
		}
    	libro = new Libro(titolo, autore, anno ,prezzo, editore);
    	libriPerAutore.put(autore, libro);
    	libriPerTitolo.put(titolo, libro);
    	
    	try{
    		autori.get(autore).put(titolo, libro);
    	}catch(Exception e){
    		autori.put(autore, new HashMap<String,Libro>());
    		autori.get(autore).put(titolo, libro);
    	}
    	
    	return libro;
    }
    
    public Libro getLibro(String autore, String titolo){
    	
        return null;
    }
    
    public Collection getClassificaSettimana(final int settimana){
        return null;
    }
    
    public Collection getClassificaMese(final int mese){
        return null;
    }
    
    public Collection getOrdini(){
        return null;
    }
    
    public void ordineRicevuto(int numOrdine){
    }
    
    public void leggi(String file){
    }
}
