package flashcards;

import java.io.Serializable;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5015219042791183332L;
	// Fields
	private Category mCat;
	private String mQuestion;
	private String mAnswer;
	private boolean hard;
	
	// Constructor
	public Card(String question, String answer, Category cat) {
		this.mQuestion = question;
		this.mAnswer = answer;
		this.mCat = cat;
		this.hard = true;
	}
	
	public Card(String question, String answer) {
		this(question, answer, null);
	}
	
	// getters setters
	public String getQuestion() {
		return mQuestion;
	}
	public void setQuestion(String newQuestion) {
		mQuestion = newQuestion;
	}
	public String getAnswer() {
		return mAnswer;
	}
	public void setAnswer(String newAnswer) {
		mAnswer = newAnswer;
	}
	public Category getCategory() {
		return mCat;
	}
	public void setCategory(Category newCat) {
		mCat = newCat;
	}
	public boolean isHard() {
		return this.hard;
	}
	public void setHard(boolean hard) {
		this.hard = hard;
	}
	
	// Methods
	// toString
	public String toString() {
		return String.format("Card [Q: '%s'; A: '%s'; Cat: '%s']", mQuestion, mAnswer, mCat);
	}
	// equals
	public boolean equals(Card other) {
		if (!other.mAnswer.equals(mAnswer)) {
			return false;
		}
		if (!other.mQuestion.equals(mQuestion)) {
			return false;
		}
		if (!other.mCat.equals(mCat)) {
			return false;
		}
		return true;
	}
	
	// Special Methods
}
