package geschaeftsobjekte;

public class Kunde extends Geschaeftsobjekt {
	private String name;
	private String strasse;
	private String ort;
	
	public Kunde(int nummer, String name, String strasse, String ort) {
		super(nummer);
		this.name = name;
		this.strasse = strasse;
		this.ort = ort;
	}
	
	//GETTER
	public String getName() {
		return name;
	}
	
	public String getStrasse() {
		return strasse;
	}
	
	public String getOrt() {
		return ort;
	}
	
	//SETTER
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
}
