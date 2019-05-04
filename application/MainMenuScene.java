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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The main menu of the Quiz Generator.
 * 
 * @author Rui Pan
 */
public class MainMenuScene {

	private Stage stage; // the stage of the scene

	protected static Quiz overallQuiz =  new Quiz();// a quiz object. See Quiz.java
	protected static List<Question> questionPool = new ArrayList<Question>(); // all
																	// questions
	protected static List<String> allallTopics = new ArrayList<String>();// all topics

	/**
	 * set the pool of the static fields whenever needed, such as ending of another scene
	 */
	protected static void setPool() {
		// initialize these statics
		overallQuiz = new Quiz();
		questionPool = new ArrayList<Question>();
		allallTopics = new ArrayList<String>();
	}
	
	/**
	 * Add new topics to the topic list if there are questions in the question
	 * pool with a new topic.
	 */
	protected static void fillTopic() {
		for (Question q : questionPool) {
			// if this is a topic that is not in the TOPIC list, then add this
			// topic
			if (!allallTopics.contains(q.getTopic())) {
				allallTopics.add(q.getTopic());
			}
		}
	}

	/**
	 * This constructor passes the primary stage into the scene
	 * 
	 * @param primaryStage is the primary stage
	 */
	public MainMenuScene(Stage primaryStage) {
		//setPool();
		stage = primaryStage;
	}

	/**
	 * This method returns the result scene
	 * 
	 * @return the result scene
	 */
	public Scene getScene() {
		BorderPane root = setBorderPane();
		Scene scene = new Scene(root, 400, 500);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	/**
	 * This helper method sets the elements and the layout in the border pane
	 * 
	 * @return the border pane
	 */
	private BorderPane setBorderPane() {
		BorderPane root = new BorderPane();

		Label title = new Label("Quiz Generator"); // set the main title
		title.setFont(new Font("Helvetica", 32)); // make the main title looks bigger

		int numQ = questionPool.size();

		Label numQuestions = new Label("Available Questions: " + numQ); 
		// display the number of available questions

		Button startQuizButton = createNewButt("Start Quiz", 150, 100, new Font("Helvetiva", 18));
		DesignTestScene design = new DesignTestScene(stage);
		startQuizButton.setOnAction(e -> {
			stage.setScene(design.getScene());
			stage.show();
		});

		// put the title, numQuestions and the start quiz button in a VBox, set
		// the VBox
		// in the center of the BorderPane
		VBox start = new VBox();
		start.getChildren().addAll(title, startQuizButton, numQuestions);
		start.setAlignment(Pos.CENTER);
		start.setSpacing(30);
		root.setCenter(start);

		// generate three buttons for different functions
		Button loadFile = createNewButton("Load File");
		loadFile.setOnAction(e -> chooseFile());
		Button addQuestion = createNewButton("Add Questions");
		AddNewQuestionScene add = new AddNewQuestionScene(stage);
		addQuestion.setOnAction(e -> {
			stage.setScene(add.getScene());
			stage.show();
		});

		// functionalities of saveFile are in saveFileToLocal().
		Button saveToLocal = createNewButton(
				"Save Current Questions to Local File");
		saveToLocal.setOnAction(e -> saveFileToLocal());
		Button exit = createNewButton("Exit");
		
		setExitButton(exit); // call the helper to set the button functionality
		
		// wrapper for all the UI controls for the main scene
		VBox buttons = new VBox();
		buttons.getChildren().addAll(loadFile, addQuestion, saveToLocal, exit);
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);

		// set the root
		root.setBottom(buttons);
		root.setPadding(new Insets(20, 20, 30, 20));
		return root;
	}
	
