package geschaeftsobjekte;

public class Dienstleistung extends Produkt{
	private String einheit;
	
	public Dienstleistung(int nummer, String bezeichnung, double preis, String einheit) {
		super(nummer, bezeichnung, preis);
		this.einheit = einheit;
	}
	
	//GETTER
	public String getEinheit() {
		return einheit;
	}
	
	//SETTER
	public void setEinheit(String einheit) {
		this.einheit = einheit;
	}
}
