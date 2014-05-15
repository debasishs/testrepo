package demo.project.dao;

import android.content.Context;
import demo.project.service.client.DemoServiceException;
import demo.project.vo.EncryptionVO;
import demo.project.vo.UserVO;

public class DemoDAO {
	private static final DemoDAO INSTANCE = new DemoDAO();

	private DemoDAO() {
	}

	public static DemoDAO getSingleInstance() {
		return INSTANCE;
	}


	
	public UserVO logon(String baseURL, String username, String password, Context context)
			throws DemoServiceException {
		return NetworkDAO.getSingleInstance()
				.logon(baseURL, username, password, context);
	}

	
	// Method to save application Info
		public void saveApplicationPreferences(String encryptionKey)
				throws DemoServiceException {

			LocalstorageDAO.getSingleInstance().saveApplicationPreferences(encryptionKey);
		}
	
		public EncryptionVO getEncrytionKey(String baseURL , String encryptionKey) throws DemoServiceException, Exception {
			return NetworkDAO.getSingleInstance().getEncryptionKey(baseURL , encryptionKey);
		}
		
		
}
