package application;

/**
 * This class is an abstraction of an answer choice in a question
 * @author Oscar Zhang, Haochen Shi, Bradley Mao, Peter Pan
 *
 */
public class Choice {

	private boolean isCorrect; // flag that shows if the choice is correct or not
	private String choiceLabel; // the label of the choice
	private String choiceDescription; // the description of the choice

	/**
	 * The constructor of the choice class; it has empty parameters so it sets all the fields to the
	 * default
	 */
	public Choice() {
		// set the fields to default
		isCorrect = false;
		choiceLabel = "";
		choiceDescription = "";
	}

	/**
	 * The constructor of the choice class with parameters
	 * @param correct the correctness of this choice with respect to the question
	 * @param description the description of the choice
	 */
	public Choice(boolean correct, String description) {
		isCorrect = correct;
		
		// process question title - Start (make sure that the changing-line is set correctly for
		// the display on the UI)
        int tmpLength = description.length();
        int tmpIndex = 1;
        int lengthLimit = 40;
        // traverse the description to detect breaking point
        while (tmpLength-1 > tmpIndex) {
            if(tmpIndex%lengthLimit==0) { 
            	// find the correct space for breaking the line
            	while(!description.substring(tmpIndex, tmpIndex+1).equals(" ")) {
            		if(tmpIndex==tmpLength-1) break;
            		tmpIndex++;
              }
              // detected the breaking point and setting the new breaking point for the line
              description=description.substring(0,tmpIndex+1)+"\n"+description.substring(tmpIndex+1);
              tmpIndex++; 
            }
            tmpIndex++;
        }
        // process question title - End
		choiceDescription = description; // assign the description
	}

	/**
	 * check the flag of correctness
	 * @return the flag of correctness isCorrect
	 */
	public boolean isCorrect() {
		return isCorrect;
	}

	/**
	 * set the correctness of the choice
	 * @param isCorrect the correctness of the choice that will be set
	 */
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	/**
	 * get the label of the choice
	 * @return label of the choice
	 */
	public String getChoiceLabel() {
		return choiceLabel;
	}

	/**
	 * set the label of the choice
	 * @param choiceLabel that is going to be set to the field
	 */
	public void setChoiceLabel(String choiceLabel) {
		this.choiceLabel = choiceLabel;
	}

	/**
	 * get the description of the choice
	 * @return the description of the choice
	 */
	public String getChoiceDescription() {
		return choiceDescription;
	}

	/**
	 * set the description of the choice
	 * @param choiceDescription the description of the choice that will be set to
	 */
	public void setChoiceDescription(String choiceDescription) {
		this.choiceDescription = choiceDescription;
	}

	/**
	 * compare the description of two choices; determine if they are the same
	 * @param choice the other choice that is compared to this choice
	 * @return true if two choices are the same; false otherwise
	 */
	public boolean equals(Choice choice) {
		// the choices are the same if the descriptions of the choices are the same
		if (this.getChoiceDescription().equals(choice.getChoiceDescription()))
			return true;
		return false; // return false if the description is not the same
	}

}
