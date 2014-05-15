package demo.project.vo;

import demo.project.util.DemoConstants;

public class ApplicationVO extends BaseVO {

	private String encryptionKey = DemoConstants.EMPTY_STRING;

	private static final ApplicationVO INSTANCE = new ApplicationVO();

	private ApplicationVO() {

	}

	public static ApplicationVO getSingleInstance() {
		return INSTANCE;

	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

}