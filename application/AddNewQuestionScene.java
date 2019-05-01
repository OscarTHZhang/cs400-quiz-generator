/**
 * Project: CS 400 Final Project
 * Name:	Quiz Generator
 * A-team:	#23
 * 
 * Credit:
 * 
 */

package application;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class represents the window to add new questions
 * 
 * @author Haochen Shi
 *
 */
public class AddNewQuestionScene {

	private Stage stage; // to get access to the current stage
	private double hSpacing = 10; // specify the horizontal spacing between elements in HBox

	/**
	 * This constructor passes the primary stage into the scene
	 * 
	 * @param primaryStage is the primary stage
	 */
	public AddNewQuestionScene(Stage primaryStage) {
		stage = primaryStage;
	}

	/**
	 * This method returns the add new question scene
	 * 
	 * @return the add new question scene
	 */
	public Scene getScene() {
		BorderPane root = setBorderPane();
		Scene scene = new Scene(root, 400, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	/**
	 * This helper method sets the elements and the layout in the border pane
	 * 
	 * @return the border pane
	 */
	private BorderPane setBorderPane() {
		Question newQ = new Question();
			
		// labels
		Label topicLabel = new Label("Topic: ");
		Label enterNewTopicLabel = new Label("OR Enter a new topic: ");
		Label questionLabel = new Label("Question: ");
		Label choiceALabel = new Label("Choice A: ");
		Label choiceBLabel = new Label("Choice B: ");
		Label choiceCLabel = new Label("Choice C: ");
		Label choiceDLabel = new Label("Choice D: ");
		Label choiceELabel = new Label("Choice E: ");

		// combo box
		ObservableList<String> options = FXCollections.observableArrayList(MainMenuScene.TOPIC);
		options.add(""); // add a empty string to the options
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ComboBox topicMenu = new ComboBox(options);

		// text fields and text area
		TextField newTopicText = new TextField();

		TextArea questionArea = new TextArea();
		questionArea.setPrefHeight(70);
		questionArea.setPrefWidth(250);

		// choice texts
		TextField choiceAText = new TextField(); 
		TextField choiceBText = new TextField();
		TextField choiceCText = new TextField();
		TextField choiceDText = new TextField();
		TextField choiceEText = new TextField();
		
		TextField[] choiceTexts = {choiceAText, choiceBText, choiceCText, choiceDText, choiceEText};
		
		// check boxes
		CheckBox choiceABox = new CheckBox("Correct?");
		CheckBox choiceBBox = new CheckBox("Correct?");
		CheckBox choiceCBox = new CheckBox("Correct?");
		CheckBox choiceDBox = new CheckBox("Correct?");
		CheckBox choiceEBox = new CheckBox("Correct?");
		
		CheckBox[] choiceBoxes = {choiceABox, choiceBBox, choiceCBox, choiceDBox, choiceEBox};
		
		// Choice objects
		Choice choiceA = new Choice();
		Choice choiceB = new Choice();
		Choice choiceC = new Choice();
		Choice choiceD = new Choice();
		Choice choiceE = new Choice();
		
		Choice[] choices = {choiceA, choiceB, choiceC, choiceD, choiceE};
						
		// Buttons
		Button back = new Button();
		back.setText("SAVE & BACK");
		// create a new instance of MainMenuScene and set the button's action
		MainMenuScene mainMenu = new MainMenuScene(stage);
		back.setOnAction(e -> {		
			if (getQTopic(newQ, topicMenu, newTopicText) && 
					getQDescription(newQ, questionArea) && 
					getChoices(newQ, choices, choiceTexts, choiceBoxes)) {
				MainMenuScene.QUESTION_POOL.add(newQ); // update quesiton pool
				MainMenuScene.TOPIC.add(newQ.getTopic()); // update topic list
				stage.setScene(mainMenu.getScene());
				stage.show();
			}	
		});
		// This is the button to add more new questions
		Button addMore = new Button();
		addMore.setText("SAVE & ADD MORE");
		// create a new instance of AddNewQuestionScene and set the button's action
		addMore.setOnAction(e -> {
			if (getQTopic(newQ, topicMenu, newTopicText) && 
					getQDescription(newQ, questionArea) && 
					getChoices(newQ, choices, choiceTexts, choiceBoxes)) {
				// add a new question to the question pool
				MainMenuScene.QUESTION_POOL.add(newQ); 
				MainMenuScene.TOPIC.add(newQ.getTopic()); // update topic list
				stage.setScene(this.getScene());
				stage.show();
			}	
		});
		
		// This is the button to upload the picture
		Button uploadPic = new Button();
		uploadPic.setText("UPLOAD YOUR PICTURE HERE");
		uploadPic.setOnAction(e -> {
			Image newImage = chooseImage();
			if (newImage != null) {
				newQ.setImg(newImage);
			}
		});

		// HBoxes
		HBox existingTopic = new HBox();
		existingTopic.getChildren().addAll(topicLabel, topicMenu);
		existingTopic.setAlignment(Pos.CENTER_LEFT); // alignment
		existingTopic.setSpacing(hSpacing); // horizontal spacing

		HBox newTopic = new HBox();
		newTopic.getChildren().addAll(enterNewTopicLabel, newTopicText);
		newTopic.setAlignment(Pos.CENTER_LEFT);
		newTopic.setSpacing(hSpacing);

		HBox question = new HBox();
		question.getChildren().addAll(questionLabel, questionArea);
		question.setAlignment(Pos.CENTER_LEFT);
		question.setSpacing(hSpacing);

		HBox choiceAHBox = new HBox();
		choiceAHBox.getChildren().addAll(choiceALabel, choiceAText, choiceABox);
		choiceAHBox.setAlignment(Pos.CENTER_LEFT);
		choiceAHBox.setSpacing(hSpacing);

		HBox choiceBHBox = new HBox();
		choiceBHBox.getChildren().addAll(choiceBLabel, choiceBText, choiceBBox);
		choiceBHBox.setAlignment(Pos.CENTER_LEFT);
		choiceBHBox.setSpacing(hSpacing);

		HBox choiceCHBox = new HBox();
		choiceCHBox.getChildren().addAll(choiceCLabel, choiceCText, choiceCBox);
		choiceCHBox.setAlignment(Pos.CENTER_LEFT);
		choiceCHBox.setSpacing(hSpacing);

		HBox choiceDHBox = new HBox();
		choiceDHBox.getChildren().addAll(choiceDLabel, choiceDText, choiceDBox);
		choiceDHBox.setAlignment(Pos.CENTER_LEFT);
		choiceDHBox.setSpacing(hSpacing);

		HBox choiceEHBox = new HBox();
		choiceEHBox.getChildren().addAll(choiceELabel, choiceEText, choiceEBox);
		choiceEHBox.setAlignment(Pos.CENTER_LEFT);
		choiceEHBox.setSpacing(hSpacing);

		HBox buttons = new HBox();
		buttons.getChildren().addAll(back, addMore);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(20, 0, 0, 0));
		buttons.setSpacing(20);
		
		// VBox
		VBox list = new VBox();
		list.getChildren().addAll(existingTopic, newTopic, question, 
				choiceAHBox, choiceBHBox, choiceCHBox, choiceDHBox, choiceEHBox, uploadPic, buttons);
		list.setAlignment(Pos.TOP_CENTER);
		list.setSpacing(15); // vertical spacing
				
		// set the border pane
		BorderPane root = new BorderPane();
		root.setCenter(list);
		root.setPadding(new Insets(20, 20, 20, 20)); // padding of the border pane

		return root;
	}

	/**
	 * Choose a file from local, read in .json file and store the questions in the
	 * program. To be implemented.
	 * 
	 * @return an image object that is uploaded from the local
	 */
	private Image chooseImage() {
		// a FileChooser to select the picture
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload your picture");
		File selectedPic = fileChooser.showOpenDialog(stage);
		if (selectedPic == null) {
			//throw new FileNotFoundException();
			return null;
		} else {
			return new Image(selectedPic.toURI().toString());
		}
	}
	
	/**
	 * Get the description of the question
	 * 
	 * @param newQ is the targeted question
	 * @param questionArea is the TextArea to enter the question description
	 * @return true if the description exists, false otherwise
	 */
	private boolean getQDescription(Question newQ, TextArea questionArea) {
		
		String newQDescription = questionArea.getText();
		if (newQDescription == null || newQDescription.equals("")) {
			showAlert("description");
			return false;
		} else {
			newQ.setDescription(newQDescription);
			return true;
		}
	}
	
	/**
	 * Get the topic of the question
	 * 
	 * @param newQ is the targeted question
	 * @param topicMenu is the ComboBox to select the topics
	 * @param newTopicText is the TextField to enter a new topic
	 * @return true if the topic exists, false otherwise
	 */
	@SuppressWarnings("rawtypes")
	private boolean getQTopic(Question newQ, ComboBox topicMenu, TextField newTopicText) {
		String existingQTopic = (String) topicMenu.getValue(); // an existing topic
		String newQTopic = newTopicText.getText(); // a new topic
		
		if (existingQTopic == null && newQTopic.equals("")) {
			showAlert("topic"); // at least one topic is required
			return false;
		} else if (existingQTopic != null && !newQTopic.equals("")) {
			showAlert("topicDuplicate"); // only one topic is allowed
			return false;
		} else if (existingQTopic != null) {
			newQ.setTopic(existingQTopic);
			return true;
		} else {
			newQ.setTopic(newQTopic);
			return true;
		}
	}
	
	/**
	 * Get the choices of the question
	 * 
	 * @param newQ is the targeted question
	 * @param choices is the array of choices
	 * @param choiceTexts is the array of TextFields to represent the choices
	 * @param choiceBoxes is the array of CheckBoxes to represent the correct answer
	 * @return true if the topics exist, false otherwise
	 */
	private boolean getChoices(Question newQ, Choice[] choices, 
			TextField[] choiceTexts, CheckBox[] choiceBoxes) {
		// set the basic information of the choices
		for (int i = 0; i < choices.length; i++) {
			String newQChoice = choiceTexts[i].getText();
			choices[i].setChoiceDescription(newQChoice);
			choices[i].setCorrect(choiceBoxes[i].isSelected());
		}
		
		// at least two choices are needed
		int choicesCount = 0;
		for (int i = 0; i < choices.length; i++) {
			if (!choices[i].getChoiceDescription().equals("")) {
				choicesCount++;
			}
		}
		if (choicesCount < 2) {
			showAlert("choices");
			return false;
		} 
		
		// at least one correct answer is correct
		boolean allFalse = true;
		for (int i = 0; i < choices.length; i++) {
			if (choices[i].isCorrect()) {
				allFalse = false;
				break;
			}
		}
		if (allFalse) {
			showAlert("choicesAllFalse");
			return false;
		} 
		
		newQ.setChoices(choices);	
		return true;
	}
	
	/**
	 * This method shows alerts to prompt user to do right behaviors
	 * 
	 * @param problem is the string that indicates the problem
	 */
	private void showAlert(String problem) {
		Text warningMessage = new Text();
		
		// judge which problem is existing
		switch(problem) {
			case "topic":
				warningMessage.setText("Please select at least one topic!");
				break;
			case "topicDuplicate":
				warningMessage.setText("Please enter only one topic!");
				break;
			case "description":
				warningMessage.setText("Please enter the question description!");
				break;
			case "choices":
				warningMessage.setText("Please enter at least two choices!");
				break;
			case "choicesAllFalse":
				warningMessage.setText("Please select at least one correct answer!");
				break;
			default:
				warningMessage.setText("Please!");
		}
		
		// set up a warning window
		Stage popUpStage = new Stage();
		BorderPane root = new BorderPane();
		root.setMaxSize(300, 100);

		// button "GOT IT"
		Button yes = new Button("GOT IT");
		yes.setOnAction(e -> popUpStage.close());

		// HBox to organize the layout
		HBox buttons = new HBox();
		buttons.getChildren().addAll(yes);
		buttons.setAlignment(Pos.CENTER);

		// VBox to organize the layout
		VBox list = new VBox();
		list.getChildren().addAll(warningMessage, buttons);
		list.setAlignment(Pos.CENTER);
		list.setSpacing(20);
		root.setCenter(list);
		root.setPadding(new Insets(15, 20, 10, 20));

		Scene warning = new Scene(root, 300, 100);
		popUpStage.setScene(warning);
		popUpStage.show();
	}
	
}
