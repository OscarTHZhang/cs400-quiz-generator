/**
 * Project: CS 400 Final Project Name: Quiz Generator A-team: #23
 * 
 * Credit:
 * 
 */

package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

  private Stage stage;

  public static Quiz QUIZ = new Quiz(); // the quiz object that we are going to
  // manipulate throughout the application
  public static List<Question> QUESTION_POOL = new ArrayList<>(); // all questions

  public static List<String> TOPIC = new ArrayList<>(); // all topic

  /**
   * fill the all topic list with the given question pool
   */
  public static void fillTopic() {
    for (Question q : QUESTION_POOL) {

      // if this is a topic that is not in the TOPIC list, then add this topic
      if (!TOPIC.contains(q.getTopic())) {
        TOPIC.add(q.getTopic());
      }
    }
  }

  /**
   * 
   * @param primaryStage
   */
  public MainMenuScene(Stage primaryStage) {
    stage = primaryStage;
  }

  /**
   * 
   * @return
   */
  public Scene getScene() {
    BorderPane root = setBorderPane();
    Scene scene = new Scene(root, 400, 500);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  /**
   * 
   * @return
   */
  private BorderPane setBorderPane() {
    BorderPane root = new BorderPane();

    Label title = new Label("Quiz Generator"); // set the main title
    title.setFont(new Font("Helvetica", 32)); // make the main title looks bigger

    Label numQuestions = new Label("Available Questions: 13"); // display the number of available
                                                               // questions

    Button startQuizButton = createNewButton("Start Quiz", 150, 100, new Font("Helvetiva", 18));
    DesignTestScene design = new DesignTestScene(stage);
    startQuizButton.setOnAction(e -> {
      stage.setScene(design.getScene());
      stage.show();
    });

    // put the title, numQuestions and the start quiz button in a VBox, set the VBox
    // in the
    // center of the BorderPane
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

    Button saveToLocal = createNewButton("Save Current Questions to Local File");
    saveToLocal.setOnAction(e -> saveFileToLocal());
    Button exit = createNewButton("Exit");

    // the functionalities of the exit button
    exit.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        Stage popUpStage = new Stage();
        BorderPane root = new BorderPane();
        root.setMaxSize(300, 100);

        Text warningMessage = new Text("Are you sure you want to quit?");

        Button no = new Button("NO");
        no.setOnAction(e -> popUpStage.close());
        Button yes = new Button("YES");
        yes.setOnAction(e -> Platform.exit());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(no, yes);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        VBox list = new VBox();
        list.getChildren().addAll(warningMessage, buttons);
        list.setAlignment(Pos.CENTER);
        list.setSpacing(20);
        root.setCenter(list);
        root.setPadding(new Insets(15, 20, 10, 20));

        Scene warning = new Scene(root, 250, 100);
        popUpStage.setScene(warning);
        popUpStage.show();
      }
    });

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loadFile, addQuestion, saveToLocal, exit);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);

    root.setBottom(buttons);
    root.setPadding(new Insets(20, 20, 30, 20));
    return root;
  }

  /**
   * Choose a file from local, read in .json file and store the questions in the program. To be
   * implemented.
   */
  private void chooseFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File"); // TODO: Where is the title displayed?
    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
      try {
        Object obj = new JSONParser().parse(new FileReader(selectedFile.getPath()));
        JSONObject jo = (JSONObject) obj;
        JSONArray questions = (JSONArray) jo.get("questionArray");
        for (int i = 0; i < questions.size(); i++) {
          Image image;
          JSONObject jsonQuestion = (JSONObject) questions.get(i);
          String questionDescription = (String) jsonQuestion.get("questionText");
          String topic = (String) jsonQuestion.get("topic");
          String imagePath = (String) jsonQuestion.get("image");
          JSONArray choiceArray = (JSONArray) jsonQuestion.get("choiceArray");
          Choice[] choices = new Choice[5];

          for (int j = 0; j < 5; j++) {
            JSONObject jsonChoice = (JSONObject) choiceArray.get(j);
            String choiceDescription = (String) jsonChoice.get("choice");
            boolean isCorrect = jsonChoice.get("isCorrect").equals("T");
            Choice choice = new Choice(isCorrect, choiceDescription);
            choices[j] = choice;
          }

          // construct instance of Question
          Question newQuestion = new Question(questionDescription, choices, topic, imagePath);
          // add question to questionList
          QUESTION_POOL.add(newQuestion);
        }
        System.out.print(QUESTION_POOL.size());
        fillTopic(); // call fill topic to fill the topic list

      } catch (FileNotFoundException e) {
      } catch (IOException e) {
      } catch (ParseException e) {
      }
    }
  }

  /**
   * Save the current questions to a local .json file. To be implemented.
   */
  @SuppressWarnings("unchecked")
  private void saveFileToLocal() {
    FileChooser fileChooser = new FileChooser();

    // Set extension filter
    // Look up for the formal name
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("JSON(*.json)", "*.json"));

    // Show save file dialog
    File file = fileChooser.showSaveDialog(stage);

    if (!file.getName().contains(".")) {
      file = new File(file.getAbsolutePath() + ".txt");
    }

    if (file != null) {
      // saves this string to a file
      JSONObject obj = new JSONObject();
      JSONArray questions = new JSONArray();

      for (Question question : QUESTION_POOL) {
        JSONObject q = new JSONObject();
        q.put("meta-data", "unused");
        q.put("questionText", question.getDescription());
        q.put("topic", question.getTopic());
        // image TODO
        // choices
        JSONArray jsonChoices = new JSONArray();
        Choice[] choices = question.getChoices();
        for (int i = 0; i < choices.length; i++) {
          JSONObject c = new JSONObject();
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

      obj.put("questionArray", questions);

      SaveFile(obj.toJSONString(), file);
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
      fileWriter = new FileWriter(file);
      fileWriter.write(content);
      fileWriter.close();
    } catch (IOException e) {
      // TODO: Figure out what is IOException and how to handle it.
      e.printStackTrace();
    }
  }

  /**
   * Returns a new Button with width 275, height 40, font Helvetica and font size 15.
   * 
   * @param text is the text of the button
   * @return a button
   */
  private Button createNewButton(String text) {
    return createNewButton(text, 275, 40, new Font("Helvetica", 15));
  }

  /**
   * Returns a reference to a new Button with certain size and font.
   * 
   * @param text is the text of the button
   * @param width is the preferred width
   * @param height is the preferred height
   * @param font is the specified font
   * @return a button
   */
  private Button createNewButton(String text, double width, double height, Font font) {
    Button newButton = new Button(text);
    newButton.setPrefWidth(width);
    newButton.setPrefHeight(height);
    newButton.setFont(font);
    return newButton;
  }

}
