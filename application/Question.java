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
	private String imgPath; // a list of images that may be related to this question
	private boolean answered; // flag that shows if this question is answered or not

	/**
	 * empty constructor of the question
	 */
	public Question() {
		
	}
	
	/**
	 * The constructor of the Question class that takes in the parameters
	 * 
	 * @param description of the question
	 * @param choices of the question
	 * @param topic that the question is affiliated to
	 * @param keys true answer for this question
	 */
	public Question(String description, Choice[] choices, String topic, String imgPath) {

		// assign the parameters to the class attributes
		this.description = description;
		this.choices = choices;
		this.topic = topic;
		this.imgPath = imgPath;
		this.answered = false;
	}

	/**
	 * show if the question is answered or not
	 * @return true if it is answered; false otherwise
	 */
	public boolean isAnswered() {
		return answered;
	}

	/**
	 * set the flag for this question answered or not
	 * @param answered true for answered; false for not answered; passed into the class attributes
	 */
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	/**
	 * get the question description
	 * @return the question description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * set the question description
	 * @param description that is going to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * get the array of answer choices
	 * @return answer choices
	 */
	public Choice[] getChoices() {
		return choices;
	}

	/**
	 * set the array of answer choices
	 * @param choices the array of answer choices that is going to be set
	 */
	public void setChoices(Choice[] choices) {
		this.choices = choices;
	}

	/**
	 * get the topic of this question
	 * @return topic of this question
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * set the topic of this question
	 * @param topic that is going to be set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * get the image this question involved
	 * @return the image this question involved
	 */
	public Image getImg() {
		if (imgPath == null || imgPath.equals("none")) {
			return null;
		} else {
			return new Image(imgPath, true);
		}
	}

	/**
	 * get the image path of the image this question involved
	 * @return the image path of the image
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * set the path of the image
	 * @param imgPath path that is going to be set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
