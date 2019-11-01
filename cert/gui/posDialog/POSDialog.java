package gui.posDialog;

import java.util.Collections;
import java.util.List;

import exceptions.BookingException;
import exceptions.OutOfStockException;
import geschaeftsobjekte.Artikel;
import geschaeftsobjekte.Kunde;
import geschaeftsobjekte.Produkt;
import gui.loginDialog.LoginDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse POSDialog
 * Initialisiert und zeigt einen POS-Dialog zur Auswahl der Produkte (Dienstleistungen und 
 * Artikel) an. 
 */
public class POSDialog {
	private Stage stage;

	private POSDialogCloseHandler closeHandler;
	private POSDialogKundenauswahlHandler kundenauswahlHandler;
	private POSDialogCheckoutHandler checkoutHandler;
	private POSDialogProduktButtonHandler produktButtonHandler;
	
	private List<Kunde> kunden;
	private List<Produkt> produkte;
	
	public POSDialog(List<Produkt> produkte, List<Kunde> kunden)  throws OutOfStockException, BookingException {
		this.kunden = kunden;
		this.produkte = produkte;

		initDialog();
		
		stage.setWidth(820.);
		stage.setHeight(700.);
		stage.initModality(Modality.APPLICATION_MODAL);
		
	}

	/**
	 * Initialisiert den Dialog, d.h.
	 * - erzeugt die benötigten Panes und UI Controls
	 * - kümmert sich um das Layout, d.h. verbindet die Panes und Controls entsprechend
	 * - installiert die von aussen (der App-Klasse) gesetzen Methoden für die Ereignisbehandlung
	 */	
	private TextArea rechnungTextArea;
	private ChoiceBox<String> kundenauswahl;
	private Button checkoutButton;
	private GridPane produktauswahl;
	
	private void initDialog() {
		/**
		 * Bitte vervollständigen
		 */
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, 820, 700);
		pane.setPrefWidth(scene.getWidth());
		stage = new Stage();
		stage.setTitle("Kasse");
		BorderPane paneLeft = new BorderPane();
		paneLeft.setPrefWidth(pane.getWidth()*0.45);
		paneLeft.setPrefHeight(scene.getHeight());
		BorderPane paneRight = new BorderPane();
		paneRight.setPrefWidth(pane.getWidth()*0.55);
		paneRight.setPrefHeight(scene.getHeight());
		pane.setLeft(paneLeft);
		pane.setRight(paneRight);
		
		//ChoiceBox für Kundenauswahl
		kundenauswahl = new ChoiceBox<>();
		kundenauswahl.setPrefWidth(pane.getWidth()/2);
		for(Kunde k: kunden) {
			kundenauswahl.getItems().add(k.getName());
		}
		kundenauswahl.getSelectionModel().selectFirst();
		paneLeft.setTop(kundenauswahl);
		kundenauswahl.setOnAction(e -> {
			if (kundenauswahl.getSelectionModel().getSelectedItem().toString() != "<bitte auswählen>" && kundenauswahl.getSelectionModel().getSelectedItem().toString() != "Barverkauf") {
				for (Kunde k: kunden) {
					if (kundenauswahl.getSelectionModel().getSelectedItem() == k.getName()) {
						kundenauswahlHandler.kundeAusgewaehlt(k);
					}
				}
			} else if (kundenauswahl.getSelectionModel().getSelectedItem().toString() == "Barverkauf") {
				kundenauswahlHandler.kundeAusgewaehlt(null);
			}
		});
		
		//Textarea
		rechnungTextArea = new TextArea();
		rechnungTextArea.setFont(new Font("Courier New", 14));
		rechnungTextArea.setEditable(false);
		paneLeft.setCenter(rechnungTextArea);
		
		//Checkout
		checkoutButton = new Button();
		checkoutButton.setText("CHECKOUT");
		checkoutButton.setPrefWidth(pane.getWidth()/2);
		paneLeft.setBottom(checkoutButton);
		checkoutButton.setOnAction(e -> {
			try {
			checkoutHandler.handleCheckout();
			}
			catch (BookingException | OutOfStockException exception) {
				
			}
		});
		
