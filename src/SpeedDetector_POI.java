import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class SpeedDetector_POI {
	
	public static void main(String[] args) throws IOException{
		ArrayList<HashMap<String, String>> data = readCamera();
		for(int i=0; i<data.size(); i++){
//			System.out.println(data.get(i).get("address") + "," + 
//					data.get(i).get("direction") + "," +
//					data.get(i).get("lat") + "," +
//					data.get(i).get("lng") + "," +
//					data.get(i).get("spd") + "," +
//					data.get(i).get("deg"));
			System.out.println(data.get(i).get("address") + "," + data.get(i).get("lat") + "," + data.get(i).get("lng"));
		}
		writefile(data, "crimevideo");
//		callEVOapi(data);
	}
	
	private static void callEVOapi(ArrayList<HashMap<String, String>> data) {
		// ex: http://q.hyxencloud.com/Spd/poi_admin_api/add_poi/{lat}/{lng}/{limit_spd}/{direction}
		String api_url = "http://q.hyxencloud.com/Spd/poi_admin_api/add_poi/";
		for(int i=0; i<data.size(); i++){
			String parameter = data.get(i).get("lat") + "/" + data.get(i).get("lng") + "/" +
					data.get(i).get("spd") + "/" + data.get(i).get("deg");
			String url = api_url+parameter;
			System.out.println(url);
			if(Desktop.isDesktopSupported()){
	            Desktop desktop = Desktop.getDesktop();
	            try {
	                desktop.browse(new URI(url));
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }else{
	            Runtime runtime = Runtime.getRuntime();
	            try {
	                runtime.exec("xdg-open " + url);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
	}

	public static ArrayList<String> readfile(String filename) throws FileNotFoundException{
		ArrayList<String> streets = new ArrayList<>();
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		String input="";
		try {
			while((input = br.readLine()) != null){
				streets.add(input);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return streets;
	}
	
	public static String getLatlng(String street){
		String link = "https://www.google.com.tw/maps/place/" + street + "/";
		String s = "";
		String latlng = "";
		try {
			URL url = new URL(link);
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String input = "";
			int i=0;
			int point=87;
			int breakpoint=0;
			while ((input = in.readLine()) != null) {
				i++;
				if(i==point){
					if(input.contains("latlng:{")){
						int start = input.indexOf("{lat") + 1;
						int end = input.indexOf("}", start);
						s = input.substring(start, end);
						breakpoint = s.indexOf(",");
						latlng = s.substring(4, breakpoint) + "," + s.substring(breakpoint+5);
						
						System.out.print(street + ":");
						System.out.println(latlng);
					}
				}
			}
			in.close();
		} 	catch (MalformedURLException e) {
		} 	catch (IOException e) {
		} finally {
		}
		return latlng;
	}
	
	public static void writefile(ArrayList<HashMap<String, String>> data, String filename) throws IOException{
		FileWriter fw = new FileWriter(filename + "(done).csv");
		for(int i=0; i<data.size(); i++){
			fw.write(data.get(i).get("address") + "," + data.get(i).get("direction") + ",\"" + data.get(i).get("lat") + "," + data.get(i).get("lng") + "\"");
			fw.write("\n");
		}
		System.out.println(filename + "(done).csv");
		System.out.print("done");
		fw.flush();
		fw.close();
	}
	
	public static ArrayList<HashMap<String, String>> readCamera(){
		String link = "http://crimevideo.npa.gov.tw/TrafficStick/TrafficStick.asp?DB_Selt=Selt";
		try{
			URL url = new URL(link);
			URLConnection uc = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputline = "";
			ArrayList<String> addresses = new ArrayList<String>();
			ArrayList<String> direction = new ArrayList<String>();
			ArrayList<String> lat = new ArrayList<String>();
			ArrayList<String> lng = new ArrayList<String>();
			ArrayList<String> spd = new ArrayList<String>();
			ArrayList<String> deg = new ArrayList<String>();
			while((inputline = br.readLine()) != null){
				if(inputline.contains("SetMarkers1")){
					if(inputline.contains("function"))
						continue;
					String[] markers = inputline.split(";");
					for(String marker : markers){
//						System.out.println(marker);
						int left = marker.indexOf("(");
//						int right = marker.indexOf(")");
						String content = marker.substring(left + 1, marker.length() - 1);
//						System.out.println(content);
						
						String[] data = content.split(",");
						if(data[1].contains("國道")){
							if(data[1].contains("南北向")){
								direction.add("南北向");
							}else if(data[1].contains("南向")){
								direction.add("南向");
							}else if(data[1].contains("北向")){
								direction.add("北向");
							}else{
								direction.add("");
							}
							addresses.add(data[1].substring(1, data[1].length() -1));
							lat.add(data[2]);
							lng.add(data[3]);
//							System.out.println(data[1].substring(1, data[1].length() -1) + "," + data[2] + "," + data[3]);
						}else{
//							System.out.println(data[1]);
						}
					}
				}
			}
			ArrayList<HashMap<String, String>> done_data = new ArrayList<HashMap<String,String>>();
//			ArrayList<ArrayList<String>> done_data = new ArrayList<ArrayList<String>>();
//			done_data.add(addresses);
//			done_data.add(direction);
//			done_data.add(lat);
//			done_data.add(lng);
			for(int i=0; i<addresses.size(); i++){
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("address", addresses.get(i));
				hm.put("direction", direction.get(i));
				hm.put("lat", lat.get(i));
				hm.put("lng", lng.get(i));
				if(spd.size()>0 ){
					hm.put("spd", spd.get(i));
				}else{
					hm.put("spd", "100");
				}
				if(deg.size()>0){
					hm.put("deg", deg.get(i));
				}else{
					hm.put("deg", "0");
				}
				done_data.add(hm);
			}
			return done_data;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
}
