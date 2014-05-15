package demo.project.parser;

import java.io.InputStream;

import android.util.Log;
import demo.project.service.client.DemoServiceException;
import demo.project.service.client.ServiceDelegate;
import demo.project.util.DemoConstants;
import demo.project.vo.EncryptionVO;

public class JSONEncryptionKeyParser extends JSONParserBase {

	private String 	encryptionKey = DemoConstants.EMPTY_STRING;

	public JSONEncryptionKeyParser(InputStream is) {
		super(is);
	}

	@Override
	public EncryptionVO parse() throws Exception {
		EncryptionVO Encrypt = EncryptionVO.getSingleInstance();
		if (jObj != null) {
			Log.i("CDW", jObj.toString());
						
			if (!jObj.isNull(DemoParserConstants.encryptionKey))
				encryptionKey = jObj.getString(DemoParserConstants.encryptionKey);
				
			try {
				ServiceDelegate.getSingleInstance().saveApplicationPreferences(encryptionKey);
			} catch (DemoServiceException e) {
				e.printStackTrace();
			}
		}

		return Encrypt;
	}

}
