package demo.project.parser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import demo.project.vo.BaseVO;

abstract class JSONParserBase {
	JSONObject jObj;
	JSONArray jAry;

	public JSONParserBase(InputStream is) {
		try {
			jObj = new JSONObject(new String(fetchBytes(is)));
		} catch (Exception ex) {
			jObj = null;
		}
	}

	public JSONParserBase(JSONObject obj) {
		this.jObj = obj;
	}

	public JSONParserBase(InputStream is, String typeOfJson) {
		try {
			jAry = new JSONArray(new String(fetchBytes(is)));

		} catch (Exception ex) {
			jObj = null;
		}
	}

	public abstract BaseVO parse() throws Exception;

	private byte[] fetchBytes(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int count;
		try {
			count = is.read(bytes);
			while (count > 0) {
				baos.write(bytes, 0, count);
				count = is.read(bytes);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			closeInputStream(is);
			is = null;
		}
		return baos.toByteArray();
	}

	private void closeInputStream(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (Exception ex) {
		}
	}
}