	/**
	 * helper method that sets the functionality of exit button
	 * @param exit button that is going to be set
	 */
	private void setExitButton(Button exit) {
		// The implementation of the functionalities of the exit button.
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// set a pop-up to remind user does he really want to quit the program
				Stage popUpStage = new Stage();
				BorderPane root = new BorderPane();
				root.setMaxSize(300, 100);
				// the display message
				Text warningMessage = new Text("Are you sure you want to quit?");
				// individual button set up
				Button no = new Button("NO");
				no.setOnAction(e -> popUpStage.close());
				Button yes = new Button("YES");
				yes.setOnAction(e -> Platform.exit());
				// buttons for yes or no
				HBox buttons = new HBox();
				buttons.getChildren().addAll(no, yes);
				buttons.setAlignment(Pos.CENTER);
				buttons.setSpacing(20);

				// wrapper for all the UI controls
				VBox list = new VBox();
				list.getChildren().addAll(warningMessage, buttons);
				list.setAlignment(Pos.CENTER);
				list.setSpacing(20);
				root.setCenter(list);
				root.setPadding(new Insets(15, 20, 10, 20));

				// show the pop-up
				Scene warning = new Scene(root, 250, 100);
				popUpStage.setScene(warning);
				popUpStage.show();
			}
		});
	}

	/**
	 * Choose a file from local, read in .json file and store the questions in
	 * the program. To be implemented.
	 */
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File"); // TODO: Where is the title
													// displayed?
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) { // user selected a file
			try { // parse the .json file
				Object obj = new JSONParser()
						.parse(new FileReader(selectedFile.getPath()));
				JSONObject jo = (JSONObject) obj;
				JSONArray questions = (JSONArray) jo.get("questionArray");
				for (int i = 0; i < questions.size(); i++) {
					JSONObject jsonQuestion = (JSONObject) questions.get(i);
					// add description, topic and image path properties to the
					// question
					String questionDescription = (String) jsonQuestion
							.get("questionText");
					String topic = (String) jsonQuestion.get("topic");
					String imagePath = (String) jsonQuestion.get("image");
					JSONArray choiceArray = (JSONArray) jsonQuestion
							.get("choiceArray");
					Choice[] choices = new Choice[choiceArray.size()];

					// add the choices to the question
					for (int j = 0; j < choiceArray.size(); j++) {
						JSONObject jsonChoice = (JSONObject) choiceArray.get(j);
						String choiceDescription = (String) jsonChoice
								.get("choice");
						boolean isCorrect = jsonChoice.get("isCorrect")
								.equals("T");
						Choice choice = new Choice(isCorrect,
								choiceDescription);
						choices[j] = choice;
					}

					// construct instance of Question
					Question newQuestion = new Question(questionDescription,
							choices, topic, imagePath);
					// add question to questionList
					questionPool.add(newQuestion);
				}
				fillTopic(); // call fill topic to fill the topic list
				stage.setScene(this.getScene());// Update number of questions shown in the main menu
			} catch (Exception e) {
				// catch any exception that is thrown for file IO
			} 
		}
	}

	/**
	 * Save the current questions to a local .json file.
	 */
	@SuppressWarnings("unchecked")
	private void saveFileToLocal() {
		// TODO: The json file doesn't contain "\n" so it's difficult for humans
		// to read, but the file
		// can be parsed without errors.
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("JSON(*.json)", "*.json"));

		// Show save file dialog
		File file = fileChooser.showSaveDialog(stage);

		if (file != null && !file.getName().contains(".")) {
			file = new File(file.getAbsolutePath() + ".json");
		}

		if (file != null) {
			// saves this string to a file
			JSONObject obj = new JSONObject();
			JSONArray questions = new JSONArray();

			for (Question question : questionPool) {
				// for each question, make a new json object and add that object
				// to the json array
				JSONObject q = new JSONObject();
				q.put("meta-data", "unused");
				q.put("questionText", question.getDescription());
				q.put("topic", question.getTopic());
				q.put("image", question.getImgPath());
				JSONArray jsonChoices = new JSONArray();
				Choice[] choices = question.getChoices();
				
				// iteratively put the JSON pairs
				for (int i = 0; i < choices.length; i++) {
					JSONObject c = new JSONObject();
					
					// correctness information
					if (choices[i].isCorrect()) {
						c.put("isCorrect", "T");
					} else {
						c.put("isCorrect", "F");
					}
					c.put("choice", choices[i].getChoiceDescription());
					jsonChoices.add(c);
					q.put("choiceArray", jsonChoices);
				}
				questions.add(q);
			}

			obj.put("questionArray", questions); // put the out-most JSON pair

			SaveFile(obj.toJSONString(), file); // save the file
		}
	}

	/**
	 * Writes the String content to a file
	 * 
	 * @param content is the content string
	 * @param file is the file that is going to store the content
	 */
	private void SaveFile(String content, File file) {
		FileWriter fileWriter = null;
		try {
			// save the file to the system
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			// catch any exception occurred
			System.out.println("IOException found!");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a new Button with width 275, height 40, font Helvetica and font
	 * size 15.
	 * 
	 * @param text is the text of the button
	 * @return a button
	 */
	private Button createNewButton(String text) {
		// set the font and other style
		return createNewButt(text, 275, 40, new Font("Helvetica", 15));
	}

	/**
	 * Returns a reference to a new Button with certain size and font.
	 * 
	 * @param text is the text of the button
	 * @param width is the preferred width
	 * @param height is the preferred height
	 * @param font is the specified font
	 * @return a button that is going to be used elsewhere
	 */
	private Button createNewButt(String text, double width, double height, Font font) {
		// set the UI control for give parameters
		Button newButton = new Button(text);
		newButton.setPrefWidth(width);
		newButton.setPrefHeight(height);
		newButton.setFont(font);
		return newButton;
	}

}
