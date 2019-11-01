package geschaeftsobjekte;

public class Rechnungsposition {
	protected double preis;
	protected Produkt produkt;
	protected int anzahl;
	
	public Rechnungsposition(int anzahl, Produkt produkt) {
		this.anzahl = anzahl;
		this.produkt = produkt;
		preis = produkt.getPreis();
	}
	
	//GETTER
	public double getPreis() {
		return anzahl * preis;
	}
	
	public Produkt getProdukt() {
		return produkt;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
	
	//SETTER
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	//OVERRIDE toString
	public String toString() {
		String s = "";
		
		//Bezeichnung
		if(produkt.getBezeichnung().length() > 34) {
			s += produkt.getBezeichnung().substring(0, 34) + "\n";
		} else {
			s += produkt.getBezeichnung() + "\n";
		}
		
		//Anzahl
		s += String.format(" %3d ", anzahl);
		
		//Einheit falls vorhanden
		if(produkt instanceof Dienstleistung) {
			if(((Dienstleistung) produkt).getEinheit().length() > 3) {
				s += String.format("%-3s", ((Dienstleistung) produkt).getEinheit().subSequence(0, 3));
			} else {
				s += String.format("%-3s", ((Dienstleistung) produkt).getEinheit());
			}
		} else {
			s += "   ";
		}
		
		//X Zeichen
		s += " x ";
				
		//Preis des Produktes
		s += String.format("%8.2f = ", preis);
		
		//Preis der Rechnungsposition
		s += String.format("%12.2f EURO", anzahl * preis);
		
		return s;
	}
}
