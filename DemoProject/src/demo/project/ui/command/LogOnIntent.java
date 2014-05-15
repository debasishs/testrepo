package demo.project.ui.command;

import android.content.Context;
import demo.project.ui.LogOnActivity;
import demo.project.util.DemoCommand;
import demo.project.vo.UserVO;

public class LogOnIntent implements DemoCommand {
	UserVO mUserVO;
	public static boolean isLockedUser;
	
	public LogOnIntent(Context mContext, Context baseContext)
	{
	}
	
	@Override
	public Object command(Object... params) {
		
		mUserVO = (UserVO)params[0];
		if (!mUserVO.isLogOnSuccess()) {	
			LogOnActivity.logOnHandler.sendMessage(LogOnActivity.logOnHandler.obtainMessage(101));
		}
		return null;
	}
	
}
