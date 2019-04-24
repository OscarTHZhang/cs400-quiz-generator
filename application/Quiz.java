package application;

import java.util.List;

public class Quiz {
  private int questionCount;
  private List<String> userAnswer;
  private boolean ongoing = false;
  private List<Question> questions;
  private List<String> allTopic;
  
  private int currentQuestionIndex=-1;
  
  public Quiz(List<String> allTopic, int questionCount) {
    this.allTopic= allTopic;
    this.questionCount = questionCount;
    this.currentQuestionIndex=0;
  }
  
  public Question currQuesiton() {
    return questions.get(currentQuestionIndex);
  }

  
  public void next() {
    currentQuestionIndex++;
  }
  public void prev() {
    currentQuestionIndex--;
  }
  
  public void answer(String answer) {
    userAnswer.set(currentQuestionIndex, answer);
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

}
