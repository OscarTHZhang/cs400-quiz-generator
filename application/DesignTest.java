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
	
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This Application is the UI for Design Application
 * @author Oscar
 *
 */
public class DesignTest extends Application {
	
	private VBox planeElement; // the vertical box that contains all the elements
	
	private HBox chooseTopic; // contains choosing list of all the topic options
	private HBox numQuestion; // contains the text field that shows number of questions you want to have
	private HBox options; // contains the options: cancel and start
	private HBox showTopic; // contains the chosen topics
	
	private Set<String> chosenTopic; // the list of topics that the user has chosen
	
	public DesignTest() {
		super();
		chosenTopic = new HashSet<String>();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			setLayout();
			
			
			root.setCenter(planeElement);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Design Your Test");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * private helper that sets the layout plan
	 * @throws Exception if there is an exception when initializing these boxes layout
	 */
	private void setLayout() throws Exception {
		planeElement = new VBox(); // contains all the HBox
		planeElement.setAlignment(Pos.CENTER);
		planeElement.setSpacing(50.0);
		
		
		chooseTopic = new HBox();
		chooseTopic.setSpacing(15.0);
		chooseTopic.getChildren().add(new Label("Choose the topic:"));
		
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
			
			/**
			 * 
			 */
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
		options.getChildren().add(new Button("CANCEL"));
		options.getChildren().add(new Button("START"));
		
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
