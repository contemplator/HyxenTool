package downloads;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppleDownload {
	static String url = "https://reportingitc2.apple.com/api/downloadCannedReport?vendorID=85097056&reportType=2A&endDate=2014%2F07%2F25&vendorType=1";
	
	public static void main (String [] argv) throws IOException, URISyntaxException, SQLException{
		
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
//			System.out.println(cal.getTime().toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String download_date = format.format(cal.getTime()).toString();
			download_date = download_date.substring(0,4) + "%2F" + download_date.substring(4, 6) + "%2F" + download_date.substring(6, 8);
			
			String url2;
			url2 = url.replace("2014%2F07%2F25", download_date);
			openBrowser(url2);
			System.out.println("Download date: " + download_date);
			
			cal.add(Calendar.DATE, 1);
		}
	}

	private static void openBrowser(String url) throws IOException, URISyntaxException {
		if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
            System.out.println("download: " + url);
        }else{
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("xdg-open " + url);
        }
	}
	
	private static String getStartDate() throws SQLException {
		AppleMysql db = new AppleMysql();
		System.out.println("connected!");
		String lastDate = db.getLastDate();
		return lastDate;
	}
}
