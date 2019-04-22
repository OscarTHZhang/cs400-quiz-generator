
package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class uses JavaFx and creates a user interface
 * 
 * @author bradley
 *
 */
public class Question extends Application {
  /**
   * This method constructs a user interface
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      Pane root = new Pane();
      int QuestionNum = 3;
      int QuestionCount = 12;
      Label questionLabel = new Label("Question " + QuestionNum + " out of " + QuestionCount);
      questionLabel.setLayoutX(1);
      questionLabel.setLayoutY(2);
      String question =
          "--What is the name of the computer sciences professors in the picture on the right? (Select one answer)";
      String[] choices = {"Deb Deppeler","Gary Dahl","Peter Pan","Harley Shi"};
      // process questin title - Start
      int tmpLength = question.length();
      int tmpCount = 1;
      int lengthLimit = 40;
      while (tmpLength > lengthLimit) {
        question = question.substring(0,
            lengthLimit * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" ")) + "\n"
            + question.substring(40 * tmpCount + question.substring(lengthLimit * tmpCount).indexOf(" "));
        tmpLength -= lengthLimit+question.substring(lengthLimit * tmpCount).indexOf(" ");
        tmpCount++;
      }
    // process questin title - End
      
    // question label 
      Label questionTitle = new Label(question);
      questionTitle.setLayoutX(10);
      questionTitle.setLayoutY(30);
    // choice label
      for(int i=0;i<choices.length;i++) {
        CheckBox cb  =new CheckBox(choices[i]);
        cb.setLayoutX(30);
        cb.setLayoutY(80+(i+1)*30);
        root.getChildren().add(cb);
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
      skip.setLayoutX(300);
      skip.setLayoutY(320); 
      root.getChildren().add(skip);
      // next and prev
      Button next = new Button("next -->");
      next.setLayoutX(320);
      next.setLayoutY(370);
      root.getChildren().add(next);
      
      Button prev = new Button("<--previous");
      prev.setLayoutX(1);
      prev.setLayoutY(370);
      root.getChildren().add(prev);
      
      
      
      Image image = new Image("400fx.jpg");
      ImageView iv = new ImageView(image);
      iv.setFitHeight(150);  
      iv.setFitWidth(150);
      iv.setLayoutX(200);
      iv.setLayoutY(100); 
      // root.setTop(greetingLabel); // set panel
      // root.getChildren().add(iv);
      root.getChildren().add(questionLabel);
      root.getChildren().add(questionTitle);
      root.getChildren().add(iv);
      // root.setCenter(questionTitle);
      // root.setLeft(comboBox);
      // root.setBottom(button);
      // root.setRight(button1);
      // root.getChildren().add(questionTitle);

      submit.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          Stage stage = new Stage(); 
          BorderPane pane = new BorderPane();
          VBox hBox = new VBox();
          Label label  = new Label("Your answer is correct √");
          Label label2  = new Label("\n");
          Button click = new Button("Next question");
          hBox.getChildren().addAll(label,label2,click);
          hBox.setAlignment(Pos.CENTER);
          pane.setCenter(hBox);
          Scene page2  = new Scene(pane, 250, 200);
          stage.setScene(page2);
          stage.show();
          
          click.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
             stage.close();
            }});
        }
      });
      
      Scene scene = new Scene(root, 400, 400); // set window size
      primaryStage.setTitle("Quiz Generator");
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Main entrance of the program
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
