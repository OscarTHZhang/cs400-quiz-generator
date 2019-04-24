
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
 * This class uses JavaFx and creates a user interface
 * 
 * @author bradley
 *
 */
public class QuestionScene {

	private Stage stage;
	private Quiz quiz;

	private int currentQuestionCount = 1;
	protected static int finishedQuestionCount = 0;
	protected static int questionCount = 12;
	protected static int correctQuestionCount = 0;

	protected static List<Question> allQuestions;

	public QuestionScene(Stage primaryStage) {
		stage = primaryStage;
		setScene();
	}

	public void setScene() {
		List<String> topics = new ArrayList<>();
		topics.add("courseInfo");
		int questionCount = 3;
		this.quiz = new Quiz(topics, questionCount);

		List<Question> allQuestions = new ArrayList<>();
		Question q1 = new Question();
		q1.setDemo1();
		Question q2 = new Question();
		q2.setDemo2();
		Question q3 = new Question();
		q3.setDemo3();
		allQuestions.add(q1);
		allQuestions.add(q2);
		allQuestions.add(q3);

		quiz.setQuestions(allQuestions);

		currentQuestionCount = 1;
		QuestionScene.questionCount = 3;
	}

	public Scene getScene() {
		BorderPane root = setBorderPane();
		
		Scene scene = new Scene(root, 400, 400); // set window size
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

	private BorderPane setBorderPane() {
		BorderPane root = new BorderPane();
		
		// set up in another place
		Label questionLabel = new Label("Question " + (currentQuestionCount) + " out of " + questionCount + ": ");
		questionLabel.setFont(new Font("Helvetiva", 12));
		questionLabel.setAlignment(Pos.CENTER_LEFT);
		
		Question cur = quiz.getQuestions().get(currentQuestionCount - 1);

		String question = cur.getDescription();
		String[] choices = cur.getChoices();

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

		HBox questionDescription = new HBox();
		// question label
		Text questionTitle = new Text(question);
		questionTitle.setFont(new Font("Helvetiva", 14));
		questionDescription.getChildren().add(questionTitle);
		questionDescription.setPadding(new Insets(10, 0, 0, 15));
		
		VBox choicesBox = new VBox();
		// choice label
		CheckBox[] allCheckBox = new CheckBox[choices.length];
		for (int i = 0; i < choices.length; i++) {
			CheckBox cb = new CheckBox(choices[i]);
			choicesBox.getChildren().add(cb);
			allCheckBox[i] = cb;
		}
		choicesBox.setAlignment(Pos.CENTER_LEFT);
		choicesBox.setSpacing(20);
		
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(30);
		buttons.setPadding(new Insets(20, 0, 0, 0));
		
		// prev
		Button prev = new Button("PREVIOUS");
		prev.setOnAction(e -> {
			if (currentQuestionCount > 1) {
				currentQuestionCount--;
				stage.setScene(QuestionScene.this.getScene());
				stage.show();
			}
		});
		buttons.getChildren().add(prev);

		// submit
		Button submit = new Button("SUBMIT");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Stage popUpStage = new Stage();
				BorderPane pane = new BorderPane();
				VBox vBox = new VBox();
				Label label = new Label("Your answer is correct!");
				Label label2 = new Label("\n");
				Button click = new Button("Next question");
				vBox.getChildren().addAll(label, label2, click);
				vBox.setAlignment(Pos.CENTER);
				pane.setCenter(vBox);
				Scene page2 = new Scene(pane, 250, 100);
				popUpStage.setScene(page2);
				popUpStage.show();

				click.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						popUpStage.close();
						if (currentQuestionCount < questionCount) {
							currentQuestionCount++;
							finishedQuestionCount++;
							stage.setScene(QuestionScene.this.getScene());
							stage.show();
						} else {
							ResultScene result = new ResultScene(stage);
							stage.setScene(result.getScene());
							stage.show();
						}
					}
				});
			}
		});
		submit.setMinSize(70, 40);
		buttons.getChildren().add(submit);

		// next
		Button next = new Button("NEXT");
		next.setOnAction(e -> {
			if (currentQuestionCount < questionCount) {
				currentQuestionCount++;
				stage.setScene(QuestionScene.this.getScene());
				stage.show();
			}
		});
		buttons.getChildren().add(next);

		Image image = new Image("400fx.jpg");
		ImageView iv = new ImageView(image);
		iv.setFitHeight(150);
		iv.setFitWidth(150);

		HBox choicesAndImage = new HBox();
		choicesAndImage.getChildren().addAll(choicesBox, iv);
		choicesAndImage.setAlignment(Pos.CENTER_LEFT);
		choicesAndImage.setSpacing(80);
		choicesAndImage.setPadding(new Insets(10, 0, 0, 10));
		
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
