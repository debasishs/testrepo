package demo.project.util;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.LinearLayout;
import demo.project.ui.HomeActivity;
import demo.project.ui.LogOnActivity;

public class Utility {

	public Utility() {

	}


	public static boolean isNetworkAvailable(Context myContext) {
		ConnectivityManager cm = (ConnectivityManager) myContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		return (info != null && info.isConnected());
	}

	static HashMap< String, String> backEnabledActivities = new HashMap<String, String>();
	static
	{
		backEnabledActivities.put(LogOnActivity.class.toString(), null);
		backEnabledActivities.put(HomeActivity.class.toString(), null);
		
	}
	

	
	@SuppressWarnings("unused")
	private static void closeInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception ex) {
        }
    }
	
	public static boolean checkStringForNullOrEmpty(String str){
		if(str==null){
			return false;
		}
		else if(str.trim().equals(DemoConstants.EMPTY_STRING)){
			return false;
		}
		else if(str.equals("null")){
			return false;
		}
		else
			return true;
	}
	
	@SuppressWarnings("deprecation")
	public static View getDividerView(Context context){
		View divider=new View(context);
		divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,1));
		LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)divider.getLayoutParams();
		params.setMargins(0, 5, 0, 5);
		divider.setLayoutParams(params);
		divider.setBackgroundColor(Color.GRAY);
		
		return divider;
	}
	
	@SuppressWarnings("deprecation")
	public static View getDividerViewWithHeight(Context context , int height , int color){
		View divider=new View(context);
		divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,height));
		LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)divider.getLayoutParams();
		params.setMargins(0, 5, 0, 5);
		divider.setLayoutParams(params);
		divider.setBackgroundColor(color);
		
		return divider;
	}
	
	
	
	public void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener){
		showAlertDialog(context, title, message, listener, null);
	}
	
	public void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener, DialogInterface.OnKeyListener keyListener){
		AlertDialog dialog = returnAlertDialog(context, title, message, listener);
		
		if(Utility.checkStringForNullOrEmpty(title))
			dialog.setTitle(title);
		
		if(keyListener != null){
			dialog.setOnKeyListener(keyListener);
		}
		
		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AlertDialog returnAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener){
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
		
		/*if(checkStringForNullOrEmpty(title)){
			alertBuilder.setTitle(title);
		}*/
		
		if(checkStringForNullOrEmpty(message)){
			alertBuilder.setMessage(message);
		}
		
		if(listener!=null){
			alertBuilder.setPositiveButton(DemoConstants.DIALOG_OK, listener);
		}
		else{
			alertBuilder.setPositiveButton(DemoConstants.DIALOG_OK, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
		}
		
		AlertDialog dialog = alertBuilder.create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
	

	public static int convertToPixels(Context context, int nDP)
	{
		final float conversionScale = context.getResources().getDisplayMetrics().density;
		 
		return (int) ((nDP * conversionScale) + 0.5f) ;
		
	}
	
	public boolean isNetworkAvaiable(Context ctx){
		 ConnectivityManager connectivityManager;
		 NetworkInfo wifiInfo, mobileInfo,activeNetworkInfo;
	     connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	     activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	     wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	     mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   
	     
	     if((activeNetworkInfo!= null && activeNetworkInfo.isConnected()) || wifiInfo.isConnected() || mobileInfo.isConnected())
	           	return true;
	     else
	            return false;
	}
	
	
	public static String getJSONString(HashMap<String, String> values){
		if(values != null && values.size() > 0){
			JSONObject jObj = new JSONObject();
			for(String key: values.keySet()){
				try {
					jObj.put(key, values.get(key));
				} catch (JSONException e) {
					return null;
				}
			}
			return jObj.toString();
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static String DemoURLEncoder(String str){
		str = URLEncoder.encode(str);
		str.replace("+", "%20");
		str.replace("*", "%2A");
		return str;
	}

}
