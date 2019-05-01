/**
 * Project: CS 400 Final Project
 * Name:    Quiz Generator
 * A-team:  #23
 * 
 * Credit:
 * 
 */

package application;

import java.util.ArrayList;
import java.util.List;

/**
 * The Quiz class that holds all questions in the quiz generator application
 * 
 * @author Bradley
 *
 */
public class Quiz {
	
	private int questionCount; // number of questions in the quiz
	private List<List<String>> userAnswer; // user's answer
	private List<Question> questions;// all questions in the quiz
	@SuppressWarnings("unused")
	private List<String> allTopic; // topics that this quiz should cover
	private int currentQuestionIndex = -1; // index of current question
	
	/**
	 * Empty constructor of quiz class
	 */
	public Quiz() {
		
	}
	
	/**
	 * Constructor of Quiz class
	 * 
	 * @param allTopic      list of all topics
	 * @param questionCount number of questions
	 */
	public Quiz(List<String> allTopic, int questionCount) {
		this.allTopic = allTopic;
		this.questionCount = questionCount;
		this.currentQuestionIndex = 0;
		this.userAnswer = new ArrayList<>();
		for (int i = 0; i < questionCount; i++)
			userAnswer.add(new ArrayList<>());
	}
	
	
	/**
	 * set the topic list of this quiz
	 * @param list of topics
	 */
	public void setTopic(List<String> topics) {
		allTopic = topics;
	}
	
	/**
	 * set the question count
	 * @param the number of question in the quiz
	 */
	public void setQuestionCount(int num) {
		questionCount = num;
	}
	
	/**
	 * generate questions from the file system according to the topic
	 */
	public void generateQuestions() {
		// idea: traverse through the QUESTION_POOL to find the relevant topic 
		for (Question q: MainMenuScene.QUESTION_POOL) {
			if (this.allTopic.contains(q.getTopic()))
				questions.add(q);
		}
	}
	
	/**
	 * getter of current question
	 * 
	 * @return current question
	 */
	public Question currQuesiton() {
		return questions.get(currentQuestionIndex);
	}

	/**
	 * move to next question
	 */
	public void next() {
		currentQuestionIndex++;
	}

	/**
	 * go back to last question
	 */
	public void prev() {
		currentQuestionIndex--;
	}

	/**
	 * store the user's answer to quiz
	 * 
	 * @param answer user's answer
	 * @return true if it is already in the quiz
	 */
	public boolean answer(String answer) {
		boolean answered = userAnswer.get(currentQuestionIndex).contains(answer);
		userAnswer.get(currentQuestionIndex).add(answer);
		return answered;
	}

	/**
	 * return user's answer for this question
	 * 
	 * @return user's answer for this question
	 */
	public List<String> checkAnswer() {
		return userAnswer.get(currentQuestionIndex);
	}

	/**
	 * getter of questionCount
	 * 
	 * @return questionCount
	 */
	public int getQuestionCount() {
		return questionCount;
	}

	/**
	 * getter of questions
	 * 
	 * @return questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * set questions
	 * 
	 * @param questions
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * getter of currentQuestionCount
	 * 
	 * @return currentQuestionCount
	 */
	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	/**
	 * getter of userAnswer
	 * 
	 * @return userAnswer
	 */
	public List<List<String>> getUserAnswer() {
		return userAnswer;
	}
	
}
