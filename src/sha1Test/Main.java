package sha1Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
	public static void main(String[] args){
		String str = "100306082";
	    MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes("UTF-8"));
		    byte[] result = md.digest();
		 
		    StringBuffer sb = new StringBuffer();
		 
		    for (byte b : result) {
		        int i = b & 0xff;
		        if (i < 0xf) {
		            sb.append(0);
		        }
		        sb.append(Integer.toHexString(i));
		    }
		    System.out.println(sb.toString().toLowerCase());
		    System.out.println("7a86a39697dccf4785be2162eea8d4e55c422e95");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
