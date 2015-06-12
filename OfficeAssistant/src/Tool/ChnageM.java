package Tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChnageM {
	public static String MMM(String WoW){
		try {
			MessageDigest localMessageDigest;
			localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.reset();
		    localMessageDigest.update(WoW.getBytes());
		    byte[] arrayOfByte = localMessageDigest.digest();
		    StringBuilder localStringBuilder = new StringBuilder();
		    int i = arrayOfByte.length;
		    for (int j = 0; j < i; j++)
	        {
	          String str1 = Integer.toHexString(0xFF & arrayOfByte[j]);
	          if (str1.length() < 2)
	          localStringBuilder.append("0");
	          localStringBuilder.append(str1);
	      }
	      return localStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
