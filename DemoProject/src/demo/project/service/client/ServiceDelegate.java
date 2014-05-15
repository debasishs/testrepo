package demo.project.service.client;

import android.content.Context;
import demo.project.dao.DemoDAO;
import demo.project.vo.EncryptionVO;
import demo.project.vo.UserVO;

public class ServiceDelegate {
	private static final ServiceDelegate INSTANCE = new ServiceDelegate();
	// Private constructor prevents instantiation from other classes
	private ServiceDelegate() {
	}

	public static ServiceDelegate getSingleInstance() {
		return INSTANCE;
	}



	public UserVO login(String username, String password, Context context)
			throws DemoServiceException {
		return DemoDAO.getSingleInstance().logon(ServiceConstants.BaseUrl,
				username, password, context);
	}

	
	public void saveApplicationPreferences(String encryptionKey)
			throws DemoServiceException {

		DemoDAO.getSingleInstance().saveApplicationPreferences(encryptionKey);
	}
	

	public EncryptionVO getEncryptionKey(String encryptionKey) throws DemoServiceException, Exception {
		return DemoDAO.getSingleInstance()
				.getEncrytionKey(ServiceConstants.BaseUrl , encryptionKey);
	}
	
}
