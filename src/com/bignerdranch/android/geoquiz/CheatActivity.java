package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
	private static final String ANSWER_SHOWN = "shown";
	private boolean mAnswerIsTrue;
	private boolean mIsAnswerShown;
	private TextView mAnswerTextView;
	private Button mShowAnswer;
	private TextView mSDKVersionTextView;
	private String mSDKVersion;
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		mIsAnswerShown = isAnswerShown;
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(ANSWER_SHOWN, mIsAnswerShown);
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
		
		mSDKVersionTextView = (TextView) findViewById(R.id.SDK_version_view);	//Wired up to the view XML set
		mSDKVersionTextView.setText(mSDKVersion = "API LEVEL " + Build.VERSION.SDK_INT);
		
		if (savedInstanceState != null) {
			setAnswerShownResult(savedInstanceState.getBoolean(ANSWER_SHOWN));
		} else {
			setAnswerShownResult(false);										//Answer will not be shown first
		}
		
		mShowAnswer = (Button) findViewById(R.id.show_answer_button);
		mShowAnswer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);			//SetText on View to show the string to the user
				}
				else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShownResult(true);
			}
		});
	}

}
