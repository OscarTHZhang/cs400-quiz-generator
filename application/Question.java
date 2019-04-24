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
 * @author Bradley
 *
 */
public class Question {
	
	private String description; // description of the question
	private String[] choices; // answer choices
	private String topic; // the topic that this question is affiliated to
	private List<String> keys; // the true answer for this question
	List<Image> imgs; // a list of images that may be related to this question

	/**
	 * The constructor of the Question class that takes in the parameters
	 * @param description of the question
	 * @param choices of the question
	 * @param topic that the question is affiliated to
	 * @param keys true answer for this question
	 */
	public Question(String description, String[] choices, String topic, List<String> keys, 
		List<Image> imgs) {
		
		// assign the parameters to the class attributes
		this.description = description;
		this.choices = choices;
		this.topic = topic;
		this.keys = keys;
		this.imgs = imgs;
	}

	/**
	 * The non-parameter constructor of the class
	 */
	public Question() {

	}

	/**
	 * Set the images that are related to this question
	 * @param imgs that are related to this question
	 */
	public void addImages(List<Image> imgs) {
		this.imgs = imgs;
	}

	/**
	 * A demo that is aimed to test the UI and functionality
	 */
	public void setDemo1() {
		// set description and choices
		this.description = "What is the name of the computer sciences professors in the picture on "
				+ "the right? (Select one answer)";
		
		this.choices = new String[] { "Deb Deppeler", "Gary Dahl", "Jim Williams", "Marc Renault" };
	}

	/**
	 * Another demo that is aimed to test the UI and functionality
	 */
	public void setDemo2() {
		// set description and choices
		this.description = "Is Bradley the best coder in UW-Madison?";
		this.choices = new String[] { "Yes", "I bet", "he is" };
	}

	/**
	 * 
	 */
	public void setDemo3() {
		// set description and choices
		this.description = "What do you think of the weather of Madison?";
		this.choices = new String[] { "very cozy", "cold", "hell cold" };
	}

	/**
	 * return the description of the question
	 * @return the description of the question
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getChoices() {
		return choices;
	}

	/**
	 * 
	 * @param choices
	 */
	public void setChoices(String[] choices) {
		this.choices = choices;
	}

}
