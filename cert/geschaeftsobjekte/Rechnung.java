package geschaeftsobjekte;

import java.util.*;
import exceptions.BookingException;
import exceptions.OutOfStockException;

public class Rechnung extends Geschaeftsobjekt {
	private int number;
	private Kunde kunde;
	private Rechnungsstatus status;
	private ArrayList<Rechnungsposition> positionen = new ArrayList<>();
	
	public Rechnung() {
		super(rechnungCounter + 1);
		rechnungCounter++;
		number = rechnungCounter;
		status = Rechnungsstatus.IN_ERSTELLUNG;
	}
	
	public Rechnung(Kunde kunde) {
		this();
		this.kunde = kunde;
	}
	
	//ADD RECHNUNGSPOSITION
	public Rechnungsposition addRechnungsposition(int anzahl, Produkt p) throws OutOfStockException, BookingException {
		boolean found = false;
		Rechnungsposition neuePosition = null;
		
		if (status != Rechnungsstatus.IN_ERSTELLUNG) {
			throw new BookingException("Rechnungsstatus nicht in Erstellung!");
		}
		
		for(Rechnungsposition r : positionen) {
			Produkt pFound = r.getProdukt();
			if(r.getProdukt().equals(p) && p.getClass().getName() == "geschaeftsobjekte.Artikel") {
				if(((Artikel)pFound).getLagerbestand() < anzahl) {
					throw new OutOfStockException(p, "Lagerbestand nicht ausreichend!");
				}
				//Anzahl erhöhen
				if(r.getAnzahl() + anzahl > ((Artikel)pFound).getLagerbestand()) {
					r.setAnzahl(((Artikel)pFound).getLagerbestand());
					throw new OutOfStockException(pFound, "Lagerbestand nicht ausreichend!");
				} 
				else {
					r.setAnzahl(r.getAnzahl() + anzahl);
				}
				
				neuePosition = r;
				found = true;
				break;
			} else if(r.getProdukt().equals(p)) {
				r.setAnzahl(r.getAnzahl() + anzahl);
				found = true;
				break;
			}
		}
		if(found == false && p.getClass().getName() == "geschaeftsobjekte.Artikel") {
			if(((Artikel)p).getLagerbestand() < anzahl) {
				throw new OutOfStockException(p, "Lagerbestand nicht ausreichend!");
			}
		}
		
		if(found == false && status == Rechnungsstatus.IN_ERSTELLUNG) {
			neuePosition = new Rechnungsposition(anzahl, p);
			setRechnungspositionen(neuePosition);
		}
		
		return neuePosition;
	}
	
	//GET RECHNUNGSPOSITION
	public Rechnungsposition getRechnungsposition(Produkt p) {
		Rechnungsposition position = null;
		
		for(Rechnungsposition r : positionen) {
			if(r.getProdukt() == p) {
				position = r;
			}
		}
		
		return position;
	}
	
	//GET ANZAHL RECHNUNGSPOSITIONEN
	public int getAnzahlRechnungspositionen() {
		int counter = 0;
		
		for(Rechnungsposition r : positionen) {
			counter++;
		}
		
		return counter;
	}
	
	//GET GESAMTPREIS
	public double getGesamtpreis() {
		double preis = 0;
		
		for(Rechnungsposition r : positionen) {
			preis += r.getPreis();
		}
		
		return preis;
	}
	
	//GET RECHNUNGSPOSITIONEN
	public List<Rechnungsposition> getRechnungspositionen() {
		return (ArrayList<Rechnungsposition>) positionen;
	}
	
	//BUCHEN
	public void buchen() throws OutOfStockException, BookingException {
		if(status == Rechnungsstatus.IN_ERSTELLUNG) {
			for(Rechnungsposition r : positionen) {
				Produkt pFound = r.getProdukt();
				if(pFound.getClass().getName() == "geschaeftsobjekte.Artikel") {
					((Artikel)pFound).auslagern(r.getAnzahl());
				}
			}
			status = Rechnungsstatus.GEBUCHT;
		} else {
			throw new BookingException("Rechnungsstatus nicht in Erstellung!");
		}
	}
	
	//OVERRIDE toString
	@Override
	public String toString() {
		String s = "";
		
		if(kunde != null) {
			s += String.format("Rechnung: %d\nKunde: %d\n%s\n%s\n%s\n", number, kunde.getNummer(), kunde.getName(), kunde.getStrasse(), kunde.getOrt());
		} else {
			s += String.format("Rechnung: %d\nBarverkauf\n", number);
		}
		
		for(Rechnungsposition r : positionen) {
			s += String.format("\n%s", r.toString());
		}
		
		s += "\n---------------------------------------";
		
		s += String.format("\n%34.2f EURO", getGesamtpreis());
				
		return s;
	}
	
	//GETTER
	public Enum<Rechnungsstatus> getRechnungsstatus() {
		return status;
	}
	
	public Kunde getKunde() {
		return kunde;
	}
	
	//SETTER
	public void setRechnungspositionen(Rechnungsposition position) {
		positionen.add(position);
	}
}
