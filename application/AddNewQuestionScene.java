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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.imageio.ImageIO;

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
		Collections.sort(MainMenuScene.allallTopics);
		ObservableList<String> options = FXCollections.observableArrayList(
				MainMenuScene.allallTopics);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ComboBox topicMenu = new ComboBox(options);

		// text fields and text area
		TextField newTopicText = new TextField();

		TextArea questionArea = new TextArea();
		questionArea.setPrefHeight(70);
		questionArea.setPrefWidth(250);

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

		CheckBox[] choiceBoxes = { choiceABox, choiceBBox, choiceCBox, choiceDBox, choiceEBox };

		// Choice objects
		Choice choiceA = new Choice();
		Choice choiceB = new Choice();
		Choice choiceC = new Choice();
		Choice choiceD = new Choice();
		Choice choiceE = new Choice();

		Choice[] choices = { choiceA, choiceB, choiceC, choiceD, choiceE };

		// buttons
		Button back = new Button();
		back.setText("SAVE & BACK");
		// create a new instance of MainMenuScene and set the button's action
		MainMenuScene mainMenu = new MainMenuScene(stage);
		back.setOnAction(e -> {
			if (getQTopic(newQ, topicMenu, newTopicText) && getQDescription(newQ, questionArea)
					&& getChoices(newQ, choices, choiceTexts, choiceBoxes)) {
				MainMenuScene.questionPool.add(newQ); // update question pool
				if (!MainMenuScene.allallTopics.contains(newQ.getTopic())) {
					MainMenuScene.allallTopics.add(newQ.getTopic()); // update topic list
				}
				stage.setScene(mainMenu.getScene());
				stage.show();
			}
		});

		Button addMore = new Button();
		addMore.setText("SAVE & ADD MORE");
		// create a new instance of AddNewQuestionScene and set the button's action
		addMore.setOnAction(e -> {
			if (getQTopic(newQ, topicMenu, newTopicText) && getQDescription(newQ, questionArea)
					&& getChoices(newQ, choices, choiceTexts, choiceBoxes)) {
				MainMenuScene.questionPool.add(newQ);
				if (!MainMenuScene.allallTopics.contains(newQ.getTopic())) {
					MainMenuScene.allallTopics.add(newQ.getTopic()); // update topic list
				}
				stage.setScene(this.getScene());
				stage.show();
			}
		});

		// button to go back to the main menu scene
		Button cancel = new Button();
		cancel.setText("CANCEL");
		cancel.setOnAction(e -> {
			stage.setScene(mainMenu.getScene());
			stage.show();
		});

		// button to upload a picture from the local
		Button uploadPic = new Button();
		uploadPic.setText("UPLOAD YOUR PICTURE HERE");
		uploadPic.setOnAction(e -> {
			// call the helper method to get the image path
			String newImagePath = chooseImage();
			if (newImagePath != null) {
				newQ.setImgPath(newImagePath);
			}
		});

		// HBoxes
		
		// topic label
		HBox existingTopic = new HBox();
		existingTopic.getChildren().addAll(topicLabel, topicMenu);
		existingTopic.setAlignment(Pos.CENTER_LEFT); // alignment
		existingTopic.setSpacing(hSpacing); // horizontal spacing

		// enter new topic label
		HBox newTopic = new HBox();
		newTopic.getChildren().addAll(enterNewTopicLabel, newTopicText);
		newTopic.setAlignment(Pos.CENTER_LEFT);
		newTopic.setSpacing(hSpacing);

		// question 
		HBox question = new HBox();
		question.getChildren().addAll(questionLabel, questionArea);
		question.setAlignment(Pos.CENTER_LEFT);
		question.setSpacing(hSpacing);

		// choice A 
		HBox choiceAHBox = new HBox();
		choiceAHBox.getChildren().addAll(choiceALabel, choiceAText, choiceABox);
		choiceAHBox.setAlignment(Pos.CENTER_LEFT);
		choiceAHBox.setSpacing(hSpacing);

		// choice B 
		HBox choiceBHBox = new HBox();
		choiceBHBox.getChildren().addAll(choiceBLabel, choiceBText, choiceBBox);
		choiceBHBox.setAlignment(Pos.CENTER_LEFT);
		choiceBHBox.setSpacing(hSpacing);

		// choice C 
		HBox choiceCHBox = new HBox();
		choiceCHBox.getChildren().addAll(choiceCLabel, choiceCText, choiceCBox);
		choiceCHBox.setAlignment(Pos.CENTER_LEFT);
		choiceCHBox.setSpacing(hSpacing);

		// choice D 
		HBox choiceDHBox = new HBox();
		choiceDHBox.getChildren().addAll(choiceDLabel, choiceDText, choiceDBox);
		choiceDHBox.setAlignment(Pos.CENTER_LEFT);
		choiceDHBox.setSpacing(hSpacing);

		// choice E 
		HBox choiceEHBox = new HBox();
		choiceEHBox.getChildren().addAll(choiceELabel, choiceEText, choiceEBox);
		choiceEHBox.setAlignment(Pos.CENTER_LEFT);
		choiceEHBox.setSpacing(hSpacing);

		// buttons for confirmation
		HBox buttons = new HBox();
		buttons.getChildren().addAll(cancel, back, addMore);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(20, 0, 0, 0));
		buttons.setSpacing(20);

		// VBox for the HBoxes
		VBox list = new VBox();
		list.getChildren().addAll(existingTopic, newTopic, question, choiceAHBox, choiceBHBox, 
				choiceCHBox, choiceDHBox, choiceEHBox, uploadPic, buttons);
		
		list.setAlignment(Pos.TOP_CENTER);
		list.setSpacing(15); // vertical spacing

		// set the border pane
		BorderPane root = new BorderPane();
		root.setCenter(list);
		root.setPadding(new Insets(20, 20, 20, 20)); // padding of the border pane
		return root;
	}

	/**
	 * This method chooses an image from the local and store it in the program by
	 * its path
	 * 
	 * @return the path of the image
	 */
	private String chooseImage() {
		// a FileChooser to select the image
		try {
		        FileChooser fileChooser = new FileChooser();
		        fileChooser.setTitle("Upload your picture");
		        File selectedPic = fileChooser.showOpenDialog(stage);
		        if (selectedPic == null) {
		            return null;
		        } else {
		            // save file to local directory
		            String fileNameWOPath = selectedPic.getName();
		            String currentDirectory = System.getProperty("user.dir");
		            System.out.println("fileNameWOPath: "+fileNameWOPath);
		            System.out.println("currentDirectory: "+currentDirectory);
		            BufferedImage origImg = ImageIO.read(selectedPic);
		            File outputfile = new File(currentDirectory+"/"+fileNameWOPath);
		            ImageIO.write(origImg, "png", outputfile);
		            System.out.println("Image successfully written to path: "+currentDirectory+fileNameWOPath);
		            return selectedPic.toURI().toString(); // return the URI string
		        }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		return null;  // Not going to happen
	}

	/**
	 * This method gets the description of the question
	 * 
	 * @param newQ is the new question to be added
	 * @param questionArea is the TextArea to enter the question description
	 * @return true if the description is entered, false otherwise
	 */
	private boolean getQDescription(Question newQ, TextArea questionArea) {
		String newQDescription = questionArea.getText();
		if (newQDescription == null || newQDescription.equals("")) {
			showAlert("description"); // show alert window
			return false;
		} else {
			// set the description of the question
			newQ.setDescription(newQDescription);
			return true;
		}
	}

	/**
	 * This method gets the topic of the question
	 * 
	 * @param newQ is the new question to be added
	 * @param topicMenu is the ComboBox to select the topic
	 * @param newTopicText is the TextField to enter a new topic
	 * @return true if the topic is selected or entered, false otherwise
	 */
	@SuppressWarnings("rawtypes")
	private boolean getQTopic(Question newQ, ComboBox topicMenu, TextField newTopicText) {
		String existingQTopic = (String) topicMenu.getValue();
		String newQTopic = newTopicText.getText();
		// judge the two strings
		if (existingQTopic == null && newQTopic.equals("")) {
			showAlert("topic");
			return false;
		} else if (existingQTopic != null && !newQTopic.equals("")) {
			showAlert("topicDuplicate");
			return false;
		} else if (existingQTopic != null) {
			newQ.setTopic(existingQTopic);
			return true;
		} else {
			if (!MainMenuScene.allallTopics.contains(newQTopic)) {
				newQ.setTopic(newQTopic);
				return true;
			} else {
				// show alert if the entered new topic already exists
				showAlert("topicExisting");
				return false;
			}
		}
	}

	/**
	 * This method gets the choices of the question
	 * 
	 * @param newQ is the new question to be added
	 * @param choices is the array of choices
	 * @param choiceTexts is the array of TextFields
	 * @param choiceBoxes is the array of CheckBoxes
	 * @return true if the choices are entered, false otherwise
	 */
	private boolean getChoices(Question newQ, Choice[] choices, TextField[] choiceTexts, CheckBox[] choiceBoxes) {
		// set up the new choices
		for (int i = 0; i < choices.length; i++) {
			String newQChoice = choiceTexts[i].getText();
			choices[i].setChoiceDescription(newQChoice);
			choices[i].setCorrect(choiceBoxes[i].isSelected());
		}

		// at least two choices have to be entered
		int choicesCount = 0;
		for (int i = 0; i < choices.length; i++) {
			if (!choices[i].getChoiceDescription().equals("")) {
				choicesCount++;
			} else if (choicesCount < 2) {
				showAlert("choices");
				return false;
			}
		}
		
		// represent the final choices that are going to be added in the question
		Choice[] finalChoices = new Choice[choicesCount];
		for (int i = 0; i < choicesCount; i++) {
			finalChoices[i] = choices[i];
		}

		// at least one correct answer has to be entered
		boolean allFalse = true;
		for (int i = 0; i < choices.length; i++) {
			if (choices[i].isCorrect()) {
				allFalse = false;
				break;
			}
		}
		// show alert if all the choices are set to false (not chosen)
		if (allFalse) {
			showAlert("choicesAllFalse");
			return false;
		}

		newQ.setChoices(finalChoices);
		return true;
	}

	/**
	 * This method shows corresponding alert windows according to the problem
	 * 
	 * @param problem indicates the type of problem
	 */
	private void showAlert(String problem) {
		Text warningMessage = new Text();
		// judge which problem it is and set up the warning message accordingly
		switch (problem) {
			case "topic": // must select one topic 
				warningMessage.setText("Please select at least one topic!");
				break;
			case "topicDuplicate": // duplicate topic
				warningMessage.setText("Please enter only one topic!");
				break;
			case "description": // no description
				warningMessage.setText("Please enter the question description!");
				break;
			case "choices": // only one or less choice
				warningMessage.setText("Please enter at least two choices!");
				break;
			case "choicesAllFalse": // right choices not selected
				warningMessage.setText("Please select at least one correct answer!");
				break;
			case "topicExisting": // duplicate topic
				warningMessage.setText("The topic already exists!");
				break;
			default:
				warningMessage.setText("Please!");
		}

		// set up the pop up alert window
		Stage popUpStage = new Stage();
		BorderPane root = new BorderPane();
		root.setMaxSize(300, 100);

		// the button to close the pop-up window
		Button yes = new Button("GOT IT");
		yes.setOnAction(e -> popUpStage.close());

		// layout of the pop-up window
		HBox buttons = new HBox();
		buttons.getChildren().addAll(yes);
		buttons.setAlignment(Pos.CENTER);

		// warning message and alert UI
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
