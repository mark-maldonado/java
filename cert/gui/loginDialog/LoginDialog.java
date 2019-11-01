package gui.loginDialog;

import gui.posDialog.POSDialogCloseHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginDialog {

	private LoginHandler loginHandler;
	
	//Konstruktor
	public LoginDialog() {
		initDialog();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setWidth(400.);
	}
	
	//Atribute für initDialog
	private Stage stage;
	private Label fehler;
	
	//init
	private void initDialog() {
		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane);
		stage = new Stage();
		stage.setTitle("WaWi Login");
		
		//TopPane
		BorderPane topPane = new BorderPane();
		pane.setTop(topPane);
		//-Benutzer
		FlowPane topPaneBenutzer = new FlowPane();
		topPane.setTop(topPaneBenutzer);
		Text benutzerName = new Text("Benutzername:    ");
		TextField benutzerText = new TextField();
		benutzerText.setOnKeyReleased(e -> {
			fehler.setText("");
		});
		topPaneBenutzer.getChildren().addAll(benutzerName, benutzerText);
		topPaneBenutzer.setPadding(new Insets(20, 0, 20, 0));
		topPaneBenutzer.setAlignment(Pos.CENTER);
		//-Passwort
		FlowPane topPanePasswort = new FlowPane();
		topPane.setBottom(topPanePasswort);
		Text passwortName = new Text("Passwort:             ");
		PasswordField passwortText = new PasswordField();
		passwortText.setOnKeyReleased(e -> {
			fehler.setText("");
		});
		topPanePasswort.getChildren().addAll(passwortName, passwortText);
		topPanePasswort.setPadding(new Insets(0, 0, 20, 0));
		topPanePasswort.setAlignment(Pos.CENTER);
		
		//MiddlePane
		fehler = new Label();
		fehler.setTextFill(Color.web("red"));
		pane.setCenter(fehler);
		
		//BottomPane
		FlowPane buttonPane = new FlowPane();
		Button button = new Button("Login");
		buttonPane.getChildren().add(button);
		button.setPrefWidth(300);
		button.setPrefHeight(35);
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.setPadding(new Insets(20));
		pane.setBottom(buttonPane);
		button.setOnAction(e -> {
			loginHandler.checkLogin(benutzerText.getText(), passwortText.getText());
		});
		
		stage.setScene(scene);
	}
	
	//Handler Setter Getter
	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}
	
	//Methoden
	public void show() {
		stage.show();
	}
	
	public void close() {
		stage.hide();
	}
	
	//Getter Setter
	public void setFehler(String s) {
		fehler.setText(s);
	}
}
