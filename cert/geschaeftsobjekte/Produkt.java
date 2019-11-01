package geschaeftsobjekte;

import java.util.*;
import java.io.*;

public abstract class Produkt extends Geschaeftsobjekt implements Comparable<Produkt> {
	protected String bezeichnung;
	protected double preis;
	
	public Produkt(int nummer, String bezeichnung, double preis) {
		super(nummer);
		this.bezeichnung = bezeichnung;
		this.preis = preis;
	}
	
	//GETTER
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public double getPreis() {
		return preis;
	}
	
	//SETTER
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	//OVERRIDE equals
	@Override
	public boolean equals(Object o) {
		
		if(o.getClass() == (this.getClass()) && ((Produkt) o).getNummer() == this.getNummer()) {
			return true;
		} else {
			return false;
		}
	}
	
	//COMPARABLE
	public int compareTo(Produkt p) {
		
		if(p.getClass() != this.getClass()) {
			if(p instanceof Dienstleistung) {
				return 1;
			} else {
				return -1;
			}
		}
		
		//beide sind Dienstleistung oder Artikel
		//prüfen ob eine Bezeichnung null ist
		if(this.getBezeichnung() == null && p.getBezeichnung() != null) {
			return -1;
		}
		
		if(p.getBezeichnung() == null && this.getBezeichnung() != null) {
			return 1;
		}
		
//		if(p.getBezeichnung() == null && this.getBezeichnung() == null) {
//			System.out.println(" ==");
//			return 0;
//		}
		
		if(p.getBezeichnung() != null && this.getBezeichnung() != null) {
			//Bezeichnungen vergleichen
				return this.getBezeichnung().compareTo(p.getBezeichnung()); 
			}
		
		//Preise vergleichen
		if(this.getPreis() > p.getPreis()) {
			return 1;
		}
		if(p.getPreis() > this.getPreis()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	//OVERRIDE toString
	public String toString() {
		return this.getNummer() + " " + this.getBezeichnung() + " " + this.getPreis();
	}
	
	public static List<Produkt> loadProducts(String SERIALIZATION_PATH){ 
 	 	List<Produkt> produkte = new LinkedList<>();  
 	 	try { 
 	 	 	// oeffnet die Datei "data/Produkt.ser" zum lesen 
 	 	 	InputStream ins  = new FileInputStream(SERIALIZATION_PATH);  
 	 	 	ObjectInputStream objin = new ObjectInputStream(ins);  
 	 	 	// als erstes Objekt wird ein Integer-Objekt gelesen,  
 	 	 	// das angibt, wie viele Produkt-Objekte kommen werden 
 	 	 	int i=objin.readInt();    
 	 	 	int z=0; 
 	 	 	while(z++ < i){ 
 	 	 	 	// lese das nächste Produkt-Objekt aus der Datei  	 	 	 	
 	 	 		Produkt p = (Produkt) objin.readObject();  
 	 	 	 	produkte.add(p);  // und füge es der Liste hinzu 
 	 	 	} 
 	 	 	// die Datei muss wieder geschlossen/freigegeben werden 
 	 	 	objin.close();   	 	 	ins.close();     
 	 	} catch (Exception e) { 
 	 	 	e.printStackTrace(); // im Fehlerfall Fehlermeldung ausgeben 
 	 	} 
 	 	return produkte; // Liste der Produkte zurueckgeben 
 	} 

}
