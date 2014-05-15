package demo.project.service.client;


class RequestBuilder {

	String getMobileAppDataRequest(String baseURL, String userName,
			String password) {

		return baseURL;
	}

	public String LogInURL() {
		return ServiceConstants.BaseUrl
				+ "Authentication/DemoAuthenticationRestService.svc/user/json";

	}
	
	String getEncryptionKeyRequest(String baseURL) {
			return baseURL + "cart/EncryptionKey.svc/savedcarts/json";
			
	}

}
