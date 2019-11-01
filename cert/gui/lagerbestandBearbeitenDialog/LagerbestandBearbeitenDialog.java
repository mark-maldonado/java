package gui.lagerbestandBearbeitenDialog;

import exceptions.OutOfStockException;
import geschaeftsobjekte.Artikel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LagerbestandBearbeitenDialog {
	private Artikel artikel;
	private Stage stage;
	
	private LagerbestandBearbeitenDialogCloseHandler closeHandler;
	
	//Konstruktor
	public LagerbestandBearbeitenDialog(Artikel artikel) {
		this.artikel = artikel;
		
		initDialog();
		stage.initModality(Modality.APPLICATION_MODAL);
	}
	
	//Attribute für initDialog
	TextField fifthTextRight;
	
	//initDialog
	private void initDialog() {
		Pane pane = new Pane();
		VBox box = new VBox();
		pane.getChildren().add(box);
		Scene scene = new Scene(pane);
		
		stage = new Stage();
		stage.setTitle("Lagerbestand bearbeiten");
		
		//firstPane
		BorderPane firstPane = new BorderPane();
		firstPane.setPadding(new Insets(10));
			FlowPane firstPaneLeft = new FlowPane();
			firstPane.setLeft(firstPaneLeft);
			firstPaneLeft.setAlignment(Pos.CENTER);
				Text firstTextLeft = new Text("Nummer");
				firstPaneLeft.getChildren().add(firstTextLeft);
			FlowPane firstPaneRight = new FlowPane();
			firstPane.setRight(firstPaneRight);
				Text firstTextRight = new Text(artikel.getNummer() + "");
				firstPaneRight.getChildren().add(firstTextRight);
				
		//secondPane
		BorderPane secondPane = new BorderPane();
		secondPane.setPadding(new Insets(10));
			FlowPane secondPaneLeft = new FlowPane();
			secondPane.setLeft(secondPaneLeft);
			secondPaneLeft.setAlignment(Pos.CENTER);
				Text secondTextLeft = new Text("Bezeichung");
				secondPaneLeft.getChildren().add(secondTextLeft);
			FlowPane secondPaneRight = new FlowPane();
			secondPane.setRight(secondPaneRight);
				Text secondTextRight = new Text(artikel.getBezeichnung());
				secondPaneRight.getChildren().add(secondTextRight);
			
		//thirdPane
		BorderPane thirdPane = new BorderPane();
		thirdPane.setPadding(new Insets(10));
			FlowPane thirdPaneLeft = new FlowPane();
			thirdPane.setLeft(thirdPaneLeft);
			thirdPaneLeft.setAlignment(Pos.CENTER);
				Text thirdTextLeft = new Text("Kurzbezeichnung");
				thirdPaneLeft.getChildren().add(thirdTextLeft);
			FlowPane thirdPaneRight = new FlowPane();
			thirdPane.setRight(thirdPaneRight);
				Text thirdTextRight = new Text(artikel.getKurzbezeichnung());
				thirdPaneRight.getChildren().add(thirdTextRight);
		
		//forthPane		
		BorderPane forthPane = new BorderPane();
		forthPane.setPadding(new Insets(10));
			FlowPane forthPaneLeft = new FlowPane();
			forthPane.setLeft(forthPaneLeft);
			forthPaneLeft.setAlignment(Pos.CENTER);
				Text forthTextLeft = new Text("Alter Lagerbestand");
				forthPaneLeft.getChildren().add(forthTextLeft);
			FlowPane forthPaneRight = new FlowPane();
			forthPane.setRight(forthPaneRight);
				Text forthTextRight = new Text(artikel.getLagerbestand() + "");
				forthPaneRight.getChildren().add(forthTextRight);
				
		//forthPane		
		BorderPane fifthPane = new BorderPane();
		fifthPane.setPadding(new Insets(10));
			FlowPane fifthPaneLeft = new FlowPane();
			fifthPane.setLeft(fifthPaneLeft);
			fifthPaneLeft.setAlignment(Pos.CENTER);
				Text fifthTextLeft = new Text("Neuer Lagerbestand");
				fifthPaneLeft.getChildren().add(fifthTextLeft);
			FlowPane fifthPaneRight = new FlowPane();
			fifthPane.setRight(fifthPaneRight);
				fifthTextRight = new TextField();
				fifthPaneRight.getChildren().add(fifthTextRight);
		
		box.getChildren().addAll(firstPane, secondPane, thirdPane, forthPane, fifthPane);
		stage.setScene(scene);
		stage.setOnCloseRequest(e -> {
			hide();
		});
	}
	
	//Methoden
	public void show() {
		stage.show();
	}
	
	
	public void hide() {
		int lagerNeu = artikel.getLagerbestand();
		
		outter:
		if(fifthTextRight.getText().length() > 0) {
			for(int i = 0; i < fifthTextRight.getText().length(); i++) {
				if(!Character.isDigit(fifthTextRight.getText().charAt(i))) {
					break outter;
				}
			}
			lagerNeu = Integer.parseInt(fifthTextRight.getText());
		}
			
		closeHandler.handleCloseEvent(lagerNeu);
		stage.hide();
	}
	
	public void setCloseHandler(LagerbestandBearbeitenDialogCloseHandler closeHandler) {
		this.closeHandler = closeHandler;
	}
}
