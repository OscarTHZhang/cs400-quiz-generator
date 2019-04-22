package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class AddNewQuestion extends Application {
	
	private double hSpacing = 5;
	Scene scene;
	
	public Scene returnScene() {
	  return scene;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = setBorderPane();
			
			Scene scene1 = new Scene(root,400,400);
			scene = scene1;
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Add New Qestions");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private BorderPane setBorderPane() {
		Label topicLabel = new Label("Topic: ");
		Label enterNewTopicLabel = new Label("OR Enter a new topic: ");
		Label questionLabel = new Label("Question: ");
		Label choiceALabel = new Label("Choice A: ");
		Label choiceBLabel = new Label("Choice B: ");
		Label choiceCLabel = new Label("Choice C: ");
		Label choiceDLabel = new Label("Choice D: ");
		Label choiceELabel = new Label("Choice E: ");
		
		ObservableList<String> options = 
				FXCollections.observableArrayList(
			        "Option 1",
			        "Option 2",
			        "Option 3"
			    );
		@SuppressWarnings({"rawtypes", "unchecked"})
		ComboBox topicMenu = new ComboBox(options);
		
		TextField newTopicText = new TextField();
		TextArea questionArea = new TextArea("Descirbe your question here.");
		questionArea.setPrefHeight(70);
		questionArea.setPrefWidth(250);
		TextField choiceAText = new TextField();
		TextField choiceBText = new TextField();
		TextField choiceCText = new TextField();
		TextField choiceDText = new TextField();
		TextField choiceEText = new TextField();
		
		CheckBox choiceABox = new CheckBox("Correct?");
		CheckBox choiceBBox = new CheckBox("Correct?");
		CheckBox choiceCBox = new CheckBox("Correct?");
		CheckBox choiceDBox = new CheckBox("Correct?");
		CheckBox choiceEBox = new CheckBox("Correct?");
		
		Button back = new Button();
		back.setText("SAVE & BACK");
		Button addMore = new Button();
		addMore.setText("SAVE & ADD MORE");
		
		HBox existingTopic = new HBox();
		existingTopic.getChildren().addAll(topicLabel, topicMenu);
		existingTopic.setAlignment(Pos.CENTER_LEFT);
		existingTopic.setSpacing(hSpacing);
		
		HBox newTopic = new HBox();
		newTopic.getChildren().addAll(enterNewTopicLabel, newTopicText);
		newTopic.setAlignment(Pos.CENTER_LEFT);
		newTopic.setSpacing(hSpacing);
		
		HBox question = new HBox();
		question.getChildren().addAll(questionLabel, questionArea);
		question.setAlignment(Pos.CENTER_LEFT);
		question.setSpacing(hSpacing);
		
		HBox choiceA = new HBox();
		choiceA.getChildren().addAll(choiceALabel, choiceAText, choiceABox);
		choiceA.setAlignment(Pos.CENTER_LEFT);
		choiceA.setSpacing(hSpacing);
		
		HBox choiceB = new HBox();
		choiceB.getChildren().addAll(choiceBLabel, choiceBText, choiceBBox);
		choiceB.setAlignment(Pos.CENTER_LEFT);
		choiceB.setSpacing(hSpacing);
		
		HBox choiceC = new HBox();
		choiceC.getChildren().addAll(choiceCLabel, choiceCText, choiceCBox);
		choiceC.setAlignment(Pos.CENTER_LEFT);
		choiceC.setSpacing(hSpacing);
		
		HBox choiceD = new HBox();
		choiceD.getChildren().addAll(choiceDLabel, choiceDText, choiceDBox);
		choiceD.setAlignment(Pos.CENTER_LEFT);
		choiceD.setSpacing(hSpacing);
		
		HBox choiceE = new HBox();
		choiceE.getChildren().addAll(choiceELabel, choiceEText, choiceEBox);
		choiceE.setAlignment(Pos.CENTER_LEFT);
		choiceE.setSpacing(hSpacing);
		
		HBox buttons = new HBox();
		buttons.getChildren().addAll(back, addMore);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(20);
		
		VBox list = new VBox();
		list.getChildren().addAll(existingTopic, newTopic, question, 
				choiceA, choiceB, choiceC, choiceD, choiceE, buttons);
		list.setAlignment(Pos.TOP_CENTER); 
		list.setSpacing(7);
		
		BorderPane root = new BorderPane();
		root.setCenter(list);
		root.setPadding(new Insets(15, 20, 10, 20));
		
		return root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
