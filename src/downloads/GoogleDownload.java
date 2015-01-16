package downloads;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class GoogleDownload {
	static String[] filepath = {
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
	
	static String[] appname = {
			"AtomicEnergy",
			"Barcode",
			"bikechallenger",
			"DrivingLogger",
			"GeoMe",
			"icare",
			"krt",
			"love",
			"MobileLocusMap",
			"ProMeApp",
			"SpeedDetectorEvo",
			"Taiwan368",
			"WifiMap",
			"HotSpring",
			"Women30",
			"taximeter",
			"RailTimeline"
	};
	
	public static void main (String [] argv) throws InterruptedException, IOException, URISyntaxException, SQLException{
		checkFileExist();
		String lastDate = getStartDate();
		HashMap<String, String> appsLink = setLinks();
		String date = lastDate.substring(8, 10);
		date = Integer.toString((Integer.parseInt(date)) +1);
		if(date.length()<2){
			date = "0"+date;
		}
		String startdate = lastDate.substring(0, 4) + lastDate.substring(5, 7) + date;
		
		String enddate = null;
		enddate = getDateTime();
		for( Object key : appsLink.keySet()){
			String link = appsLink.get(key).toString();
			if(link.contains("20100101") && link.contains("20100102")){
				String s1 = link.replace("20100101", startdate);
				String s2 = s1.replace("20100102", enddate);
				
				if(Desktop.isDesktopSupported()){
		            Desktop desktop = Desktop.getDesktop();
		            desktop.browse(new URI(s2));
		            System.out.println("download: " + s2);
		        }else{
		            Runtime runtime = Runtime.getRuntime();
		            runtime.exec("xdg-open " + s2);
		        }
			}else{
				System.out.println("error: not contains 20100101 & 20100102");
			}
		}
		
	}

	private static void checkFileExist() {
		for(int i=0; i<filepath.length; i++){
			File file = new File("C:\\Users\\Leo\\Downloads\\" + filepath[i]);
			if(file.exists()){
				file.delete();
				System.out.println("delete:" + filepath[i]);
			}else{
				System.out.println("C:\\Users\\Leo\\Downloads" + filepath[i] + " does not exist.");
				continue;
			}
		}
	}

	private static HashMap<String, String> setLinks() {
		HashMap<String, String> appsLink = new HashMap<String, String>();
		appsLink.put("AtomicEnergy", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.AtomicEnergy&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Barcode", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.Barcode&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("bikechallenger", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.bikechallenger&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("DrivingLogger", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.DrivingLogger&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("GeoMe", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.GeoMe&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("icare", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.icare&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("krt", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.krt&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("love", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.love&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("MobileLocusMap", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.MobileLocusMap&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("ProMeApp", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.ProMeApp&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("SpeedDetectorEvo", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.SpeedDetectorEvo&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Taiwan368", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.Taiwan368&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("WifiMap", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.WifiMap&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("HotSpring", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.sled.iset.HotSpring&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("Women30", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.sled.iset.Women30&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("taximeter", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.taximeter.app&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		appsLink.put("RailTimeline", "https://play.google.com/apps/publish/statistics/download?package=com.hyxen.app.RailTimeline&sd=20100101&ed=20100102&dim=overall&met=current_device_installs,daily_device_installs,daily_device_uninstalls&asv2=true&dev_acc=05129407701782775346");
		
		return appsLink;
	}
	
	public static String getDateTime(){
		
		String strDate = JOptionPane.showInputDialog("請輸入Google Play 後台可查詢的最新一日：", "可至 http://goo.gl/V0mjru 查詢, 格式：20140101");
		System.out.println(strDate);
		return strDate;
	}

	private static String getStartDate() throws SQLException {
		MysqlTest db = new MysqlTest();
		System.out.println("connected!");
		String lastData = db.getLastData();
		return lastData;
	}
	
	private static String addZero(String singleDate){
		String doubleDate = "";
		if(Integer.parseInt(singleDate) < 10){
			doubleDate = 0 + singleDate;
		}else{
			doubleDate = singleDate;
		}
		return doubleDate;
	}
}
