package libreria;

import sun.nio.cs.ext.TIS_620;

public class Ordine {
	
	private Editore editore;
	private Libro libro;
	private int quantita;
	private boolean consegnato = false;
	private int numero;
	
	public Ordine(Editore editore, Libro libro, int quantita, int numero){
		this.editore = editore;
		this.libro = libro;
		this.quantita = quantita;
		this.numero = numero;
	}

    public Editore getEditore(){
        return editore;
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public int getQuantita(){
        return quantita;
    }

    public boolean isConsegnato(){
        return consegnato;
    }

    public int getNumero(){
        return numero;
    }
    
    public void setConsegnato(boolean bool){
    	consegnato = bool;
    	libro.setInAttesa(false);
    }
    
    public String toString(){
    	return getNumero() + " - " + getLibro();
    }
}
