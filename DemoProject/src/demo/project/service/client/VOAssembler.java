package demo.project.service.client;

import java.io.InputStream;

import org.json.JSONObject;

import demo.project.parser.JSONEncryptionKeyParser;
import demo.project.parser.JSONUserInfoParser;
import demo.project.vo.EncryptionVO;
import demo.project.vo.UserVO;

public class VOAssembler {
	public VOAssembler() {
	}

	
	public UserVO setUserVo(JSONObject jobj){
		JSONUserInfoParser parser=new JSONUserInfoParser(jobj);
		try {
			return parser.parse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public EncryptionVO getEncryptionKeyVO(InputStream is) throws Exception {

		JSONEncryptionKeyParser encryptionKeyParser = new JSONEncryptionKeyParser(is);

		return (EncryptionVO) encryptionKeyParser.parse();
	}
	

}
