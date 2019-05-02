/**
 * Project: CS 400 Final Project
 * Name:	Quiz Generator
 * A-team:	#23
 * 
 * Credit:
 * 
 */

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * This class works as a scene controller of the GUI
 * 
 * @author Haochen Shi
 *
 */
public class Main extends Application {

	/**
	 * This method sets up the application with the main menu scene
	 * 
	 * @param priamryStage is the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// get access to the main menu scene by creating an instance of MainMenuScene
			MainMenuScene mainMenuWindow = new MainMenuScene(primaryStage);
			Scene mainMenu = mainMenuWindow.getScene();
			mainMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(mainMenu);
			primaryStage.setTitle("Quiz Generator");
			// disallow resizing to make the layout more elegant
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("file:icon.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method launches the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("");
		launch(args);
	}

}
