package Atomic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddressSearchLatLng {
	
	static ArrayList<String> addresses = new ArrayList<String>();
	static ArrayList<Double> lats = new ArrayList<Double>();
	static ArrayList<Double> lngs = new ArrayList<Double>();
	
	public static void main(String[] args){
		String filename = "C:\\Users\\Leo\\Desktop\\test.csv";
		readAddress(filename);
//		System.out.println(addresses.get(1));
		for(int i=0; i<addresses.size(); i++){
			System.out.println(addresses.get(i));
			sendGoogleMapRequest(addresses.get(i));
		}
//		writeFile(filename);
	}

	private static void readAddress(String filename) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
			String line = "";
			while((line = br.readLine()) != null){
				int start = line.indexOf("立") + 1;
				line = line.substring(start);
				line = line.replaceAll("民", "");
				line = line.replaceAll("學", "");
				line = line.replaceAll("級", "");
//				System.out.println(line);
				addresses.add(line);
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void sendGoogleMapRequest(String address){
		try {
			String url = "http://maps.googleapis.com/maps/api/geocode/json?address=";
			url += address;
			
			URL mapUrl = new URL(url);
			InputStream in = mapUrl.openStream();
			InputStreamReader r = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(r);
			String result_string = "";
			String line = "";
			while((line = br.readLine()) != null){
				line = line.trim();
				result_string += line;
			}
			JSONObject jo = new JSONObject(result_string);
			JSONArray ja = jo.getJSONArray("results");
			JSONObject geometry = ja.getJSONObject(0).getJSONObject("geometry");
			double lat = geometry.getJSONObject("location").getDouble("lat");
			lats.add(lat);
			double lng = geometry.getJSONObject("location").getDouble("lng");
			lngs.add(lng);
			System.out.println("," + lat + "," + lng);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void writeFile(String filename_pre) {
		int length = addresses.size() - 1;
		String filename = filename_pre.replace(".csv", "(done).csv");
		String result = "";
		for(int i=0; i<length; i++){
			result += addresses.get(i) + ",";
			result += lats.get(i) + ",";
			result += lngs.get(i) + "\n";
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
			out.write(result);
			out.close();
			System.out.println("*** Also wrote this information to file: " + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
