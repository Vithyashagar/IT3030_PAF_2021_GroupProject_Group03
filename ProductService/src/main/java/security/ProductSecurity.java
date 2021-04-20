package security;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProductSecurity {
	
	
	//Converting bytes to hex
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

		public static String bytesToHex(byte[] bytes) {
		  char[] hexChars = new char[bytes.length * 2];
		  int v;
		  for (int j = 0; j < bytes.length; j++) {
		    v = bytes[j] & 0xFF;
		    hexChars[j * 2] = hexArray[v >>> 4];
		    hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		  }
		  return new String(hexChars);
		}
		
		
		// Change this to encryoted form
		private static String SALT = "P@7en79$#8*?";
		
		
		
}
