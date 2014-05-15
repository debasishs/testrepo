package demo.project.dao;

import android.content.Context;
import demo.project.service.client.DemoServiceException;
import demo.project.util.DemoPreferences;
import demo.project.vo.ApplicationVO;

public class LocalstorageDAO {
	private static LocalstorageDAO INSTANCE = new LocalstorageDAO();
	DemoPreferences preferences = DemoPreferences.getInstance();
	public ApplicationVO applicationVO = ApplicationVO.getSingleInstance();

	private LocalstorageDAO(){
		
	}
	
	public static LocalstorageDAO getSingleInstance(){
		return INSTANCE;
	}
	
	
	public ApplicationVO getApplicationInfo(Context context)throws DemoServiceException {

		preferences.setContext(context);
		applicationVO = preferences.getApplPreferences();
		return applicationVO;
	}
	
	public void saveApplicationPreferences(String encryptionKey)throws DemoServiceException {
		
    	applicationVO.setEncryptionKey(encryptionKey);
    	
    	preferences.saveApplicationPreferences();
		
	}
	

	
}
