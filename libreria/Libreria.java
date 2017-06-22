package libreria;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Libreria {
	
	private Map<String, Editore> editori =  new TreeMap<>();
	private Map<String, Libro> libriPerTitolo = new HashMap<>();
	private Map<String, Libro> libriPerAutore = new HashMap<>();
	private Map<String, Map<String,Libro>> autori = new HashMap<>();
	private List<Ordine> ordini = new ArrayList<>();

 
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
    	
    	
			editore = editori.get(nomeEditore);
			if ( editore == null ){
				throw new EditoreInesistente();
			}
		
    	libro = new Libro(titolo, autore, anno ,prezzo, editore, this);
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
    	if(autore == null && titolo != null){
    		return libriPerTitolo.get(titolo);
    	}
    	
    	if(autore != null && titolo == null){
    		return libriPerAutore.get(autore);
    	}
    	
    	try{
    		return autori.get(autore).get(titolo);
    	}catch(Exception e){
    		System.out.println("autore non presente.");
    		return null;
   		}
    }
    
    public Collection getClassificaSettimana(final int settimana){
    	
    	return 
    		libriPerTitolo.values().stream()
    		.sorted(new Comparator<Libro>() {
    			@Override
    			public int compare(Libro l1, Libro l2){
    				return l1.getVenditeSettimana(settimana) - l2.getVenditeSettimana(settimana);
    			}
			})
			//.map(l -> l.toString())
			.collect(Collectors.toList());
    }
    
    public Collection getClassificaMese(final int mese){
        return
        	libriPerTitolo.values().stream()
        	.sorted(new Comparator<Libro>(){
        		@Override
        		public int compare(Libro l1, Libro l2){
        			return l1.getVenditeMese(mese) - l2.getVenditeMese(mese);
        		};
        	})
        		//.map(l -> l.toString())
        	.collect(Collectors.toList());
    }
    
    public Collection getOrdini(){
        return ordini;
    }
    
    public void ordineRicevuto(int numOrdine){
    	Ordine ordine = ordini.listIterator(numOrdine).next();
    	ordine.setConsegnato(true);
    	ordine.getLibro().setQuantita(ordine.getLibro().getQuantita() + ordine.getQuantita());
    }
    
    public void leggi(String file){
    	
    try(BufferedReader br = new BufferedReader(new FileReader(file))){
    	String buffer;
    	
			while ((buffer = br.readLine()) != null) {
				try {
					StringTokenizer st = new StringTokenizer(buffer, ";");
					String type = st.nextToken();
					if (type.equals("E")) {
						String nome = st.nextToken();
						String tempoConsegna = st.nextToken();
						String email = st.nextToken();
						
						creaEditore(nome, new Integer(tempoConsegna), email);
					}
					if (type.equals("L")) {
						String titolo = st.nextToken();
						String autore = st.nextToken();
						int anno = new Integer(st.nextToken());
						double prezzo = new Double(st.nextToken());
						String nomeEditore = st.nextToken();
						int quantita = new Integer(st.nextToken());
						
						creaLibro(titolo, autore, anno, prezzo, nomeEditore).setQuantita(quantita);
						
					}
				} catch (Exception e) {
					continue;
				}
			}
    	
    	
    }catch(Exception e){
    	}
    }
}
