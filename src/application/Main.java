package application;
	
import Cliet.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import server.Server;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
//p176026@nu.edu.pk
//check_c = Server.check_connected(Client.get_ID());
//if(check_c)
//p171040@nu.edu.pk
public class Main extends Application {
	static BorderPane root ;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("root.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("cant load");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
