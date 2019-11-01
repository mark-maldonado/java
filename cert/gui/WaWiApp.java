package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import exceptions.BookingException;
import exceptions.OutOfStockException;
import geschaeftsobjekte.Artikel;
import geschaeftsobjekte.Dienstleistung;
import geschaeftsobjekte.Kunde;
import geschaeftsobjekte.Produkt;
import geschaeftsobjekte.Rechnung;
import gui.lagerbestaendeSichtenDialog.LagerbestaendeSichtenDialog;
import gui.lagerbestaendeSichtenDialog.LagerbestaendeSichtenDialogCloseHandler;
import gui.lagerbestandBearbeitenDialog.LagerbestandBearbeitenDialog;
import gui.lagerbestandBearbeitenDialog.LagerbestandBearbeitenDialogCloseHandler;
import gui.loginDialog.LoginDialog;
import gui.loginDialog.LoginHandler;
import gui.posDialog.POSDialog;
import gui.posDialog.POSDialogCheckoutHandler;
import gui.posDialog.POSDialogCloseHandler;
import gui.posDialog.POSDialogKundenauswahlHandler;
import gui.posDialog.POSDialogProduktButtonHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Startet die Dialoge der Anwendung und definiert für jeden Dialog
 * die Handler-Methoden (=Lambdas) für die Behandlung der Ereignisse des 
 * Dialogs. 
 * Tritt ein Ereignis auf, ruft der Dialog den hier definierten 
 * Handler auf. Dadurch entsteht eine Trennung (Modularisierung) zwischen
 * den Klassen der Benutzeroberfläche und der Ereignisbehandlung (diese Datei).
 * Vorteile sind die getrennte Testbarkeit der Ereignisbehandlung sowie 
 * der minimierte Aufwand beim Umstieg auf eine andere UI-Technologie. Letzteres
 * ergibt sich daraus, dass die geschäftslogik-Klassen keine Abhängigkeiten 
 * zum UI-Framework (hier: JavaFX) besitzen (siehe Imports in dieser Datei). 
 * 
 */
public class WaWiApp extends Application {
	/**
	 * Benötigte Daten: Produkte und Kunden
	 */
	private List<Produkt> produkte;
	private List<Kunde> kunden;
	
	private String nutzername = "Hugo";
	private String admin = "Admin";
	private String passwort = "123";
	
	/**
	 * Dialoge der App
	 */
	private POSDialog posDialog;
	private LoginDialog loginDialog;
	
	static public void main(String[] args) {
		launch(); // Anwendung starten
	}

	@Override
	public void start(Stage stage) throws Exception {
		produkte = initialisiereProdukte();
		kunden = initialisiereKunden();
		showLoginDialog();
	}

	/**
	 * Erzeugt eine Instanz von POSDialog, setzt für diese die benötigten 
	 * Ereignis-Handler und zeigt den Dialog an.
	 * Aufgabenteilung:
	 * - POSDialog zeigt den Dialog an und erlaubt Eingaben
	 * - die Reaktion auf Eingaben wird *hier* vorgenommen, 
	 *   indem entsprechende Lambda-Ausdrücke definiert werden, 
	 *   die zu den geforderten Interfaces passen:
	 * 		- Kundenauswahl (Interface POSDialogKundenauswahlHandler)
	 * 		- Checkoutbutton geklickt (Interface POSDialogCheckoutHandler)
	 * 		- "Dialog schliessen"-Button geklickt (Interface POSDialogCloseHandler)
	 * 		- Produktbutton geklickt (Interface POSDialogProduktButtonHandler)
	 *   Die Lambdas werden hier definiert und dem POSDialog bekannt gemacht (per Setter-Aufruf)
	 */
	private Rechnung rechnung;
	
	//showPosDialog
	private void showPOSDialog() throws OutOfStockException, BookingException {
		/**
		 * Hier bitte eigenen Code einfügen
		 */
		posDialog = new POSDialog(produkte, kunden);
		
		//Kundenauswahl
		POSDialogKundenauswahlHandler kundenauswahlHandler = e -> {
			if(e != null) {
				rechnung = new Rechnung(e);
			} else {
				rechnung = new Rechnung();
			}
			
			posDialog.setRechnungTextAreaText(rechnung.toString());
			posDialog.setProduktauswahlModus();
		};
		posDialog.setKundenauswahlHandler(kundenauswahlHandler);
		
		//Checkoutbutton
		POSDialogCheckoutHandler checkoutHandler = () -> {
			rechnung.buchen();
			posDialog.getKundenauswahlButton().getSelectionModel().selectFirst();
			posDialog.setKundenauswahlModus();
		};
		posDialog.setCheckoutHandler(checkoutHandler);
		
		//Dialog schließen
		POSDialogCloseHandler closeHandler = () -> {
			try { showLoginDialog(); } catch (BookingException | OutOfStockException e) { System.out.println(e); }
			posDialog.hide();
		};
		posDialog.setCloseHandler(closeHandler);
		
		//Produktbutton
		POSDialogProduktButtonHandler produktButtonHandler = e -> {
			if(e.getProdukt() instanceof Artikel) {
				try {
					rechnung.addRechnungsposition(1, e.getProdukt());
					posDialog.setRechnungTextAreaText(rechnung.toString());
					if((((Artikel)e.getProdukt()).getLagerbestand() - rechnung.getRechnungsposition(e.getProdukt()).getAnzahl()) < 1) {
						posDialog.setProduktOff(e);
					}
				}
				catch (OutOfStockException | BookingException exc) {
					posDialog.setProduktOff(e);
				}
			} else {
				try {					
					rechnung.addRechnungsposition(1, e.getProdukt());
					posDialog.setRechnungTextAreaText(rechnung.toString());
				}

				catch (OutOfStockException | BookingException exc) {	}
			}
		};
		posDialog.setProduktButtonHandler(produktButtonHandler);
		
		//start App
		posDialog.setKundenauswahlModus();
		posDialog.setCheckoutOff();
		posDialog.setProduktauswahlOff();
		
		posDialog.show();
	}
	
