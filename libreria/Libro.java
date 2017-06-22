package libreria;

import java.util.List;

public class Libro {
	private final static int numeroSettimane = 52;
	private final static int numeroMesi = 12;
	private Libreria libreria;
	private String titolo;
	private String autore;
	private int anno;
	private double prezzo;
	private Editore editore;
	private int quantita;
	private int[] venditeMensili = new int[numeroMesi];
	private int[] venditeSettimanali = new int[numeroSettimane];
	private int soglia;
	private int quantitaRiordino;
	private boolean inAttesa = false;
	
	public Libro(String titolo, String autore, int anno, double prezzo, Editore editore, Libreria libreria){
		this.libreria = libreria;
		this.titolo = titolo;
		this.autore = autore;
		this.anno = anno;
		this.prezzo = prezzo;
		this.editore = editore;
	}

    public String getTitolo(){
        return titolo;
    }
    
    public String getAutore(){
        return autore;
    }
    
    public int getAnno(){
        return anno;
    }

    public double getPrezzo(){
        return prezzo;
    }
    
    public Editore getEditore(){
        return editore;
    }

    public void setQuantita(int q){ 
    	quantita  = q;
    }
    
    public int getQuantita(){
        return quantita;	
    }

    public void registraVendita(int settimana, int mese){
    	if(settimana >= numeroSettimane){
    		System.out.println("Numero settimana invalido");
    		return;
    	}
    	if(mese >= numeroMesi){
    		System.out.println("Numero mese invalido");
    		return;
    	}
    	setQuantita( getQuantita() - 1 );
    	if(quantita <= soglia && inAttesa == false){
    		libreria.getOrdini().add(
    				new Ordine(editore, this, quantitaRiordino, libreria.getOrdini().size())
    				);
    		inAttesa = true;
    	}
    	venditeMensili[mese-1]++;
    	venditeSettimanali[settimana-1]++;
    }
    
    public int getVenditeSettimana(int settimana){
    	return venditeSettimanali[settimana-1];
    }
    
    public int getVenditeMese(int mese) {
    	return venditeSettimanali[mese-1];
		
	}
    
    public void setParametri(int soglia, int quantitaRiordino){ 
    	this.soglia = soglia;
    	this.quantitaRiordino = quantitaRiordino;
    }
    
    public String toString(){
    	return getTitolo() + " - " + getAutore();
    }
    
    public void setInAttesa(boolean bool){
    	inAttesa = bool;
    }
}
