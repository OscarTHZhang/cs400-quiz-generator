package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultScene {
	
	private Stage stage;
	
	public ResultScene(Stage primaryStage) {
		stage = primaryStage;
	}
	
	public Scene getScene() {
		BorderPane root = setBorderPane();	
		Scene scene = new Scene(root,400,200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
	private BorderPane setBorderPane() {
		Text answeredQText = new Text("Number of Correct Questions: " + 
				QuestionScene.correctQuestionCount + " / " + QuestionScene.questionCount);
		Text correctQText = new Text("Number of Answered Questions: " +
				QuestionScene.finishedQuestionCount + " / " + QuestionScene.questionCount);
		
		
		Button takeNewQuiz = new Button("Take New Quiz");
		MainMenuScene mainMenu = new MainMenuScene(stage);
		takeNewQuiz.setOnAction(e -> {stage.setScene(mainMenu.getScene()); stage.show();});
		
		Button saveAns = new Button("Save Answers to a JSON file");
		
		Button exit = new Button("Exit without Saving");
		exit.setOnAction(e -> {
			Stage popUpStage = new Stage();
            BorderPane root = new BorderPane();
            root.setMaxSize(200, 100);
            
            Text warningMessage = new Text("Are you sure you want to quit?");
            
            Button no = new Button("NO");
            no.setOnAction(e2 -> popUpStage.close());
            Button yes = new Button("YES");
            yes.setOnAction(e2 -> Platform.exit());
            
            HBox buttons = new HBox();
            buttons.getChildren().addAll(no, yes);
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(20);
            
            VBox list = new VBox();
            list.getChildren().addAll(warningMessage, buttons);
            list.setAlignment(Pos.CENTER); 
            list.setSpacing(20);
            root.setCenter(list);
            root.setPadding(new Insets(15, 20, 10, 20));

            Scene warning = new Scene(root, 250, 200);
            popUpStage.setScene(warning);
            popUpStage.show();
		});
		
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
	
}