	//showLoginDialog
	private void showLoginDialog() throws OutOfStockException, BookingException {
		loginDialog = new LoginDialog();
		
		//setHandler
		LoginHandler loginHandler = (b, p) -> {
			if(b.trim().equals("")) {
				loginDialog.setFehler("Bitte geben Sie einen Benutzernamen ein!");
			} else if(p.trim().equals("")) {
				loginDialog.setFehler("Bitte geben Sie ein Passwort ein!");
			} else if(b.equals("Hugo") && p.equals("123")) {
				loginDialog.close();
				try {
					showPOSDialog();
				} catch(OutOfStockException e) {
					
				} catch(BookingException e) {
					
				}
			} else if(b.equals("Admin") && p.equals("123")) {
				loginDialog.close();
				showLagerbestaendeSichtenDialog();
			} else {
				loginDialog.setFehler("Ungültige Benutzername/Passwort-Kombination!");
			}
		};
		loginDialog.setLoginHandler(loginHandler);
		
		//start loginDialog
		loginDialog.show();
	}
	
	//showLagerbestaendeSichtenDialog
	private void showLagerbestaendeSichtenDialog() {
		LagerbestaendeSichtenDialog lagerDialog = new LagerbestaendeSichtenDialog(produkte);
		
		//setHandler
		LagerbestaendeSichtenDialogCloseHandler closeHandler = () -> {
			try { showLoginDialog(); } catch (BookingException | OutOfStockException e) { System.out.println(e); }
			lagerDialog.hide();
		};
		lagerDialog.setCloseHandler(closeHandler);
		
		//start lagerDialog
		lagerDialog.show();
	}
	


	/**
	 * Erzeugt und initialisiert eine Kundenliste und gibt diese zurück
	 * @return Kundenliste
	 */
	private List<Kunde> initialisiereKunden() {
		List<Kunde> kunden = new ArrayList<>();
		
		kunden.add(new Kunde(-1, "<bitte auswählen>", "", ""));
		kunden.add(new Kunde(0, "Barverkauf", "", ""));
		kunden.add(new Kunde(1, "Madonna", "Sunset Boulevard 1", "Hollywood"));
		kunden.add(new Kunde(2, "Heidi Klum", "Modelwalk 13", "L.A."));				

		return kunden;
	}
	
	/**
	 * Erzeugt und initialisiert eine Produktliste und gibt diese zurück
	 * @return Produktliste
	 */
	private List<Produkt> initialisiereProdukte() {
		List<Produkt> produkte = new LinkedList<>();
		
		Artikel p1 = new Artikel(12345, "Arbeitsplatte A1", 89.90);
		p1.einlagern(1);
		Dienstleistung p2 = new Dienstleistung(100, "Küchenmontage", 75., "h");
		Artikel p3 = new Artikel(98989876, "Akku-Handsauger", 129.90);
		p3.einlagern(3);
		Artikel p4 = new Artikel(5261, "Spax 6x100", 3.99);
		p4.einlagern(4);
		Artikel p5 = new Artikel(4593, "Coca Cola 12x1l", 12.69);
		p5.einlagern(5);
		Artikel p6 = new Artikel(4594, "Capri-Sonne", 8.99);
		p6.einlagern(6);
		Artikel p7 = new Artikel(4595, "Jever Partyfass 5l", 8.99);
		p7.einlagern(7);
		Artikel p8 = new Artikel(12346, "Arbeitsplatte A2", 99.90);
		p8.einlagern(1);
		Artikel p9 = new Artikel(526, "Laminatbodenpack", 13.99);
		p9.einlagern(5);
		Dienstleistung p10 = new Dienstleistung(123, "Parkettmontage", 75.00, "qm");
		Dienstleistung p11 = new Dienstleistung(128, "Montage Sockelleisten", 5.59, "lfdm");

		produkte.add(p1);
		produkte.add(p2);
		produkte.add(p3);
		produkte.add(p4);
		produkte.add(p5);
		produkte.add(p6);
		produkte.add(p7);
		produkte.add(p8);
		produkte.add(p9);
		produkte.add(p10);
		produkte.add(p11);
		
		return produkte;
	}

}
