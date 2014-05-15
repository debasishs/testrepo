package demo.project.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import demo.project.vo.ApplicationVO;
import demo.project.vo.UserVO;

public class DemoPreferences {
	private static DemoPreferences mDemoPreferences = new DemoPreferences();
	private UserVO userVO;
	private ApplicationVO applVO;
	private Context mContext;
	public static String username = null;

	private DemoPreferences() {

	}

	public static DemoPreferences getInstance() {
		return mDemoPreferences;
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context context) {
		this.mContext = context;
	}

	public UserVO getPreferences() {
		if (userVO != null) {
			return userVO;
		}
		return userVO;
	}

	

	private SharedPreferences getSharedPreferences(String preferencesName,
			int mode) {

		if (preferencesName == null || preferencesName.trim().length() == 0) {
			return null;
		}

		if (mode == Context.MODE_PRIVATE) {
			return mContext.getSharedPreferences(preferencesName,
					Context.MODE_PRIVATE);
		} else {
			return null;
		}

	}

	// APPLICATION PREFERENCES
	public ApplicationVO getApplPreferences() {
		if (applVO != null) {
			return applVO;
		}
		readApplicationPreferences();
		return applVO;
	}

	private void readApplicationPreferences() {

		applVO = ApplicationVO.getSingleInstance();
		SharedPreferences applSharedPrefer = getSharedPreferences(
				DemoConstants.DEMO_PREFERENCES, Context.MODE_PRIVATE);
		if (applSharedPrefer != null) {
			String encryptionKey = applSharedPrefer.getString(
					DemoConstants.DEMO_ENCRYPTION_KEY, "");

			applVO.setEncryptionKey(encryptionKey);
		}

	}

	public void saveApplicationPreferences() {
		SharedPreferences applSharedPrefer = getSharedPreferences(
				DemoConstants.DEMO_PREFERENCES, Context.MODE_PRIVATE);

		if (applSharedPrefer != null) {
			Editor editor = applSharedPrefer.edit();

			editor.putString(DemoConstants.DEMO_ENCRYPTION_KEY,
					applVO.getEncryptionKey());
			editor.commit();
		}

	}

}
