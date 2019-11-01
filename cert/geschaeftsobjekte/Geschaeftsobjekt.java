package geschaeftsobjekte;

import java.io.*;

public abstract class Geschaeftsobjekt implements Serializable {
	static int rechnungCounter = 0;
	protected final int nummer;
	
	public Geschaeftsobjekt(int nummer) {
		this.nummer = nummer;
	}
	
	//GETTER
	public int getNummer() {
		return nummer;
	}	
}
