package demo.project.vo;

import demo.project.util.DemoConstants;

public class EncryptionVO extends BaseVO {

	private String Encrypt_KEY = DemoConstants.EMPTY_STRING;

	private static final EncryptionVO INSTANCE = new EncryptionVO();

	public String getEncryptionKey() {
		return Encrypt_KEY;
	}

	public void setEncryptionKey(String Encrypt_KEY) {
		this.Encrypt_KEY = Encrypt_KEY;
	}

	public static EncryptionVO getSingleInstance() {
		return INSTANCE;
	}

}