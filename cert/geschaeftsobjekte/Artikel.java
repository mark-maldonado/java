package geschaeftsobjekte;

import exceptions.OutOfStockException;


public class Artikel extends Produkt {
	/**
	 * Attribute
	 */
	private int lagerbestand;
	
	/**
	 * Konstruktor
	 * @param nummer: Nummer
	 * @param bezeichnung: Bezeichnung
	 */
	public Artikel(int nummer, String bezeichnung, double preis) {
		super(nummer, bezeichnung, preis);
	}
	
	/**
	 * Lagerbestand erweitern
	 * @param nummer
	 */
	public void einlagern(int zahl) {
		lagerbestand += zahl;
	}
	
	/**
	 * Lagerbestand verringern
	 * @param zahl
	 */
	public void auslagern(int zahl) throws OutOfStockException {
		if(lagerbestand - zahl >= 0) {
			lagerbestand -= zahl;
		} else {
			throw new OutOfStockException(this, "Lagerbestand reicht nicht aus!");
		}
	}

	//GETTER
	public int getNummer() {
		return nummer;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public String getKurzbezeichnung() {
		return erzeugeKurzbezeichnung(nummer, bezeichnung);
	}
	
	public int getLagerbestand() {
		return lagerbestand;
	}
	
	//SETTER
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}	
	
	/**
	 * Kurzbeschreibung erstellen
	 * @param nummer
	 * @param bezeichnung
	 * @return
	 */
	static public String erzeugeKurzbezeichnung(int nummer, String bezeichnung) {
		String kurzb = "";
		int count = 0;
		int asciiSum = 0;
		String ascii = "";
		
		/**
		 * bezeichnung to ascii
		 */
		for(count = 0; count < bezeichnung.length(); count++) {
			char c = bezeichnung.charAt(count);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				asciiSum += (int) c;
			}
		}
		ascii = Integer.toString(asciiSum);
		
		/**
		 * ascii
		 */
		if (ascii.length() < 4) {
			for(count = 0; count < 4-ascii.length(); count++) {
				kurzb += "0";
			}
			kurzb += ascii;
		}
		
		else if (ascii.length() > 4) {
			for(count = 4; count > 0; count--) {
				kurzb += ascii.charAt(ascii.length()-count);
			}
		}
		
		else {
			kurzb = ascii;
		}
		
		/**
		 * nummer
		 */
		if (Integer.toString(nummer).length() < 4) {
			for(count = 0; count < 4-Integer.toString(nummer).length(); count++) {
				kurzb += "0";
			}			
			kurzb += Integer.toString(nummer);
		}
		
		else {
			for (count = 4; count > 0; count--) {
				kurzb += Integer.toString(nummer).charAt(Integer.toString(nummer).length()-count);
			}
		}
		
		/**
		 * Prüfziffer
		 */
		int prüfziffer = 0;
		for(count = 0; count < 8; count++) {
			prüfziffer += Character.getNumericValue(kurzb.charAt(count));
		}
		kurzb += Integer.toString(prüfziffer % 10);
		
		/**
		 * return
		 */
		return kurzb;
	}
	
	/**
	 * Ausgabe toString
	 * @param args
	 */
	public String toString() {
		String s = "";
		s += nummer + ", " + erzeugeKurzbezeichnung(nummer, bezeichnung) + ", " + bezeichnung + ", " + lagerbestand + " auf Lager";
		return s;
	}
	
}
