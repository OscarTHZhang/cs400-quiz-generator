/**
 * Project: CS 400 Final Project 
 * Name: Quiz Generator 
 * A-team: #23
 * Members: Oscar Zhang, lec 002, tzhang383@wisc.edu
 * 			Haochen Shi, lec 001, hshi74@wisc.edu
 * 			Bradley Mao, lec 002, jmao43@wisc.edu
 * 			Peter Pan,	 lec 002, rpan33@wisc.edu
 * 
 * Credit:
 * for most of the implementation of java-fx -> http://www.java2s.com/example/java/javafx/
 * 
 */

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

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
			// set the icon of the program
			primaryStage.getIcons().add(new Image("file:icon.png"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method launches the application
	 * 
	 * @param args argument in the command line mode
	 */
	public static void main(String[] args) {
		System.out.println("Starting the application");
		launch(args);
	}

}
