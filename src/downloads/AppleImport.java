package downloads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AppleImport {
	static final String[] apps = {"DrivingLogger", "GeoMe", "iCare", "ZeroCard x 熊賺錢 (會員卡管理、賺禮券)",
		"女人30情定水舞間(三立電視)", "有軌時刻表 (火車、高鐵、訂票、轉乘、捷運)", "百貨折扣精選", "命運好好玩(求靈籤)", 
		"核安即時通", "高捷到站查詢", "測速照相器偵測(Speed Detector EVO)", "愛度無限",
		"熱海戀歌(三立電視台20周年旗艦大戲)", "優惠折扣通"};
	static HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	public static void main (String [] argv) throws FileNotFoundException, SQLException, UnsupportedEncodingException {
		AppleMysql mysql = new AppleMysql();
		System.out.println("connected!");
		
		String filename_f = "C:\\Users\\Leo\\Downloads\\S_D_85097056_";
		String filename_b = ".txt";
		
		String start_date = getStartDate();
		System.out.println(start_date);
		int date = Integer.parseInt(start_date.substring(8,10)) + 1;
		int year = Integer.parseInt(start_date.substring(0,4)) - 1900;
		int month = Integer.parseInt(start_date.substring(5,7)) - 1;
		Date dd = new Date(year, month, date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dd);
		Date date2 = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.add(Calendar.DATE, -2);
		
		while(cal.getTime().compareTo(cal2.getTime()) <= 0){
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String file_date = format.format(cal.getTime()).toString();
			String filename = filename_f + file_date + filename_b;
			readFile(filename, file_date);
			
			for(Object key : hm.keySet()){
				String date_new = file_date.substring(0,4) + "-" + file_date.substring(4,6) + "-" + file_date.substring(6,8);
				mysql.insertTable(key.toString(), date_new, hm.get(key));
				System.out.println(key.toString() + "," + date_new + "," + hm.get(key));
			}
			System.out.println(filename + " imported!");
			
			cal.add(Calendar.DATE, 1);
		}
	}

	private static void readFile(String filename, String date) throws FileNotFoundException, UnsupportedEncodingException {
		initHash();
		File file = new File(filename);
	    FileReader fileReader = new FileReader(file);
	    InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
	    BufferedReader buffferedReader = new BufferedReader(isr);
	    String line = null;
	    String[] arr;
	    
	    try {
			while ((line = buffferedReader.readLine()) != null) {
				arr = line.split("	");
				if(arr[6].equals("1") || arr[6].equals("1F")){
					setHashMap(arr[4],arr[7]);
//					System.out.println(arr[4] + "," + arr[6] + "," + arr[7]);
				}
			}
//			for( Object key : hm.keySet()){
//				System.out.println("Date : " + date + "." + key + ":" + hm.get(key).toString());
//				
//			}
			buffferedReader.close();
		    fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setHashMap(String appname, String u) {
		int unit = Integer.parseInt(u);
		int total = 0;
		switch (appname) {
		case "DrivingLogger":
			total = hm.get("DrivingLogger");
			hm.put("DrivingLogger", total + unit);
			break;
		case "GeoMe":
			total = hm.get("GeoMe");
			hm.put("GeoMe", total + unit);
			break;
		case "iCare":
			total = hm.get("iCare");
			hm.put("iCare", total + unit);
			break;
//		case "ZeroCard":
//			total = hm.get("Barcode");
//			hm.put("Barcode", total + unit);
//			break;
		case "女人30情定水舞間(三立電視)":
			total = hm.get("Women30");
			hm.put("Women30", total + unit);
			break;
//		case "有軌時刻表 (火車、高鐵、訂票、轉乘、捷運)":
//			total = hm.get("RailTimeline");
//			hm.put("RailTimeline", total + unit);
//			break;
		case "百貨折扣精選":
			total = hm.get("LoveShopping");
			hm.put("LoveShopping", total + unit);
			break;
		case "命運好好玩(求靈籤)":
			total = hm.get("BegStick");
			hm.put("BegStick", total + unit);
			break;
		case "核安即時通":
			total = hm.get("AtomicEnergy");
			hm.put("AtomicEnergy", total + unit);
			break;
		case "高捷到站查詢":
			total = hm.get("Krt");
			hm.put("Krt", total + unit);
			break;
		case "速度偵測(Speed Detector EVO)":
			total = hm.get("SpeedDetectorEvo");
			hm.put("SpeedDetectorEvo", total + unit);
			break;
		case "愛度無限":
			total = hm.get("Love");
			hm.put("Love", total + unit);
			break;
		case "熱海戀歌(三立電視台20周年旗艦大戲)":
			total = hm.get("HotSpring");
			hm.put("HotSpring", total + unit);
			break;
		case "優惠折扣通":
			total = hm.get("ProMeApp");
			hm.put("ProMeApp", total + unit);
			break;
		default:
			break;
		}
		
		if(appname.contains("有軌時刻表")){
			total = hm.get("RailTimeline");
			hm.put("RailTimeline", total + unit);
		}else if(appname.contains("ZeroCard") && !appname.equals("ZeroCard-HK") && !appname.equals("ZeroCard(会员卡管理中国版)")){
			total = hm.get("Barcode");
			hm.put("Barcode", total + unit);
		}
	}
	
	private static void initHash() {
		hm.put("DrivingLogger", 0);
		hm.put("GeoMe", 0);
		hm.put("iCare", 0);
		hm.put("Barcode", 0);
		hm.put("Women30", 0);
		hm.put("RailTimeline", 0);
		hm.put("LoveShopping", 0);
		hm.put("BegStick", 0);
		hm.put("AtomicEnergy", 0);
		hm.put("Krt", 0);
		hm.put("SpeedDetectorEvo", 0);
		hm.put("Love", 0);
		hm.put("HotSpring", 0);
		hm.put("ProMeApp", 0);
	}
	
	private static String getStartDate() throws SQLException {
		AppleMysql db = new AppleMysql();
		System.out.println("connected!");
		String lastDate = db.getLastDate();
		return lastDate;
	}
}
