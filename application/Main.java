package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MainMenuScene mainMenuWindow = new MainMenuScene(primaryStage);
			Scene mainMenu = mainMenuWindow.getScene();
			mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainMenu);
			primaryStage.setTitle("Quiz Generator");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
