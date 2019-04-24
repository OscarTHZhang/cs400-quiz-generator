package application;

import java.util.List;

import javafx.scene.image.Image;

public class Question {
	private String description;
	private String[] choices;
	private String topic;
	private List<String> keys;
	List<Image> imgs;

	public Question(String description, String[] choices, String topic, List<String> keys) {
		this.description = description;
		this.choices = choices;
		this.topic = topic;
		this.keys = keys;
	}

	public Question() {

	}

	public void addImages(List<Image> imgs) {
		this.imgs = imgs;
	}

	public void setDemo1() {
		this.description = "--What is the name of the computer sciences professors in the picture on the right? (Select one answer)";
		this.choices = new String[] { "Deb Deppeler", "Gary Dahl", "Jim Williams", "Marc Renault" };
	}

	public void setDemo2() {
		this.description = "Is Bradley the best coder in UW-Madison ?";
		this.choices = new String[] { "Yes", "I bet", "he is" };
	}

	public void setDemo3() {
		this.description = "What do you think of the weather of Madison ?";
		this.choices = new String[] { "very cozy", "cold", "hell cold" };
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getChoices() {
		return choices;
	}

	public void setChoices(String[] choices) {
		this.choices = choices;
	}

}
