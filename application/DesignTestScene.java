/**
 * Project: CS 400 Final Project
 * Name:	Quiz Generator
 * A-team:	#23
 * 
 * Credit:
 * 
 * 
 * Bugs: 
 */

package application;
	
import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This Application is the UI for Design Application
 * @author Oscar
 *
 */
public class DesignTestScene{
	
	private Stage stage;
	
	private VBox planeElement; // the vertical box that contains all the elements
	
	private HBox chooseTopic; // contains choosing list of all the topic options
	private HBox numQuestion; // contains the text field that shows number of questions you want to have
	private HBox options; // contains the options: cancel and start
	private HBox showTopic; // contains the chosen topics
	
	private Set<String> chosenTopic; // the list of topics that the user has chosen
	
	public DesignTestScene(Stage primaryStage) {
		stage = primaryStage;
		chosenTopic = new HashSet<String>();
	}
	
	public Scene getScene() {
		BorderPane root = new BorderPane();
		setLayout();
		root.setCenter(planeElement);
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
	/**
	 * private helper that sets the layout plan
	 * @throws Exception if there is an exception when initializing these boxes layout
	 */
	private void setLayout() {
		planeElement = new VBox(); // contains all the HBox
		planeElement.setAlignment(Pos.CENTER);
		planeElement.setSpacing(50.0);
		
		chooseTopic = new HBox();
		chooseTopic.setSpacing(15.0);
		chooseTopic.getChildren().add(new Label("Choosing the topic:"));
		
		// TODO: should not be able to add the same topic once again
		// this should be imported from another array list in the back end topic class
		ComboBox<String> topicList = new ComboBox<String>(
			FXCollections.observableArrayList(
				"topic 1",
				"topic 2",
				"topic 3",
				"...."	
			)
		);
		
		chooseTopic.getChildren().add(topicList);
		
		showTopic = new HBox();
		showTopic.setSpacing(5.0);
		
		// set up select button
		Button add = new Button("+");
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				chosenTopic.add(topicList.getValue());	
				showTopic.getChildren().add( new Label( topicList.getValue() ) );	
			}	
		});
		
		chooseTopic.getChildren().add(add);
		
		numQuestion = new HBox();
		numQuestion.setSpacing(15.0);
		numQuestion.getChildren().add(new Label("Number of questions:"));
		numQuestion.getChildren().add(new TextField());

		options = new HBox();
		options.setSpacing(50.0);
		
		Button cancel = new Button("CANCEL");
		MainMenuScene mainMenu = new MainMenuScene(stage);
		cancel.setOnAction(e -> {stage.setScene(mainMenu.getScene()); stage.show();});
		options.getChildren().add(cancel);
		
		Button start = new Button("START");
		QuestionScene question = new QuestionScene(stage);
		start.setOnAction(e -> {stage.setScene(question.getScene()); stage.show();});
		options.getChildren().add(start);
		
		// set the alignment of all the boxes
		
		chooseTopic.setAlignment(Pos.CENTER);
		numQuestion.setAlignment(Pos.CENTER);
		options.setAlignment(Pos.CENTER);
		showTopic.setAlignment(Pos.CENTER);
		
		// add the HBox into VBox
		planeElement.getChildren().add(chooseTopic);
		planeElement.getChildren().add(numQuestion);
		planeElement.getChildren().add(options);
		planeElement.getChildren().add(showTopic);
	}
	
}
