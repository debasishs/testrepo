package demo.project.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import demo.project.ui.command.EncryptionKeyCommand;
import demo.project.ui.command.LogOnCommand;
import demo.project.ui.command.LogOnIntent;
import demo.project.util.AESEncryptor;
import demo.project.util.DEMOAsyncTask;
import demo.project.util.DemoConstants;
import demo.project.util.Utility;
import demo.project.vo.ApplicationVO;
import demo.project.vo.UserVO;


@SuppressLint("HandlerLeak")
public class LogOnActivity extends BaseActivity implements OnClickListener{
	private Button logonButton;
	private Context mContext = this;
	private EditText userNameText;
	private EditText passwordText;
	public static String userName = "";
	public static String password = "";
	public static Handler logOnHandler;

	String title = DemoConstants.EMPTY_STRING;
	String message = DemoConstants.EMPTY_STRING;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userNameText = (EditText) findViewById(R.id.usernametxt);
		passwordText = (EditText) findViewById(R.id.passwordtxt);
		passwordText.setTypeface(Typeface.DEFAULT);

		if(ApplicationVO.getSingleInstance().getEncryptionKey().length() <= 0){
			getEncryptionKey();
		}

		SharedPreferences applicationSharedPrefer=mContext.getSharedPreferences(DemoConstants.DEMO_PREFERENCES,Context.MODE_PRIVATE);
		if(applicationSharedPrefer != null)
		{
			String last_logged_in_username = applicationSharedPrefer.getString(DemoConstants.LAST_SUCCESSFUL_LOGGED_USER,"");
			if(Utility.checkStringForNullOrEmpty(last_logged_in_username)){
				userNameText.setText(AESEncryptor.decryptForLocalStorage(last_logged_in_username, mContext));
				passwordText.requestFocus();
			}
		}

		userNameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {
				//TODO:
			}
		});

		userNameText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		userNameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {	

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {

					userName = userNameText.getText().toString();
					password = passwordText.getText().toString();

					if (userName.length() < 1) {
						userNameText.requestFocus();
						userNameText.setError("Enter your Username");
					} else if (password.length() < 1) {
						passwordText.requestFocus();

					} else {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(userNameText.getWindowToken(), 0);
						startLogonCommand();
					}

					return true;
				}
				return false;

			}

		});

		passwordText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE ) {

					userName = userNameText.getText().toString();
					password = passwordText.getText().toString();

					if (userName.length() < 1) {
						userNameText.requestFocus();

					} else if (password.length() < 1) {
						passwordText.requestFocus();
						passwordText.setError("Enter your Password");
					} else {
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
						imm.hideSoftInputFromWindow(userNameText.getWindowToken(), 0);
						startLogonCommand();
					}

					return true;
				}
				return false;
			}
		});

		logonButton = (Button) findViewById(R.id.logon);
		logonButton.setOnClickListener(this);

		logOnHandler=new Handler(){
			public void handleMessage(Message msg){
				if(msg.what==100){
					setResult(RESULT_OK, null);
				}
				if(msg.what==101){
					try{
						if(UserVO.getSingleInstance().getLoginErrorMsg().equals(DemoConstants.INVALID_PASSWORD_COMPLIANCE))
						{
							//TODO: Reset Password
						}
						else if(UserVO.getSingleInstance().getLoginErrorMsg().equals(DemoConstants.USER_NOT_VERIFIED))
						{
							//TODO: User not verified
						}
						else
						{
							//TODO: Show alert Invalid Username or Password
						}
					}catch(Exception e){
						new Utility().showAlertDialog(LogOnActivity.this, null, "Error communicating.", null);
					}
				}

			}
		};

	}

	private void startLogonCommand(){
		System.out.println("Starting logon command");
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		DEMOAsyncTask loginTask = new DEMOAsyncTask(mContext.getString(R.string.logging_on), mContext,
				new LogOnCommand(userName, password, mContext),
				new LogOnIntent(mContext, getBaseContext()));
		loginTask.execute();
	}

	private void getEncryptionKey(){
		System.out.println("Starting encryption download");
		DEMOAsyncTask encryptionKeyDownloadTask = new DEMOAsyncTask(mContext.getString(R.string.logging_on), mContext,
				new EncryptionKeyCommand(),
				null);
		encryptionKeyDownloadTask.execute();
	}

	public void onResume(){
		super.onResume();
		//TODO: Do work in on resume
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.logon:

			userName = userNameText.getText().toString().trim();
			password = passwordText.getText().toString();
			if (userName.trim().length() < 1) {
				userNameText.setText(DemoConstants.EMPTY_STRING);
				userNameText.requestFocus();
				userNameText.setError("Enter your Username");
			} else if (password.length() < 1) {
				passwordText.requestFocus();
				passwordText.setError("Enter your Password");
			} else {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(userNameText.getWindowToken(), 0);
				startLogonCommand();
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK)
		{
			Intent intent = new Intent(LogOnActivity.this, HomeActivity.class);
			LogOnActivity.this.startActivity(intent);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
