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

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Graphics interface for a quiz. Containing a question , selection box, and
 * options to move to next question or back to last question
 * 
 * @author Bradley
 *
 */
public class QuestionScene {

	private Stage stage; // graphics stage in the session
	private Quiz quiz; // quiz in the session

	protected static int questionCount = 0; // question count of the quiz
	protected static int correctQuestionCount = 0; // number of correct questions
	protected static int finishedQuestionCount = 0; // number of finished questions
	protected static int correctChoiceCount = 0; // number of correct choices
	protected static int choiceCount = 0; // number of all choices

	protected static double score = 0.0; // current score

	/**
	 * constructor of QuestionScence; setting the stage to the primary stage
	 * 
	 * @param primaryStage is the primary stage
	 */
	public QuestionScene(Stage primaryStage) {
		stage = primaryStage;
	}

	/**
	 * set the quiz for this quiz by setting relevant parameters
	 * 
	 * @param newQuiz the new Quiz that is set to be created
	 * @return true if successfully created the quiz
	 */
	public boolean setQuiz(Quiz newQuiz) {
		try {
			// setting the relevant parameters
			questionCount = 0;
			correctQuestionCount = 0;
			finishedQuestionCount = 0;
			correctChoiceCount = 0;
			choiceCount = 0;
			score = 0.0;
			quiz = newQuiz;
			return true;
		} catch (Exception e) {
			// print error message when creating quiz failed
			System.out.print("setting quiz failed.");
			return false;
		}
	}

	/**
	 * initialize scene by initializing quiz and a few important static parameters
	 */
	@SuppressWarnings("unused")
	public void setScene() {
		// passing the static parameters to the attributes
		List<String> topics = MainMenuScene.allallTopics;
		List<Question> allQuestions = MainMenuScene.overallQuiz.getQuestions();
		QuestionScene.questionCount = MainMenuScene.overallQuiz.getQuestionCount();
	}

	/**
	 * set scene attributes to prepare the showing of UI
	 * 
	 * @return scene the scene that is set up
	 */
	public Scene getScene() {
		BorderPane root = setBorderPane();
		Scene scene = new Scene(root, 700, 450); // set window size
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		// render with CSS file
		return scene;
	}

	/**
	 * helper method to process the question string so that the question is breaking according to 
	 * the English grammar
	 * @return the new string with breaking point setting properly
	 */
	private String processQuestion(String question) {
		// process question title : add \n after certain number of single strings
		// process question title - Start
		int tmpLength = question.length();
		int tmpIndex = 1;
		int lengthLimit = 55;
		// traverse the line
		while (tmpLength - 1 > tmpIndex) {
			if (tmpIndex % lengthLimit == 0) {
				// find the right place to add a line breaker
				while (!question.substring(tmpIndex, tmpIndex + 1).equals(" ")) {
					if (tmpIndex == tmpLength - 1)
						break;
					tmpIndex++;
				}
				// add the line breaker
				question = question.substring(0, tmpIndex + 1) + "\n" + 
						question.substring(tmpIndex + 1);
				
				tmpIndex++;
			}
			tmpIndex++;
		}
		// process question title - End
		return question;
	}
	
