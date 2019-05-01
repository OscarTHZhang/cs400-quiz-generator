/**
 * Project: CS 400 Final Project
 * Name:	Quiz Generator
 * A-team:	#23
 * 
 * Credit:
 * 
 */

package application;

import java.util.List;

import javafx.scene.image.Image;

/**
 * The question class that holds a question in the quiz generator application
 * 
 * @author Bradley
 *
 */
public class Question {

	private String description; // description of the question
	private Choice[] choices; // answer choices
	private String topic; // the topic that this question is affiliated to
	private Image img; // a list of images that may be related to this question

	/**
	 * The constructor of the Question class that takes in the parameters
	 * 
	 * @param description of the question
	 * @param choices     of the question
	 * @param topic       that the question is affiliated to
	 * @param keys        true answer for this question
	 */
	public Question(String description, Choice[] choices, String topic, Image img) {

		// assign the parameters to the class attributes
		this.description = description;
		this.choices = choices;
		this.topic = topic;
		this.img = img;
	}

	public Question() {
		
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Choice[] getChoices() {
		return choices;
	}

	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}
