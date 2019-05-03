package application;

public class Choice {

	private boolean isCorrect;
	private String choiceLabel;
	private String choiceDescription;

	public Choice() {
		isCorrect = false;
		choiceLabel = "";
		choiceDescription = "";
	}

	public Choice(boolean correct, String description) {
		isCorrect = correct;
		
		// process question title - Start
        int tmpLength = description.length();
        int tmpIndex = 1;
        int lengthLimit = 40;
        while (tmpLength-1 > tmpIndex) {
            if(tmpIndex%lengthLimit==0) { 
              while(!description.substring(tmpIndex, tmpIndex+1).equals(" ")) {
                if(tmpIndex==tmpLength-1) break;
                tmpIndex++;
              }
              description=description.substring(0,tmpIndex+1)+"\n"+description.substring(tmpIndex+1);
              tmpIndex++; 
            }
            tmpIndex++;
        }
        
        // process question title - End
		
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
		if (this.getChoiceDescription().equals(choice.getChoiceDescription()))
			return true;
		return false;
	}

}
