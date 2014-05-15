package demo.project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onPause() {
		super.onPause();
		// TODO:
	}

	@Override
	protected void onResume() {
		super.onResume();
		// TODO:
	}

	protected void onDestroy() {
		super.onDestroy();
		// TODO:
	}

	protected void onStart() {
		super.onStart();
		// TODO:
	}

	protected void onStop() {
		super.onStop();
		// TODO:
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TODO:
	}

}
