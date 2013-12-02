package com.bignerdranch.android.geoquiz;

public class TrueFalse {
	private int mQuestion;					//Holds the ID of question string resource
	private boolean mTrueQuestion;			//Indicates of statement is true/false
	
	public TrueFalse(int question, boolean trueQuestion) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
	}
	
	public int getQuestion() {
		return mQuestion;
	}
	
	public void setQuestion(int question) {
		mQuestion = question;
	}
	
	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}
	
	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
	
	public static void main(String[] args) {

	}

}
