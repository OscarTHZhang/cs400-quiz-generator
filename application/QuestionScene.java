
package application;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

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
    int questionCount  =3;
    this.quiz= new Quiz(topics,questionCount);
    
    List<Question> allQuestions = new ArrayList<>();
    Question q1= new Question(); 
    q1.setDemo1();
    Question q2= new Question(); 
    q2.setDemo2();
    Question q3= new Question(); 
    q3.setDemo3();
    allQuestions.add(q1);
    allQuestions.add(q2);
    allQuestions.add(q3);
    
    quiz.setQuestions(allQuestions);
    
    currentQuestionCount = 1;
    QuestionScene.questionCount=3;
  }

  public Scene getScene() {
    Pane root = new Pane();
     // set up in another place 
    Label questionLabel =
        new Label("Question " + (currentQuestionCount) + " out of " + questionCount);
    questionLabel.setLayoutX(1);
    questionLabel.setLayoutY(2); 
    Question cur = quiz.getQuestions().get(currentQuestionCount-1);
    
    String question= cur.getDescription();
    String[] choices  = cur.getChoices();
    
    // process question title - Start
    
    int tmpLength = question.length();
    int tmpCount = 1;
    int lengthLimit = 40;
    while (tmpLength > lengthLimit) {
      question = question.substring(0,
          lengthLimit * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" ")) + "\n"
          + question
              .substring(40 * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" "));
      tmpLength -= lengthLimit + question.substring(lengthLimit * tmpCount).indexOf(" ");
      tmpCount++;
    }
    
    // process question title - End

    // question label
    Label questionTitle = new Label(question);
    questionTitle.setLayoutX(10);
    questionTitle.setLayoutY(30);
    // choice label
    CheckBox[] allCheckBox = new CheckBox[choices.length];
    for (int i = 0; i < choices.length; i++) {
      CheckBox cb = new CheckBox(choices[i]);
      cb.setLayoutX(30);
      cb.setLayoutY(80 + (i + 1) * 30);
      root.getChildren().add(cb);
      allCheckBox[i]=cb;
    }
    // function button
    // submit button
    Button submit = new Button("Submit Answer ");
    submit.setLayoutX(130);
    submit.setLayoutY(270);
    submit.setMinSize(70, 70);
    root.getChildren().add(submit);

    // skip button
    Button skip = new Button("skip this\n question >>");
    skip.setOnAction(e -> {
      currentQuestionCount++;
      stage.setScene(QuestionScene.this.getScene());
      stage.show();
    });
    skip.setLayoutX(300);
    skip.setLayoutY(320);
    root.getChildren().add(skip);

    // next and prev
    Button next = new Button("next -->");
    next.setOnAction(e -> {
      if (currentQuestionCount < questionCount) {
        currentQuestionCount++;
        stage.setScene(QuestionScene.this.getScene());
        stage.show();
      }
    });
    next.setLayoutX(320);
    next.setLayoutY(370);
    root.getChildren().add(next);

    Button prev = new Button("<--previous");
    prev.setOnAction(e -> {
      if (currentQuestionCount > 1) {
        currentQuestionCount--;
        stage.setScene(QuestionScene.this.getScene());
        stage.show();
      }
    });
    prev.setLayoutX(1);
    prev.setLayoutY(370);
    root.getChildren().add(prev);

    Image image = new Image("400fx.jpg");
    ImageView iv = new ImageView(image);
    iv.setFitHeight(150);
    iv.setFitWidth(150);
    iv.setLayoutX(200);
    iv.setLayoutY(100);
    
    root.getChildren().add(questionLabel);
    root.getChildren().add(questionTitle);
    root.getChildren().add(iv);

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
        Scene page2 = new Scene(pane, 250, 200);
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

    Scene scene = new Scene(root, 400, 400); // set window size
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

    return scene;
  }

}