	/**
	 * set interface's borderPane of the UI
	 * 
	 * @return BorderPane the border pane that is set 
	 */
	private BorderPane setBorderPane() {
		setScene();
		BorderPane root = new BorderPane();
		Question cur = quiz.currQuesiton(); // get current question
		String question = cur.getDescription(); // get question title
		Choice[] choices = cur.getChoices(); // get choices

		question = processQuestion(question); // call helper method to process question string

		// question label with paging number
		Label questionLabel = new Label("Question " + (quiz.getCurrentQuestionIndex() + 1) + 
				" out of " + questionCount + ": ");
		
		questionLabel.setFont(new Font("Helvetiva", 12));
		questionLabel.setAlignment(Pos.CENTER_LEFT);

		// set question label
		HBox questionDescription = new HBox();
		Text questionTitle = new Text(question);
		questionTitle.wrappingWidthProperty().bind(root.widthProperty());
		// questionTitle.setWrappingWidth(500);
		questionTitle.setFont(new Font("Helvetiva", 14));
		questionDescription.getChildren().add(questionTitle);
		questionDescription.setPadding(new Insets(10, 0, 0, 10));
		questionDescription.setAlignment(Pos.CENTER_LEFT);

		// set choice label
		VBox choicesBox = new VBox();
		CheckBox[] allCheckBox = new CheckBox[choices.length];

		// traverse to get the choice description
		for (int i = 0; i < choices.length; i++) {

			CheckBox cb = new CheckBox(choices[i].getChoiceDescription());
			if (quiz.checkAnswer().size() != 0) { 
				// if the question has been answered, show last answer
				for (Choice choice : quiz.checkAnswer()) {
					if (choice.equals(choices[i])) {
						cb.setSelected(true);
					}
				}
			}
			choicesBox.getChildren().add(cb);
			allCheckBox[i] = cb;
		}

		// layout of boxes for buttons 
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(30);

		// spacing and placing for choice boxes
		choicesBox.setPadding(new Insets(0, 0, 0, 10));
		choicesBox.setAlignment(Pos.CENTER_LEFT);
		choicesBox.setSpacing(20);

		// set previous button
		Button prev = new Button("PREVIOUS");
		prev.setOnAction(e -> {
			if (quiz.getCurrentQuestionIndex() > 0) {
				quiz.prev();
				stage.setScene(QuestionScene.this.getScene());
				stage.show();
			}
		});
		buttons.getChildren().add(prev);

		// set submit button
		Button submit = new Button("SUBMIT");
		// if user hits submit button

		submit.setOnAction(new EventHandler<ActionEvent>() {
		  
			/**
			 * set up the functionality of submit button
			 * @param event the event of the control
			 */
			@Override
			public void handle(ActionEvent event) {
				if (!cur.isAnswered()) {
					// check user's answer
					boolean correctness = false;
					boolean redundantChoice = false;
					for (Choice choice : cur.getChoices()) {
						if (choice.isCorrect())
							choiceCount++;
					}

					// check finished questions
					if (!quiz.checkAnswer().isEmpty())
						finishedQuestionCount++;

					int numShouldSelected = 0;
					for (Choice c : choices) {
						if (c.isCorrect())
							numShouldSelected++;
					}

					// traverse to compare answers with the key
					for (int i = 0; i < allCheckBox.length; i++) {

						if (allCheckBox[i].isSelected()) {
							// add in to answer
							quiz.answer(choices[i]);

							// check the case where user select both correct and incorrect answers
							if (choices[i].isCorrect()) {
								//System.out.println(correctChoiceCount);
								correctChoiceCount++;
								correctness = true;
							} else {
								redundantChoice = true;
							}
						}
					}

					if (correctChoiceCount < numShouldSelected)
						// check the case where user does not select enough correct answers
						redundantChoice = true;

					// setting up the prompt for result of current question
					String correctnessPrompt;
					if (correctness) {
						// correct case
						if (redundantChoice) {
							// partially correct case
							correctnessPrompt = "partially correct";
							score += 0.5;
						} else {
							// all correct
							correctnessPrompt = "correct :)";
							correctQuestionCount++;
							score += 1;
						}
					} else {
						// non of them is correct
						correctnessPrompt = "incorrect :("; // prepare prompt for each result
					} 
					// set pop-up window after user hits submit
					Stage popUpStage = new Stage();
					BorderPane pane = new BorderPane();
					VBox vBox = new VBox();
					Label label = new Label("Your answer is " + correctnessPrompt);
					Label label2 = new Label("\n");
					Button click = new Button("Next question");
					vBox.getChildren().addAll(label, label2, click);
					vBox.setAlignment(Pos.CENTER);
					pane.setCenter(vBox);
					Scene page2 = new Scene(pane, 250, 100);
					popUpStage.setScene(page2);
					popUpStage.show();

					// if user clicks on next question
					click.setOnAction(new EventHandler<ActionEvent>() {
						
						/**
						 * set up the functionality of the button "next"
						 * @param event that is going to start the function of this button
						 */
						@Override
						public void handle(ActionEvent event) {
							popUpStage.close();
							if (!quiz.checkAnswer().isEmpty()) 
								// check if user answered this question
								finishedQuestionCount++;
							if (quiz.getCurrentQuestionIndex() + 1 < questionCount) { 
								// if not the last question
								quiz.next();
								stage.setScene(QuestionScene.this.getScene());
								stage.show();
							} else { 
								// prepare result scene
								ResultScene result = new ResultScene(stage);
								stage.setScene(result.getScene());
								stage.show();
							}
						}
					});
					cur.setAnswered(true);
					
				} else {
					// setting up the pop-up for showing the question is answered
					Stage popUpStage = new Stage();
					BorderPane pane = new BorderPane();
					VBox vBox = new VBox();
					Label label = new Label("The question is answered!");
					Label label2 = new Label("\n");
					Button click = new Button("OK");
					vBox.getChildren().addAll(label, label2, click);
					vBox.setAlignment(Pos.CENTER);
					pane.setCenter(vBox);
					Scene page2 = new Scene(pane, 250, 100);
					popUpStage.setScene(page2);
					popUpStage.show();

					// if user clicks on next question
					click.setOnAction(new EventHandler<ActionEvent>() {
						/**
						 * set up the functionality of next button in this stage
						 * @param event that is going to trigger the function of this button
						 */
						@Override
						public void handle(ActionEvent event) {
							 // prepare result scene
							if (quiz.getCurrentQuestionIndex() + 1 >= questionCount) { 
								// if not the last question
								ResultScene result = new ResultScene(stage);
								stage.setScene(result.getScene());
								stage.show();
							} else {
								// close the current pop-up if it is not last question
								popUpStage.close();
							}
						}
					});
				}	
			}
		});

		submit.setMinSize(70, 40); // set the size of the button
		buttons.getChildren().add(submit); // add the button submit

		// set next button for skipping
		Button next = new Button("SKIP");
		next.setOnAction(e -> {
			// go the the next question
			if (quiz.getCurrentQuestionIndex() + 1 < questionCount) {
				quiz.next();
				stage.setScene(QuestionScene.this.getScene());
				stage.show();
			}
		});
		buttons.getChildren().add(next);

		// set choice and images
		HBox choicesAndImage = new HBox();
		choicesAndImage.setAlignment(Pos.CENTER_LEFT);
		choicesAndImage.setSpacing(80);
		choicesAndImage.setPadding(new Insets(10, 0, 0, 30));
		choicesAndImage.getChildren().addAll(choicesBox);
		// if has images
		if (cur.getImg() != null) {
			Image image = cur.getImg();
			ImageView iv = new ImageView(image);
			iv.setFitHeight(150);
			iv.setFitWidth(150);
			choicesAndImage.getChildren().addAll(iv);
		}

		// 
		VBox list = new VBox();
		list.getChildren().addAll(questionLabel, questionDescription);
		list.setAlignment(Pos.TOP_LEFT);
		list.setSpacing(15);
		list.setPadding(new Insets(0, 0, 0, 20));

		root.setTop(list);
		root.setCenter(choicesAndImage);
		root.setBottom(buttons);
		root.setPadding(new Insets(20, 0, 20, 0));

		return root;
	}
}
