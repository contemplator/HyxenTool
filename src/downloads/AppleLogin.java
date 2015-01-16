package downloads;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class AppleLogin {
	public static void main (String [] argv){
		itunesLogin();
	}

	private static void itunesLogin() {
		new Thread(){
			@Override
			public void run(){
				String url = "https://itunesconnect.apple.com/WebObjects/iTunesConnect.woa/wo/2.0.1.11.3.15.2.1.1.3.1.1";
				String result = "";
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(url);
				try{
					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	            	params.add(new BasicNameValuePair("theAccountName", "contemplation8213@hyxen.com"));
	            	params.add(new BasicNameValuePair("theAccountPW", "Qwer1234"));
	            	params.add(new BasicNameValuePair("1.Continue.x", "0"));
	            	params.add(new BasicNameValuePair("1.Continue.y", "0"));
	            	params.add(new BasicNameValuePair("theAuxValue", ""));
	            	params.add(new BasicNameValuePair("inframe", "0"));
	            	UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
	                post.setEntity(ent);
	                
	                HttpResponse responsePOST = client.execute(post);
	                HttpEntity resEntity = responsePOST.getEntity();
	                result = EntityUtils.toString(resEntity, "UTF-8");
	                System.out.println(result);
				} catch(Exception e){
					System.out.println(e.toString());
				}
			}
		}.start();
	}
}
