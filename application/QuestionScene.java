/**
 * Project: CS 400 Final Project
 * Name:    Quiz Generator
 * A-team:  #23
 * 
 * Credit:
 * 
 */

package application;

import java.util.ArrayList;
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

	private Stage stage; // graphics stage
	private Quiz quiz; // quiz

	protected static int questionCount = 0; // question count of the quiz
	protected static int correctQuestionCount = 0; // number of correct questions
	protected static int finishedQuestionCount = 0; // number of finished questions
	protected static int correctChoiceCount = 0; // number of correct choices
	protected static int choiceCount = 0; // number of all choices

	/**
	 * constructor of QuestionScence
	 * 
	 * @param primaryStage
	 */
	public QuestionScene(Stage primaryStage) {
		stage = primaryStage;
		setScene();
	}
	
	/**
	 * set the quiz for this quiz
	 * @param newQuiz the new Quiz that is set to be created
	 * @return true if successfully created the quiz
	 */
	public boolean setQuiz(Quiz newQuiz) {
		try {
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
	public void setScene() {
		List<String> topics = MainMenuScene.TOPIC;
		List<Question> allQuestions = MainMenuScene.QUIZ.getQuestions();
		QuestionScene.questionCount = MainMenuScene.QUIZ.getQuestionCount();
	}

	/**
	 * set scene attributes
	 * 
	 * @return scene
	 */
	public Scene getScene() {
		BorderPane root = setBorderPane();
		Scene scene = new Scene(root, 400, 400); // set window size
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	/**
	 * set interface's borderPane
	 * 
	 * @return BorderPane
	 */
	private BorderPane setBorderPane() {
		BorderPane root = new BorderPane();
		Question cur = quiz.currQuesiton(); // get current question
		String question = cur.getDescription(); // get question title
		Choice[] choices = cur.getChoices(); // get choices

		// process question title - Start
		int tmpLength = question.length();
		int tmpCount = 1;
		int lengthLimit = 40;
		while (tmpLength > lengthLimit) {
			question = question.substring(0,
					lengthLimit * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" ")) + "\n"
					+ question.substring(40 * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" "));
			tmpLength -= lengthLimit + question.substring(lengthLimit * tmpCount).indexOf(" ");
			tmpCount++;
		}
		// process question title - End

		Label questionLabel = new Label(
				"Question " + (quiz.getCurrentQuestionIndex() + 1) + " out of " + questionCount + ": ");
		questionLabel.setFont(new Font("Helvetiva", 12));
		questionLabel.setAlignment(Pos.CENTER_LEFT);

		// set question label
		HBox questionDescription = new HBox();
		Text questionTitle = new Text(question);
		questionTitle.setFont(new Font("Helvetiva", 14));
		questionDescription.getChildren().add(questionTitle);
		questionDescription.setPadding(new Insets(10, 0, 0, 15));

		// set choice label
		VBox choicesBox = new VBox();
		CheckBox[] allCheckBox = new CheckBox[choices.length];
		for (int i = 0; i < choices.length; i++) {
			CheckBox cb = new CheckBox(choices[i].getChoiceDescription());
			if (quiz.checkAnswer().size() != 0) { // if the question has been answered, show last answer
			    for(Choice choice : quiz.checkAnswer()) {
    				if (choice.equals(choices[i])) {
    					cb.setSelected(true);
    				}
			    }
			}
			choicesBox.getChildren().add(cb);
			allCheckBox[i] = cb;
		}
		choicesBox.setAlignment(Pos.CENTER_LEFT);
		choicesBox.setSpacing(20);

		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(30);
		buttons.setPadding(new Insets(20, 0, 0, 0));

		// set prev button
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
		if (quiz.checkAnswer().size() == 0) {
			submit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {

					// check user's answer
					boolean correctness = false;
					boolean partiallCorrectness = false;
					boolean redundantChoice = false;
					choiceCount += allCheckBox.length;
					
//					List<String> keys = cur.getKeys();
//					for (int i = 0; i < allCheckBox.length; i++) {
//						if (allCheckBox[i].isSelected()) {
//							quiz.answer(choices[i]);
//							if (keys.contains(choices[i])) {
//								partiallCorrectness = true;
//								keys.remove(choices[i]);
//								correctChoiceCount++;
//							} else {
//								redundantChoice = true; // check if it is completely correct or partially correct
//							}
//						}
//					}
//					if (keys.isEmpty()) {
//						correctness = true;
//						correctQuestionCount++;
//					}
					String correctnessPrompt;
					if (correctness && !redundantChoice)
						correctnessPrompt = "correct :)";
					else if (partiallCorrectness)
						correctnessPrompt = "partially correct";
					else
						correctnessPrompt = "incorrect :("; // prepare prompt for each result

					// set popup window after user hits submit
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
						@Override
						public void handle(ActionEvent event) {
							popUpStage.close();
							if (!quiz.checkAnswer().isEmpty()) // check if user answered this question
								finishedQuestionCount++;
							if (quiz.getCurrentQuestionIndex() + 1 < questionCount) { // if not the last question
								quiz.next();
								stage.setScene(QuestionScene.this.getScene());
								stage.show();
							} else { // prepare result scene
								ResultScene result = new ResultScene(stage);
								stage.setScene(result.getScene());
								stage.show();
							}
						}
					});
				}
			});
		}
		submit.setMinSize(70, 40);
		buttons.getChildren().add(submit);

		// set next button
		Button next = new Button("NEXT");
		next.setOnAction(e -> {
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
		choicesAndImage.setPadding(new Insets(10, 0, 0, 10));
		choicesAndImage.getChildren().addAll(choicesBox);
		// if has images
		if (cur.getImg() != null) {
			Image image = cur.getImg();
			ImageView iv = new ImageView(image);
			iv.setFitHeight(150);
			iv.setFitWidth(150);
			choicesAndImage.getChildren().addAll(iv);
		}

		VBox list = new VBox();
		list.getChildren().addAll(questionLabel, questionDescription);
		list.setAlignment(Pos.TOP_LEFT);
		list.setSpacing(15);

		root.setTop(list);
		root.setCenter(choicesAndImage);
		root.setBottom(buttons);
		root.setPadding(new Insets(20, 30, 20, 30));

		return root;
	}
}
