package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {								//Subclass of Activity class
	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private ImageButton mNextButton;
	private ImageButton mPrevButton;
	private TextView mQuestionTextView;
	private static final String TAG = "QuizActivity";						//Tag value is the class name for easy identification
	private static final String KEY_INDEX = "index";
	private boolean mIsCheater;												//Hold the value CheatActivity is passing back
	
	private TrueFalse[] mQuestionBank = new TrueFalse[] {					//Array of true/false questions
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true),
	};
	
	private int mCurrentIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {					//onCreate method of the activity subclasses
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);
		
		if (savedInstanceState != null) {									//Check for previous saved state
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
		}
		
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
		
		mNextButton = (ImageButton) findViewById(R.id.next_button);					//Next Button wired up
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;		//Round about array
				mIsCheater = false;
				updateQuestion();
			}
		});
		
		mPrevButton = (ImageButton) findViewById(R.id.prev_button);					//Prev button (ImageButton instead of Button)
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex-1+mQuestionBank.length) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuizActivity.this, CheatActivity.class);		//Prepare intent for ActivityManager
				boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion(); 
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);      //Put extras on intent
				startActivityForResult(intent, 0);										//Start another activity & expecting a return
			}
		});
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);	//Wired up to question_text_view
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
				updateQuestion();
			}
		});
		
		updateQuestion();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}
	
	private void updateQuestion() {
//		Log.d(TAG, "Update question text for question #" + mCurrentIndex, new Exception()); //Logs the entire stack and show where
		int question = mQuestionBank[mCurrentIndex].getQuestion();							//the call to updateQuestion was made
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		
		int messageReID = 0;
		if (mIsCheater) {												//First check if the user has cheated
			messageReID = R.string.judgement_toast;
		}
		else if (userPressedTrue == answerIsTrue) {
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {		//Override to save additional data
		super.onSaveInstanceState(savedInstanceState);					//Default: asks all activity's views to save their state as data in Bundle object
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);			//Bundle is a structure like HashTable
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //Handle extras returned from "children" activities
		if (data == null) {
			return;
		}																			//Default is false
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, true);	//Use the String key declared public in the child activity
	}
}