		//Produktauswahl (GridPane)
		produktauswahl = new GridPane();
		paneRight.setCenter(produktauswahl);
		produktauswahl.setAlignment(Pos.CENTER);
		produktauswahl.setHgap(10);
		produktauswahl.setVgap(10);
//		Collections.sort(produkte);
		produkte.sort(null);
		int i = 0;
		for (Produkt p: produkte) {
			//ProduktButton
			ProduktButton produktButton = new ProduktButton(p);
			produktButton.setOnAction(e -> {
				produktButtonHandler.handleClick(produktButton);
			});
			produktauswahl.add(produktButton, i%3, i/3);
			i++;
			if(p instanceof Artikel) {
				if(((Artikel)p).getLagerbestand() < 1) {
					produktButton.setDisable(true);
				}
			}
		}
		

		
		//schließen
		stage.setOnCloseRequest(e -> {
			closeHandler.handleCloseEvent();
		});
		
		stage.setScene(scene);
	}
	
	/**
	 * Zeigt den Dialog an
	 */
	public void show() {
		/**
		 * Bitte vervollständigen
		 */
		stage.show();
	}

	/**
	 * Verbirgt den Dialog
	 */
	public void hide() {
		/**
		 * Bitte vervollständigen
		 */
		//LoginDialog loginDialog = new LoginDialog();
		//loginDialog.show();
		stage.hide();
	}

	/**
	 * ab hier bitte Getter und Setter für alle Attribute anfügen,
	 * so dass die Ereignisbehandlung die Dialog-Elemente von aussen 
	 * nutzen kann.
	 */
	
	//Handler Setter Getter
	public void setCloseHandler(POSDialogCloseHandler closeHandler) {
		this.closeHandler = closeHandler;
	}
	
	public POSDialogCloseHandler getCloseHandler() {
		return closeHandler;
	}
	
	public void setKundenauswahlHandler(POSDialogKundenauswahlHandler kundenauswahlHandler) {
		this.kundenauswahlHandler = kundenauswahlHandler;
	}
	
	public POSDialogKundenauswahlHandler getKundenauswahlHandler() {
		return kundenauswahlHandler;
	}
	
	public void setCheckoutHandler(POSDialogCheckoutHandler checkoutHandler) {
		this.checkoutHandler = checkoutHandler;
	}
	
	public POSDialogCheckoutHandler getCheckoutHandler() {
		return checkoutHandler;
	}
	
	public void setProduktButtonHandler(POSDialogProduktButtonHandler produktButtonHandler) {
		this.produktButtonHandler = produktButtonHandler;
	}
	
	public POSDialogProduktButtonHandler getProduktButtonHandler() {
		return produktButtonHandler;
	}
	
	//Getter/Setter
	public void setRechnungTextAreaText(String text) {
		rechnungTextArea.setText(text);
	}
	
	public ChoiceBox<String> getKundenauswahlButton() {
		return kundenauswahl;
	}
	
	public Button getCheckoutButton() {
		return checkoutButton;
	}
	
	//Own Methods
	public void setKundenauswahlOff() {
		kundenauswahl.setDisable(true);
	}
	
	public void setKundenauswahlOn() {
		kundenauswahl.setDisable(false);
	}
	
	public void setCheckoutOff() {
		checkoutButton.setDisable(true);
	}
	
	public void setCheckoutOn() {
		checkoutButton.setDisable(false);
	}
	
	public void setProduktauswahlOff() {
		produktauswahl.setDisable(true);
	}
	
	public void setProduktauswahlOn() {
		produktauswahl.setDisable(false);
	}
	
	public void setProduktOff(ProduktButton produktButton) {
		produktButton.setDisable(true);
	}
	
	public void setKundenauswahlModus() {
		produktauswahl.setDisable(true);
		checkoutButton.setDisable(true);
		kundenauswahl.setDisable(false);
		rechnungTextArea.setDisable(true);
		rechnungTextArea.setText(null);
	}
	
	public void setProduktauswahlModus() {
		produktauswahl.setDisable(false);
		checkoutButton.setDisable(false);
		kundenauswahl.setDisable(true);
		rechnungTextArea.setDisable(false);
	}
	
}
