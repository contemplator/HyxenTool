package bike;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

public class UbikeChcg {
	
	public static void main(String[] args){
		readUbike();
	}
	
	private static void readUbike(){
		try {
			URL url = new URL("http://chcg.youbike.com.tw/cht/f12.php?loc=chcg");
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String line = "";
			JSONObject jo = new JSONObject();
			Collection<JSONObject> stations = new ArrayList<JSONObject>();
			while((line = in.readLine()) != null){
				if(line.contains("%7B%")){
					int start = line.indexOf("'") + 1;
					int end = line.indexOf("'", start+1);
					line = line.substring(start, end);
					line = java.net.URLDecoder.decode(line);
					
					
					JSONObject json = new JSONObject(line);
					for(int i=7001; i<7050; i++){
						if(json.has(Integer.toString(i))) {
							JSONObject test = json.getJSONObject(Integer.toString(i));
							stations.add(test);
//							System.out.println(test.toString());
						}
					}
					jo.put("retVal", new JSONArray(stations.toString()));
				}
			}
			System.out.println(jo.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
