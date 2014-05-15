package demo.project.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import android.annotation.SuppressLint;
import android.content.Context;
import demo.project.vo.ApplicationVO;

@SuppressLint("TrulyRandom")
public class AESEncryptor {
	
	private static final String ALGO = "AES";
	private static final byte[] keyValue = 
	        new byte[] { 'C', 'D', 'W', 'H', 'E', 'X', '4', '3', '4', '4', '5', '7', '0', 'K', 'e', 'y'};
	private static final int NO_OF_ITERATIONS = 3;
	private static final String SALT = "6514897843612325";
	
	public static final int SALT_LENGTH = 8;
	public static final int IV_LENGTH = 16;
	public static final int PBE_ITERATION_COUNT = 10000;
	
    private static final String PBE_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    
    public AESEncryptor() {
    	PRNGFixes.apply();
	}
    
	public String encryptString(String plainText) {

		try {
			DemoPreferences preferences = DemoPreferences.getInstance();
			ApplicationVO applicationVO = preferences.getApplPreferences();

			String ek = new StringBuffer(applicationVO.getEncryptionKey()).toString();

			byte[] keyBytes = ek.getBytes("UTF-8");
			byte[] keyBytes16 = new byte[16];
			System.arraycopy(keyBytes, 0, keyBytes16, 0,
					Math.min(keyBytes.length, 16));

			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes16, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));

			byte[] useridBytes = plainText.getBytes("UTF-8");

			// encrypt
			useridBytes = cipher.doFinal(useridBytes);
			return new String(Base64.encodeBase64(useridBytes));
		} catch (InvalidKeyException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			
			e.printStackTrace();
		} catch (BadPaddingException e) {
			
			e.printStackTrace();
		}

		return null;
	}
	
	@SuppressLint("TrulyRandom")
	public static String encryptForLocalStorage(String sSource, Context ctx)
	{
		
		if(sSource.equalsIgnoreCase(""))
			return "";
		
		Key key;
		Cipher c;
		String eVal = sSource;
		

		try
		{
			key = getKey();
			c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			
			//Add SALT and encode;
			String finalVal = null;
	        for (int nCtr = 0; nCtr < NO_OF_ITERATIONS; nCtr++)
	        {
	        	finalVal = SALT + eVal;
	            byte[] encBytes;
				encBytes = c.doFinal(finalVal.getBytes());
	            eVal = new String(Base64.encodeBase64(encBytes));
	        }

		} 
		catch (IllegalBlockSizeException e)
		{
			return sSource;
		} 
		catch (BadPaddingException e)
		{
			return sSource;
		}
		catch (NoSuchAlgorithmException e)
		{
			return sSource;
		} 
		catch (NoSuchPaddingException e)
		{
			return sSource;
		} 
		catch (InvalidKeyException e)
		{
			return sSource;
		}

		
		return eVal;
	}
	
	public static String decryptForLocalStorage(String sSource, Context ctx)
	{
		
		if(sSource.equalsIgnoreCase(""))
			return "";
		
		Key key;
		Cipher c;
		String dVal = sSource;

		try
		{
			key = getKey();
			c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			
			//First decode, then remove SALT;
	        for (int nCtr = 0; nCtr < NO_OF_ITERATIONS; nCtr++)
	        {
	        	byte[] decBytes = Base64.decodeBase64(dVal.getBytes());	        	
	            byte[] decVal = c.doFinal(decBytes);	            
	            dVal = new String(decVal).substring(SALT.length());
	        }

		} 
		catch (IllegalBlockSizeException e)
		{
			return sSource;
		} 
		catch (BadPaddingException e)
		{
			return sSource;
		}
		catch (NoSuchAlgorithmException e)
		{
			return sSource;
		} 
		catch (NoSuchPaddingException e)
		{
			return sSource;
		} 
		catch (InvalidKeyException e)
		{
			return sSource;
		}

		
		return dVal;
	}
	
	
	private static Key getKey()
	{
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
	public String getEncryptedString(String plainText, String passphrase){
		byte[] salt;
		byte[] ivspec;
		byte[] encryptedBytes;
		try {
			
			salt = generateSalt();
			SecretKey secret = getSecretKey(passphrase, salt);
			
			ivspec = generateIV();
			
			Cipher encryptionCipher = Cipher.getInstance(CIPHER_ALGORITHM);
			encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivspec));
			encryptedBytes = plainText.getBytes("UTF-8");
			encryptedBytes = encryptionCipher.doFinal(encryptedBytes);
		} 
		catch (Exception e) {
			return null;
		}
		
		byte[] version = {0};
		
		byte[] tempArray = concatArray(version, salt);
		byte[] newTempArray = concatArray(tempArray, ivspec);
		byte[] finalByteArray = concatArray(newTempArray, encryptedBytes);
		
		return new String(Base64.encodeBase64(finalByteArray));
	}
	
	public byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
	}
	
	public SecretKey getSecretKey(String passphrase, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(passphrase.toCharArray(), salt, PBE_ITERATION_COUNT, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        SecretKey tmp = factory.generateSecret(pbeKeySpec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_ALGORITHM);
        return secret;
       
	}
	
	private byte[] generateIV() {
		SecureRandom random = new SecureRandom();;
		byte[] iv = new byte[IV_LENGTH];
		random.nextBytes(iv);
		return iv;
	}
	
	private byte[] concatArray(byte[] first, byte[] second){
		int aLen = first.length;
		   int bLen = second.length;
		   byte[] result= new byte[aLen+bLen];
		   System.arraycopy(first, 0, result, 0, aLen);
		   System.arraycopy(second, 0, result, aLen, bLen);
		   return result;
	}
}
