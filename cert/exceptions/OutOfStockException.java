package exceptions;

import geschaeftsobjekte.Produkt;

public class OutOfStockException extends Exception {
	private Produkt produkt;
	
	public OutOfStockException(Produkt produkt, String message) {
		this.produkt = produkt;
	}
	
	public Produkt getProdukt() {
		return produkt;
	}
}
