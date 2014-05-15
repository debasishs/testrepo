package demo.project.service.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import demo.project.util.DemoCommand;

public class DemoApplication extends Application
{
	private static boolean mIsAppRunning = false;
	private static HashMap<String, Activity> mAppActivityStack = new HashMap<String, Activity>();
	private static int mShowingActivityCounter = 0;
	public static boolean hasTokenRenewalAlarmStarted = false;
	public static CopyOnWriteArrayList<DemoCommand> onNetworkReceiveCommandList = new CopyOnWriteArrayList<DemoCommand>();
	private static volatile boolean mDialogShowing = false;
	
	public static Context applicationContext;
	public static boolean isButtonClicked = false;
	
	@Override
	public void onCreate()
	{
		setEndpoints();
		
		applicationContext = this;
	}
	
	private void setEndpoints(){
		//TODO: Set API end points if any
	}
	
	/**
	 * @author Quinnox
	 * Setter for isAppRunning
	 * @param bRunning
	 */
	public static void setAppRunning(boolean bRunning)
	{
		mIsAppRunning = bRunning;
	}
	
	/**
	 * @author Quinnox
	 * Getter for IsAppRunning
	 * @return
	 */
	public static boolean isAppRunning()
	{
		return mIsAppRunning;
	}
	
	/**
	 * @author Quinnox
	 * Increment the counter which stores the number of activities currently showing 
	 * Note: It includes the activities paused / resumed, and only counts the activities
	 * that are started / stopped
	 */
	public static void incrementShowActivityCounter() { mShowingActivityCounter++; }
	
	/**
	 * @author Quinnox
	 * Decrement the counter which stores the number of activities currently showing 
	 * Note: It includes the activities paused / resumed, and only counts the activities
	 * that are started / stopped
	 */
	public static void decrementShowActivityCounter() { mShowingActivityCounter--; }
	
	/**
	 * @author Quinnox
	 * This function will return the showing activity count. This is used to keep track 
	 * whether all the activities in the application have been minimized / are showing
	 */
	public static int  getShowActivityCount()	{ return mShowingActivityCounter;	}
	
	/**
	 * @author Quinnox
	 * Register the activity with the application class
	 * @param activity
	 */
	public static void registerActivity(Activity activity)
	{
//		Log.d("BR", "registering -> " + activity.getLocalClassName());
		mAppActivityStack.put(activity.getLocalClassName(), activity);
	}
	
	/**
	 * @author Quinnox
	 * Unregister the activity with the application class
	 * @param activity
	 */
	public static void unRegisterActivity(Activity activity)
	{
//		Log.d("BR", "Unregistering -> " + activity.getLocalClassName());
		mAppActivityStack.remove(activity.getLocalClassName());
	}
	
	/**
	 * @author Quinnox
	 * Clear the activity stack by finishing all the activities 
	 * (Can be used in case of a device shutdown / exit)
	 */
	public static void clearStack()
	{
		if(mAppActivityStack == null)
			return;
		
		Iterator<Map.Entry<String, Activity>> it = mAppActivityStack.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Activity> member = it.next();
			Activity activity = member.getValue();
			activity.finish();
			it.remove();
		}
	}

	public static synchronized CopyOnWriteArrayList<DemoCommand> getOnNetworkReceiveCommandList() {
		return onNetworkReceiveCommandList;
	}

	public static synchronized void setOnNetworkReceiveCommandList(CopyOnWriteArrayList<DemoCommand> onNetworkReceiveCommandList) {
		DemoApplication.onNetworkReceiveCommandList = onNetworkReceiveCommandList;
	}
	
	public static void showGeneralProgress(final Context context)
	{
		mDialogShowing = true;
		new DemoApplication.PGTask(context).execute();
	}
	
	public static class PGTask extends AsyncTask<Object, String, Void>
	{
		private Context mCtx = null;
		private ProgressDialog mPGDialog = null;
		
		public PGTask(Context ctx)
		{
			if(mCtx == null)
				mCtx = ctx;
		}
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mPGDialog = new ProgressDialog(mCtx);
			mPGDialog.setMessage("Please wait...");
			mPGDialog.setCancelable(true);
			mPGDialog.show();
		}
		
		
		@Override
		protected Void doInBackground(Object... params)
		{
			while(mDialogShowing)
			{
				// KIll time till mDialogShowing is false
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			if((mPGDialog != null) && (mPGDialog.isShowing())) 
				mPGDialog.dismiss();
		}
		
	}

	public static void stopGeneralProgress()
	{
		mDialogShowing = false;
	}
}
