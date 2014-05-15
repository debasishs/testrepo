package demo.project.ui.command;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import demo.project.service.client.DemoServiceException;
import demo.project.service.client.ServiceDelegate;
import demo.project.ui.LogOnActivity;
import demo.project.util.AESEncryptor;
import demo.project.util.DemoCommand;
import demo.project.util.DemoConstants;
import demo.project.util.DemoPreferences;
import demo.project.vo.ApplicationVO;
import demo.project.vo.UserVO;


public class LogOnCommand implements DemoCommand{
		private String userName;
		private String password;
		private Context mContext;

		private UserVO mUserVO;
		public LogOnCommand(String userName , String password, Context context)
		{
			this.userName = userName;
			this.password = password;
			this.mContext = context;
		}	
	
	@Override
	public Object command(Object... params) {
		
		try {
			String username_encrypted= new AESEncryptor().getEncryptedString(userName, ApplicationVO.getSingleInstance().getEncryptionKey());
			String password_encrypted= new AESEncryptor().getEncryptedString(password, ApplicationVO.getSingleInstance().getEncryptionKey());
			mUserVO = ServiceDelegate.getSingleInstance().login(username_encrypted, password_encrypted, mContext);
			
			if (mUserVO.isLogOnSuccess()) {
				
				
				DemoPreferences preferences = DemoPreferences.getInstance();
				preferences.setContext(mContext);
				UserVO userPreferencesVO = preferences.getPreferences();
				userPreferencesVO.setAppForFirstTime(false);
				userPreferencesVO.setLogOnSuccess(true);
				
				SharedPreferences applicationSharedPrefer=mContext.getSharedPreferences(DemoConstants.DEMO_PREFERENCES,Context.MODE_PRIVATE);
				
				if(applicationSharedPrefer != null)
				{
					Editor editor = applicationSharedPrefer.edit();
					editor.putString(DemoConstants.LAST_SUCCESSFUL_LOGGED_USER,AESEncryptor.encryptForLocalStorage(userName, mContext));
					editor.commit();
				}
							
				preferences.saveApplicationPreferences();
				
				if(LogOnActivity.logOnHandler != null){
					LogOnActivity.logOnHandler.sendMessage(LogOnActivity.logOnHandler.obtainMessage(100));
				}
				
				return mUserVO;
			}
			
		} catch (DemoServiceException e) {
			e.printStackTrace();
		}
		return mUserVO;
	}
}
