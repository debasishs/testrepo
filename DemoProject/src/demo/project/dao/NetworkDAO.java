package demo.project.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import demo.project.service.client.DemoServiceException;
import demo.project.service.client.Network;
import demo.project.service.client.ServiceLocator;
import demo.project.service.client.VOAssembler;
import demo.project.ui.R;
import demo.project.util.DemoConstants;
import demo.project.util.Utility;
import demo.project.vo.EncryptionVO;
import demo.project.vo.UserVO;

class NetworkDAO  {
	private static final NetworkDAO INSTANCE = new NetworkDAO();
	private Network networkActivator = new Network();
	private VOAssembler voAssembler = new VOAssembler();

	private NetworkDAO() {
	}

	static NetworkDAO getSingleInstance() {
		return INSTANCE;
	}


	UserVO logon(String baseURL, String username, String password, Context context)
			throws DemoServiceException {
		UserVO loggedInUser = UserVO.getSingleInstance();
		loggedInUser.setLogOnSuccess(false);
		
		try {
			if(username == null || password == null){
				return loggedInUser;
			}
			

			URL url = new URL(ServiceLocator.getSingleInstance()
					.getUserInfoURL());	
			username = Utility.DemoURLEncoder(username);
			
			password = Utility.DemoURLEncoder(password);
			
			StringEntity strEntity = new StringEntity("Header to attached");
			strEntity.setContentType(DemoConstants.URL_ENCODING_TYPE);
			
			InputStream inputStream;
			inputStream = networkActivator.invoke(url.toString(), DemoConstants.POST, strEntity);
			
			if(inputStream != null){
				JSONObject responseJSONObj = streamToJSON(inputStream);
				if(responseJSONObj != null){
					if(responseJSONObj.has(DemoConstants.ERROR) || !responseJSONObj.has(DemoConstants.ACCESS_TOKEN)){						
						
						if(responseJSONObj.has(DemoConstants.DEMO_ERROR) && responseJSONObj.getString(DemoConstants.DEMO_ERROR).equals(DemoConstants.INVALID_SITE_KEY))
						{
							loggedInUser.setLoginErrorMsg(responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY).replace("\n ", "\n"));
							try {
								//TODO re factor if possible
								int currentSiteLocation = responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY).indexOf("-")+1;
								String currentSite = responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY).substring(currentSiteLocation, currentSiteLocation+2).trim();
								loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_invalid_account_title,currentSite));
							} catch (Exception e) {
								loggedInUser.setLoginErrorTitle("Invalid Account");
							}
						}
						else if(responseJSONObj.has(DemoConstants.DEMO_ERROR) && responseJSONObj.getString(DemoConstants.DEMO_ERROR).equals(DemoConstants.USER_DISABLED))
						{
							loggedInUser.setLoginErrorMsg(responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY));
							loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_disabled_user_title, loggedInUser.getSite()));
						}
						else if(responseJSONObj.has(DemoConstants.DEMO_ERROR) && responseJSONObj.getString(DemoConstants.DEMO_ERROR).equals(DemoConstants.USER_LOCKED))
						{
							loggedInUser.setLoginErrorMsg(responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY));
							loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_locked_user_title, loggedInUser.getSite()));
						}
						else if(responseJSONObj.has(DemoConstants.DEMO_ERROR) && responseJSONObj.getString(DemoConstants.DEMO_ERROR).equals(DemoConstants.INVALID_PASSWORD_COMPLIANCE))
						{
							loggedInUser.setLoginErrorMsg(DemoConstants.INVALID_PASSWORD_COMPLIANCE);
							loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_invalid_logon_title));
							if(responseJSONObj.has(DemoConstants.PASSWORD_VALID_MESSAGE))
								loggedInUser.setPasswordRules(responseJSONObj.getString(DemoConstants.PASSWORD_VALID_MESSAGE));
							if(responseJSONObj.has(DemoConstants.PASSWORD_REGEX_KEY))
								loggedInUser.setPasswordRulesRegex(responseJSONObj.getString(DemoConstants.PASSWORD_REGEX_KEY));	
						}
						else if(responseJSONObj.has(DemoConstants.DEMO_ERROR) && responseJSONObj.getString(DemoConstants.DEMO_ERROR).equals(DemoConstants.USER_NOT_VERIFIED))
						{
							if(responseJSONObj.has(DemoConstants.ERROR_MESSAGE_KEY))
								loggedInUser.setEmailVerificatioErrorMessage(responseJSONObj.getString(DemoConstants.ERROR_MESSAGE_KEY));
							loggedInUser.setLoginErrorMsg(DemoConstants.USER_NOT_VERIFIED);
							loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_user_not_verified_title));
						}
						else
						{
							loggedInUser.setLoginErrorMsg(context.getString(R.string.demo_invalid_logon_msg));
							loggedInUser.setLoginErrorTitle(context.getString(R.string.demo_invalid_logon_title));
						}
						
						loggedInUser.setLogOnSuccess(false);
						return loggedInUser;
					}else{
						loggedInUser  = voAssembler.setUserVo(responseJSONObj);
					}

				}
			}
		} catch (Exception e) {
			return loggedInUser;
		}	
		
		loggedInUser.setLogOnSuccess(true);

		return loggedInUser;
	}
	
	private JSONObject streamToJSON(InputStream inStream) throws IOException, JSONException{
		if(inStream != null){
			InputStreamReader inputReader = new InputStreamReader(inStream);
			BufferedReader reader = new BufferedReader(inputReader);
	
			String responseStr = reader.readLine().toString();
			if(responseStr != null){
				JSONObject JSONObj = new JSONObject(responseStr);
				return JSONObj;
			}
		}
		return null;
	}
	
	
	EncryptionVO getEncryptionKey(String baseURL,
			String EncryptedType) throws Exception, DemoServiceException {
		String EncryptedURL = ServiceLocator.getSingleInstance()
				.getEncryptedURL(baseURL);
		InputStream inputStream = networkActivator.invoke(EncryptedURL, "get");
		return voAssembler.getEncryptionKeyVO(inputStream);

	}
	
	
}
