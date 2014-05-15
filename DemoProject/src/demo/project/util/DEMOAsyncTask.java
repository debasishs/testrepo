package demo.project.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.KeyEvent;
import demo.project.service.client.DemoServiceException;
import demo.project.service.client.Network;


public class DEMOAsyncTask extends AsyncTask<Object, Void, Object> {
	private ProgressDialog waitDialog;
	private String mWaitMsg = null;
	private Context mContext;
	private DemoCommand mCommandAct;
	private DemoCommand mComIntent;
	public static boolean isDEMOAsyncTaskRunning = false;


	public DEMOAsyncTask(String message, Context context, Object command,
			Object intent) {
		this.mWaitMsg = message;
		this.mContext = context;
		this.mCommandAct = (DemoCommand) command;
		this.mComIntent = (DemoCommand) intent;

	}

	public DEMOAsyncTask(Context context, ProgressDialog waitDialog, Object command,
			Object intent){
		this.waitDialog = waitDialog;
		this.mContext = context;
		this.mCommandAct = (DemoCommand) command;
		this.mComIntent = (DemoCommand) intent;
	}

	public void onPreExecute() {
		if(isDEMOAsyncTaskRunning == false)
		{
			super.onPreExecute();

			if(waitDialog != null){
				try{
					waitDialog.show();
				}
				catch (Exception e) {

				}
			}

			else if(this.mWaitMsg!=null){
				waitDialog = new ProgressDialog(mContext);
				waitDialog.setMessage(mWaitMsg);
				waitDialog.setCanceledOnTouchOutside(false);
				waitDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK
								&& waitDialog.isShowing()) {

							if(mComIntent!=null && isBackEnabledForAsyncTask(mComIntent.getClass().toString()))
							{
								isDEMOAsyncTaskRunning =false;
								DEMOAsyncTask.this.cancel(true);
								waitDialog.dismiss();
								if(mComIntent.getClass().getName().contains("command.ResultItemIntent"));
								//TODO: Update UI
							}
							else
								return true;	

						}
						return false;
					}
				});
				try{
					waitDialog.show();
				}
				catch (Exception e) {
					// TODO: handle exception
				}

			}
		}
		else
		{
			DEMOAsyncTask.this.cancel(true);
		}
	}


	public Object doInBackground(Object... params) {

		Object result;
		result = mCommandAct.command(params);

		if (result == null)
			return Boolean.FALSE;
		else
			return result;

	}

	public void onPostExecute(Object result) {
		if(result == Boolean.FALSE || result instanceof DemoServiceException) {
			if(waitDialog!=null && mContext != null){
				if(!Network.hasTokenExpirationErrorOccured){
					String title = "Network Error";
					String message= "There was an error while trying to complete your request. Please try again later.";	
					DialogInterface.OnClickListener listener = null;
					DialogInterface.OnKeyListener keyListener = null;

					if(result instanceof DemoServiceException) {
						DemoServiceException errorDetails=null;
						errorDetails = (DemoServiceException)result;

						if(Utility.checkStringForNullOrEmpty(errorDetails.getCustomErrorMessage())){
							message = errorDetails.getCustomErrorMessage();
						}

						if(Utility.checkStringForNullOrEmpty(errorDetails.getTitle())){
							title = DemoConstants.DIALOG_ERROR;
						}

						if(errorDetails.getAlertDialogOnClicklistener() != null){
							listener = errorDetails.getAlertDialogOnClicklistener();
						}

						if(errorDetails.getAlertDialogKeyListener() != null){
							keyListener = errorDetails.getAlertDialogKeyListener();
						}
					} 

					new Utility().showAlertDialog(mContext, title, message, listener, keyListener);
				}
				try {
					waitDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}else {
			if(mComIntent!=null)
			{
				mComIntent.command(result);	
			}
			if(waitDialog!=null){
				try {
					waitDialog.dismiss();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			isDEMOAsyncTaskRunning =false;
		}
	}
	private boolean isBackEnabledForAsyncTask(String intentActivity)
	{
		if(Utility.backEnabledActivities.containsKey(intentActivity))
			return true;
		else
			return false;
	}
}