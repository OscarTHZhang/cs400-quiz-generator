package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Result extends Application {
	public void start(Stage primaryStage) {
		try {
			BorderPane root = setBorderPane();
			
			Scene scene = new Scene(root,400,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Add New Qestions");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private BorderPane setBorderPane() {
		Text answeredQText = new Text("Number of Correct Questions: 93 / 100");
		Text correctQText = new Text("Number of Answered Questions: 98 / 100");
		
		
		Button takeNewQuiz = new Button("Take New Quiz");
		Button saveAns = new Button("Save Answers to a JSON file");
		
		Button exit = new Button("Exit without Saving");
		exit.setOnAction(e -> Platform.exit());
		
		VBox buttons = new VBox();
		buttons.getChildren().addAll(takeNewQuiz, saveAns, exit);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(10);
		
		VBox list = new VBox();
		list.getChildren().addAll(answeredQText, correctQText, buttons);
		list.setAlignment(Pos.CENTER); 
		list.setSpacing(20);
		
		BorderPane root = new BorderPane();
		root.setCenter(list);
		root.setPadding(new Insets(15, 20, 10, 20));
		
		return root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

