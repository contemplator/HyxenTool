package downloads;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class GoogleImport {
	static String[] filelist = {
		"com.hyxen.app.AtomicEnergy_overall_installs.csv", 
		"com.hyxen.app.Barcode_overall_installs.csv",
		"com.hyxen.app.bikechallenger_overall_installs.csv",
		"com.hyxen.app.DrivingLogger_overall_installs.csv",
		"com.hyxen.app.GeoMe_overall_installs.csv",
		"com.hyxen.app.icare_overall_installs.csv",
		"com.hyxen.app.krt_overall_installs.csv",
		"com.hyxen.app.love_overall_installs.csv",
		"com.hyxen.app.MobileLocusMap_overall_installs.csv",
		"com.hyxen.app.ProMeApp_overall_installs.csv",
		"com.hyxen.app.RailTimeline_overall_installs.csv",
		"com.hyxen.app.SpeedDetectorEvo_overall_installs.csv",
		"com.hyxen.app.Taiwan368_overall_installs.csv",
		"com.hyxen.app.WifiMap_overall_installs.csv",
		"com.hyxen.sled.iset.HotSpring_overall_installs.csv",
		"com.hyxen.sled.iset.Women30_overall_installs.csv",
		"com.hyxen.taximeter.app_overall_installs.csv"
	};
	
	static String[] applist = {
		"AtomicEnergy", 
		"Barcode",
		"Bikechallenger",
		"DrivingLogger",
		"GeoMe",
		"iCare",
		"Krt",
		"Love",
		"MobileLocusMap",
		"ProMeApp",
		"RailTimeline",
		"SpeedDetectorEvo",
		"Taiwan368",
		"WifiMap",
		"HotSpring",
		"Women30",
		"Taximeter"
	};
	
	public static void main (String [] argv){
		MysqlTest db_download = new MysqlTest();
		System.out.println("connected!");
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		HashMap<String, ArrayList<ArrayList<String>>> hm = new HashMap<String, ArrayList<ArrayList<String>>>();
		String originPath = "C:\\Users\\Leo\\Downloads\\";
		for(int i=0; i<filelist.length; i++){
			data = readCSV(originPath + filelist[i]);
			hm.put(applist[i], data);
		}
		
		String date = null;
		for( String key : hm.keySet()){
			data = hm.get(key);
			for(int i=data.size()-1; i>=0; i--){
				ArrayList<String> row = data.get(i);
				date = row.get(0);
				date = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6,8);
				db_download.insertTable(key, date, Integer.parseInt(row.get(1)), Integer.parseInt(row.get(2)), Integer.parseInt(row.get(3)));
			}
		}
		System.out.print("done!");
	}
	
	public static ArrayList<ArrayList<String>> readCSV(String filepath){
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		BufferedReader br;
		
		try{
			br = new BufferedReader(new FileReader(filepath));
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			br.readLine();
			String line = null;
			
			while((line = br.readLine()) != null){
				ArrayList<String> row = new ArrayList<String>();
				String item[] = line.split(",");
				for(int i=0; i<item.length; i++){
					row.add(item[i]);
				}
				data.add(row);
			}
			br.close();
			
		} catch(IOException e){
			e.printStackTrace();
		}
		return data;
	}
}
