package demo.project.ui.command;


import demo.project.service.client.DemoServiceException;
import demo.project.service.client.ServiceDelegate;
import demo.project.util.DemoCommand;
import demo.project.util.DemoConstants;
import demo.project.vo.EncryptionVO;

public class EncryptionKeyCommand implements DemoCommand{
	
	private EncryptionVO encryptionVO;

	@Override
	public Object command(Object... params) {
		
		try {
			encryptionVO = ServiceDelegate.getSingleInstance().getEncryptionKey(params[0].toString());
		} catch (DemoServiceException e) {
			if(!e.getMessage().equals(DemoConstants.NETWORKERROR))
			{
				e.setCustomErrorMessage(DemoConstants.USER_NOT_VERIFIED);
			}
				return e;
				//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptionVO;
	}

}
