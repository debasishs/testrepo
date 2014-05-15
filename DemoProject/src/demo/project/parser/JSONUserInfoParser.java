package demo.project.parser;

import org.json.JSONObject;

import android.util.Log;
import demo.project.service.client.DemoServiceException;
import demo.project.service.client.ServiceDelegate;
import demo.project.util.DemoConstants;
import demo.project.vo.UserVO;

public class JSONUserInfoParser extends JSONParserBase {

	private String 	encryptionKey = DemoConstants.EMPTY_STRING;

	public JSONUserInfoParser(JSONObject jobj) {
		super(jobj);
	}

	@Override
	public UserVO parse() throws Exception {
		UserVO user = UserVO.getSingleInstance();
		if (jObj != null) {
			Log.i("CDW", jObj.toString());
			if (!jObj.isNull(DemoParserConstants.EMAIL))
				user.setEmail(jObj.getString(DemoParserConstants.EMAIL));

			if (jObj.has(DemoParserConstants.IS_USER_LOCKEDOUT)) {
				user.setLockedUser(jObj
						.getBoolean(DemoParserConstants.IS_USER_LOCKEDOUT));
			}

			if (!jObj.isNull(DemoParserConstants.FULLNAME))
				user.setFullName(jObj.getString(DemoParserConstants.FULLNAME));

			if (!jObj.isNull(DemoParserConstants.PHONE))
				user.setPhone(jObj.getString(DemoParserConstants.PHONE));

			if (!jObj.isNull(DemoParserConstants.PHONEEXT))
				user.setPhoneExt(jObj.getString(DemoParserConstants.PHONEEXT));

			if (!jObj.isNull(DemoParserConstants.SITE))
				user.setSite(jObj.getString(DemoParserConstants.SITE));

			if (!jObj.isNull(DemoParserConstants.ISPASUSER))
				user.setIsPasUser(jObj
						.getBoolean(DemoParserConstants.ISPASUSER));

			
			if (!jObj.isNull(DemoParserConstants.encryptionKey))
				encryptionKey = jObj.getString(DemoParserConstants.encryptionKey);

			try {
				ServiceDelegate.getSingleInstance().saveApplicationPreferences(encryptionKey);
			} catch (DemoServiceException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

}
