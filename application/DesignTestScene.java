/**
 * Project: CS 400 Final Project
 * Name:	Quiz Generator
 * A-team:	#23
 * 
 * Credit:
 * 
 */

package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This Application is the UI for Design Application
 * 
 * @author Oscar
 *
 */
public class DesignTestScene {

	private Stage stage; // the stage that is built on

	private VBox planeElement; // the vertical box that contains all the elements

	private HBox chooseTopic; // contains choosing list of all the topic options
	private HBox numQuestion; // contains the text field that shows number of
								// questions you want to have
	private HBox options; // contains the options: cancel and start
	private HBox showTopic; // contains the chosen topics

	private TextField questionNum;

	private ArrayList<String> chosenTopic; // the list of topics that the user has chosen

	/**
	 * The constructor of the DesignSecne class
	 * 
	 * @param primaryStage
	 */
	public DesignTestScene(Stage primaryStage) {
		stage = primaryStage;
		chosenTopic = new ArrayList<String>();
	}

	/**
	 * return the scene that is generated by the class
	 * 
	 * @return the scene that is generated by the class
	 */
	public Scene getScene() {
		BorderPane root = new BorderPane();
		setLayout();
		root.setCenter(planeElement);
		root.setPadding(new Insets(20, 20, 20, 20));
		Scene scene = new Scene(root, 400, 250);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	/**
	 * private helper that sets the layout plan
	 * 
	 * @throws Exception if there is an exception when initializing these boxes layout
	 */
	private void setLayout() {
		planeElement = new VBox(); // contains all the HBox
		planeElement.setAlignment(Pos.CENTER);
		planeElement.setSpacing(30.0);

		chooseTopic = new HBox();
		chooseTopic.setSpacing(15.0);
		chooseTopic.getChildren().add(new Label("Choosing the topic:"));

		// this should be imported from another array list in the back end topic
		// class
		ComboBox<String> topicList = new ComboBox<String>(
				FXCollections.observableArrayList(MainMenuScene.TOPIC));
		chooseTopic.getChildren().add(topicList);

		// a new HBox for showing the topic
		showTopic = new HBox();
		showTopic.setSpacing(5.0);

		// set up select button
		Button add = new Button("+");
		// set the functionality using lambda expression
		add.setOnAction(event -> {
			if (topicList.getValue() != null
					&& !chosenTopic.contains(topicList.getValue())) {
				chosenTopic.add(topicList.getValue());
				showTopic.getChildren().add(
						new Label(chosenTopic.get(chosenTopic.size() - 1) + ";"));
				// get the latest added topic to display on the screen
			}
		});
		// add the button
		chooseTopic.getChildren().add(add);

		// set up number of questions box
		numQuestion = new HBox();
		numQuestion.setSpacing(15.0);
		numQuestion.getChildren().add(new Label("Number of questions:"));
		questionNum = new TextField();
		numQuestion.getChildren().add(questionNum);

		options = new HBox();
		options.setSpacing(50.0);

		// cancel button
		options.getChildren().add(createButtCancel());
		// start button
		options.getChildren().add(createButtStart());
		setBoxLayout(); // set the boxes' layout
	}

	/**
	 * set the layout and containment of the VBoxes and HBoxes used in this
	 * scene
	 */
	private void setBoxLayout() {
		// set the alignment of all the boxes
		chooseTopic.setAlignment(Pos.CENTER);
		numQuestion.setAlignment(Pos.CENTER);
		options.setAlignment(Pos.CENTER);
		showTopic.setAlignment(Pos.CENTER);

		// add the HBox into VBox
		planeElement.getChildren().add(chooseTopic);
		planeElement.getChildren().add(numQuestion);
		planeElement.getChildren().add(showTopic);
		planeElement.getChildren().add(options);
	}

	/**
	 * create the button for cancel
	 * 
	 * @return Button object for canceling
	 */
	private Button createButtCancel() {
		Button cancel = new Button("CANCEL");
		MainMenuScene mainMenu = new MainMenuScene(stage);
		// set button function
		cancel.setOnAction(e -> {
			stage.setScene(mainMenu.getScene());
			stage.show();
		});
		return cancel;
	}

	/**
	 * create the button for start the quiz
	 * 
	 * @return Button object for starting
	 */
	private Button createButtStart() {
		Button start = new Button("START");
		QuestionScene questionScene = new QuestionScene(stage);

		// set button function
		start.setOnAction(e -> {
			String qNum = questionNum.getText();
			int maxQNum = 0;
			for (int i = 0; i < MainMenuScene.QUESTION_POOL.size(); i++) {
				for (int j = 0; j < chosenTopic.size(); j++) {
					if (MainMenuScene.QUESTION_POOL.get(i).getTopic().equals(chosenTopic.get(j))) {
						maxQNum++;
					}
				}
			}
			System.out.print(maxQNum);
			if (!qNum.equals("") && !chosenTopic.isEmpty()) {
				try {
					int num = Integer.parseInt(qNum);
					if (num < 1) {
						showAlert("numberTooSmall");
					} else if (num > maxQNum) {
						showAlert("numberTooLarge");
					} else {
						MainMenuScene.QUIZ.setQuestionCount(num);
						MainMenuScene.QUIZ.setTopic(chosenTopic);
						MainMenuScene.QUIZ.generateQuestions();
						
						questionScene.setQuiz(MainMenuScene.QUIZ);
						stage.setScene(questionScene.getScene());
						stage.show();
					}
				} catch (NumberFormatException exception) {
					showAlert("numberFormat");
				}

			} else if (chosenTopic.isEmpty()) {
				showAlert("topic");
			} else if (qNum.equals("")) {
				showAlert("number");
			}
		});
		return start;
	}

	/**
	 * 
	 * @param problem
	 */
	private void showAlert(String problem) {
		Text warningMessage = new Text();

		switch (problem) {
			case "topic" :
				warningMessage.setText("Please select at least one topic!");
				break;
			case "number" :
				warningMessage.setText("Please enter the number of questions!");
				break;
			case "numberTooSmall" :
				warningMessage.setText("Please enter a positive number!");
				break;
			case "numberTooLarge" :
				warningMessage.setText("The number is too large!");
				break;
			case "numberFormat" :
				warningMessage.setText("Please enter a valid number!");
				break;
			default :
				warningMessage.setText("Please!");
		}

		Stage popUpStage = new Stage();
		BorderPane root = new BorderPane();

		Button yes = new Button("GOT IT");
		yes.setOnAction(e -> popUpStage.close());

		HBox buttons = new HBox();
		buttons.getChildren().addAll(yes);
		buttons.setAlignment(Pos.CENTER);

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
