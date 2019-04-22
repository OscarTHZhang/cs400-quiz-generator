package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
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
 * @author Rui Pan
 */
public class MainMenu extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    BorderPane root = setBorderPane();
		  
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Quiz Generator");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private BorderPane setBorderPane() {
  	    BorderPane root = new BorderPane();
        
        Label title = new Label("Quiz Generator"); // set the main title
        title.setFont(new Font("Helvetica", 30)); // make the main title looks bigger
        Label numQuestions = new Label("Available Questions: 13"); // display the number of available questions
        Button startQuizButton = createNewButton("Start Quiz", 150, 100, new Font("Helvetiva", 20));

        // put the title, numQuestions and the start quiz button in a VBox, set the VBox in the 
        // center of the BorderPane
        VBox start = new VBox();
        start.getChildren().addAll(title, startQuizButton, numQuestions);
        start.setAlignment(Pos.CENTER);
        start.setSpacing(30);
        root.setCenter(start);
        
        // generate three buttons for different functions
        Button loadFile = createNewButton("Load File");
        Button addQuestion = createNewButton("Add Questions");
        Button saveToLocal = createNewButton("Save Current Questions to Local File");
        Button exit = createNewButton("Exit");
  
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                BorderPane root = new BorderPane();
                root.setMaxSize(200, 100);
                
                Text warningMessage = new Text("Are you sure you want to quit?");
                
                Button no = new Button("NO");
                no.setOnAction(e -> stage.close());
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

                Scene exit = new Scene(root, 250, 200);
                stage.setScene(exit);
                stage.show();
            }
        });
        
        VBox buttons = new VBox();
        buttons.getChildren().addAll(loadFile, addQuestion, saveToLocal, exit);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(15, 20, 10, 10));
        buttons.setAlignment(Pos.CENTER);
        root.setBottom(buttons);
  
        return root;
	}
	
	/**
	 * Returns a new Button with width 275, height 40, font Helvetica and font size 15.
	 * @param text
	 * @return
	 */
	private Button createNewButton(String text) {
        return createNewButton(text, 275, 40, new Font("Helvetica", 15));
    }
	
	/**
	 * Returns a reference to a new Button with certain size and font.
	 * @param text
	 * @param width
	 * @param height
	 * @param font
	 * @return
	 */
	private Button createNewButton(String text, double width, double height, Font font) {
	    Button newButton = new Button(text);
	    newButton.setPrefWidth(width);
	    newButton.setPrefHeight(height);
	    newButton.setFont(font);
	    return newButton;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
