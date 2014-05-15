package demo.project.dao;

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import demo.project.ui.R;
import demo.project.util.DemoConstants;

public class DemoHttpClient extends DefaultHttpClient {

	final Context context;

	public DemoHttpClient(Context context) {

		this.context = context;

	}

	@Override
	protected ClientConnectionManager createClientConnectionManager() {
		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", createAdditionalCertsSSLSocketFactory(), 443));

		// and then however you create your connection manager, I use ThreadSafeClientConnManager
		final HttpParams params = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(params, DemoConstants.TIMEOUT_CONNECTION);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(params,  DemoConstants.SOCKET_TIMEOUT);
		
		return new ThreadSafeClientConnManager(params,schemeRegistry);
	}

	private SSLSocketFactory createAdditionalCertsSSLSocketFactory() {

		try {
	        final KeyStore ks = KeyStore.getInstance("BKS");

	        // the bks file we generated above
	        final InputStream in = context.getResources().openRawResource( R.raw.demokeystore);  
	        try {
	            // don't forget to put the password used above in strings.xml/mystore_password
	            ks.load(in, context.getString( R.string.mystore_password ).toCharArray());
	        } finally {
	            in.close();
	        }

	        // Pass the keystore to the SSLSocketFactory. The factory is responsible
            // for the verification of the server certificate.
            SSLSocketFactory sf = new SSLSocketFactory(ks);

            // Hostname verification from certificate
            // http://hc.apache.org/httpcomponents-client-ga/tutorial/html/connmgmt.html#d4e506
            sf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);

            return sf;
	    } catch( Exception e ) {
	        throw new RuntimeException(e);
	    }
		
	}
}
