package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private TextView mQuestionTextView;
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {					//Array of true/false questions
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {			//View.OnClickListener interface
			@Override
			public void onClick(View v) {
//				Toast.makeText(QuizActivity.this, 							//this refers to View.OnClickListener
//						       R.string.incorrect_toast, 
//						       Toast.LENGTH_SHORT).show();
				checkAnswer(true);
			}
		});
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {	    //Listener is Inner anonymous class
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mNextButton = (Button) findViewById(R.id.next_button);					//Next Button wired up
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;		//Round about array
				updateQuestion();
			}
		});
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);	//Wired up to question_text_view
		updateQuestion();
	}
	
	private void updateQuestion() {
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageReID = 0;
		
		if (userPressedTrue == answerIsTrue) {
			messageReID = R.string.correct_toast;
		}
		else if (userPressedTrue != answerIsTrue) {
			messageReID = R.string.incorrect_toast;
		}
		
		Toast.makeText(QuizActivity.this, messageReID, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

}
