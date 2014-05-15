package demo.project.service.client;


public class ServiceLocator {
	private RequestBuilder requestBuilder = new RequestBuilder();
	private static final ServiceLocator INSTANCE = new ServiceLocator();

	public static ServiceLocator getSingleInstance() {
		return INSTANCE;
	}
	
	public String getUserInfoURL() {
		return requestBuilder.LogInURL();
	}
	
	public String getEncryptedURL(String baseURL) {
		return requestBuilder.getEncryptionKeyRequest(baseURL);
	}

}
