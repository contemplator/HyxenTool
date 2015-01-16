package bike;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class UbikeKao {
	public static void main(String[] args){
		readUbike();
	}
	
	private static void readUbike(){
		try {
			URL url = new URL("http://www.c-bike.com.tw/MapStation1.aspx");
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String line = "";
			String lat;
			String lng;
			String name;
			String address;
			String sno; //sid
			String tot;
			String sus;
			String park;
			String mday;
			int start;
			int end;
			JSONObject jo = new JSONObject();
			Collection<JSONObject> stations = new ArrayList<JSONObject>();
			while((line = in.readLine()) != null){
				if(line.contains("{ lat")){
					JSONObject station = new JSONObject();
					
					start = line.indexOf("'", line.indexOf("lat"));
					end = line.indexOf("'", start+1);
					lat = line.substring(start+1, end);
					
					start = line.indexOf("'", line.indexOf("lng"));
					end = line.indexOf("'", start+1);
					lng = line.substring(start+1, end);
					
					start = line.indexOf("'", line.indexOf("name"));
					end = line.indexOf("'", start+1);
					name = line.substring(start+1, end);
					
					start = line.indexOf("'", line.indexOf("name"));
					end = line.indexOf("'", start+1);
					name = line.substring(start+1, end);
					
					start = line.indexOf("地址");
					end = line.indexOf("<br>", start+1);
					address = line.substring(start+3, end);
					
					start = line.indexOf("目前數量");
					end = line.indexOf("剩餘空位", start+1);
					sus = line.substring(start+5, end-1).trim();
					
					start = line.indexOf("剩餘空位");
					end = line.indexOf("'", start+1);
					park = line.substring(start+5, end);
					
					start = line.indexOf("=", line.indexOf("sid"));
					end = line.indexOf("\"", start+1);
					sno = line.substring(start+1, end);
					
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					mday = sdFormat.format(date);
					
//					System.out.println(lat + "," + lng + "," + name + "," + address + "," + sus + "," + park + "," + sno + "," + mday);
					station.put("sno", sno);
					station.put("sna", name);
					station.put("tot", Integer.parseInt(sus) + Integer.parseInt(park));
					station.put("sbi", sus);
					station.put("mday", mday);
					station.put("lat", lat);
					station.put("lng", lng);
					station.put("ar", address);
					stations.add(station);
				}else{
					continue;
				}
				jo.put("retVal", new JSONArray(stations.toString()));
			}
//			JSONArray ja = jo.getJSONArray("retVal");
//			JSONObject test1 = ja.getJSONObject(0);
//			String test1_sno = test1.getString("ar");
			System.out.println(jo.toString());
//			System.out.println(test1_sno);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
