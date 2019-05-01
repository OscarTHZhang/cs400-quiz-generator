package application;

public class Choice {
	
	private boolean isCorrect ;
	private String choiceLabel;
	private String choiceDescription;
	
	public Choice() {
		isCorrect = false;
		choiceLabel = "";
		choiceDescription = "";
	}
	
	public Choice(boolean correct, String description) {
	     isCorrect = correct;
	     choiceDescription = description;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getChoiceLabel() {
		return choiceLabel;
	}

	public void setChoiceLabel(String choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

	public String getChoiceDescription() {
		return choiceDescription;
	}

	public void setChoiceDescription(String choiceDescription) {
		this.choiceDescription = choiceDescription;
	}
	
	public boolean equals(Choice choice) {
	  if (this.getChoiceDescription().equals(choice.getChoiceDescription())) return true;
    return false;
	  
	}
	
}
