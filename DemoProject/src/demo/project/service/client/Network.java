package demo.project.service.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import demo.project.dao.DemoHttpClient;
import demo.project.util.DEMOAsyncTask;
import demo.project.util.DemoConstants;

public class Network {
	public static boolean hasTokenExpirationErrorOccured = false;

	/**
	 * A method used to fetch the data from server.
	 * 
	 * @param url
	 *            specific URL
	 * @param type
	 *            Post or Get
	 * @return the data to streams
	 * @throws DemoServiceException
	 */
	public synchronized InputStream invoke(final String url, String type) throws Exception, DemoServiceException {
		hasTokenExpirationErrorOccured = false;
		return doService(url,type, null);
	}

	public synchronized InputStream invoke(final String url, String type, HttpEntity entity) throws Exception, DemoServiceException {
		hasTokenExpirationErrorOccured = false;
		return doService(url,type, entity);
	}

	private InputStream doService(String url, String type, HttpEntity entity) throws Exception, DemoServiceException {
		if (type.equals(DemoConstants.GET)) {
			return doGet(url);
		} else {
			return doPost(url, entity);
		}
	}

	private InputStream doGet(String url) throws Exception, DemoServiceException {
		HttpResponse response = null;	
		HttpClient httpClient = null;
		HttpGet request = null;

		try {
			// Instantiate the custom HttpClient
			httpClient = new DemoHttpClient(DemoApplication.applicationContext);
			request = new HttpGet(url);
			signRequest(request);
		} catch (Exception e) {
			throw new DemoServiceException(DemoConstants.XML_RESPONSE_DUE_TO_ERROR);
		}

		try
		{	
			response = httpClient.execute(request);
		}
		catch(Exception e)
		{
			throw new DemoServiceException(DemoConstants.NETWORKERROR);

		}

		return obtainResponseContent(response);	
	}

	private InputStream doPost(String url, HttpEntity entity) throws Exception, DemoServiceException {
		HttpResponse response = null;
		HttpClient httpClient = null;
		HttpPost request = null;

		try {
			// Instantiate the custom HttpClient
			httpClient = new DemoHttpClient(DemoApplication.applicationContext);
			request = new HttpPost(url);
			if(entity != null){
				request.setEntity(entity);
			}
			//request.setParams();
			signRequest(request);
		} catch (Exception e) {
			throw new DemoServiceException(DemoConstants.XML_RESPONSE_DUE_TO_ERROR);
		}

		try
		{
			response = httpClient.execute(request);
		}
		catch(Exception e)
		{
			throw new DemoServiceException(DemoConstants.NETWORKERROR);	
		}

		return obtainResponseContent(response);
	}

	private InputStream obtainResponseContent(HttpResponse response) throws DemoServiceException, IllegalStateException, IOException{
		if (response.getStatusLine().getStatusCode() > 300) {
			checkTokenExpiredError(response);
			Header content_type = response.getEntity().getContentType();
			HeaderElement[] typeNameElements = content_type.getElements();
			for(HeaderElement element : typeNameElements){
				String elementName = element.getName();
				if(elementName.equals(DemoConstants.IMAGE_RESPONSE_CONTENT_TYPE)){
					//TODO: Revert back to some class if any
					throw new DemoServiceException(DemoConstants.XML_RESPONSE_DUE_TO_ERROR);
				}
			}
			if(content_type != null && content_type.getValue().contains(DemoConstants.JSON_CONTENT_TYPE)){
				return response.getEntity().getContent();
			}
			throw new DemoServiceException(DemoConstants.XML_RESPONSE_DUE_TO_ERROR);
		} else {
			Header entityTypeHeader = response.getEntity().getContentType();
			HeaderElement[] typeNameElements = entityTypeHeader.getElements();
			for(HeaderElement element : typeNameElements){
				String elementName = element.getName();
				if(elementName.equals(DemoConstants.IMAGE_RESPONSE_CONTENT_TYPE)){
					//TODO: Revert back to some class if any
					throw new DemoServiceException(DemoConstants.XML_RESPONSE_DUE_TO_ERROR);
				}
			}
			return response.getEntity().getContent();
		}
	}

	private void signRequest(HttpRequestBase request){
		//TODO: If any special sign request (Oauth)		
	}


	private void checkTokenExpiredError(HttpResponse response){
		Header[] responseHeaders = response.getAllHeaders();
		for (Header header : responseHeaders) {
			if (header.getName().equals("WWW-Authenticate")) {
				Header authenticationHeader = header;
				HeaderElement[] elements = authenticationHeader.getElements();

				for (HeaderElement element : elements) {
					if (element.getName().equals("oauth_problem")) {
						String strAdvice = element.getValue();
						if (strAdvice.equals("token_expired")) {
							hasTokenExpirationErrorOccured = true;
							//TODO: Revert back to some class if any 
							DEMOAsyncTask.isDEMOAsyncTaskRunning = false;
						}
					}
				}
			}
		}
	}
}
