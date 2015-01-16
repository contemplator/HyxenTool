package downloads;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AppleDecompress {
	public static void main (String [] argv) throws IOException, SQLException{
		String zipPath_f = "C:\\Users\\Leo\\Downloads\\S_D_85097056_";
		String zipPath_b = ".txt.gz";
		String desPath = "C:\\Users\\Leo\\Downloads\\itunes";
		
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
			String de_date = format.format(cal.getTime()).toString();
			String zipPath = zipPath_f + de_date + zipPath_b;
			decompress(zipPath, desPath);
			System.out.println("Decopress: " + zipPath);
			
			cal.add(Calendar.DATE, 1);
		}
	}

	private static void decompress(String zipFile, String desPath) throws IOException {
		String fileName = zipFile;
		GZIPInputStream gzi = new GZIPInputStream(new FileInputStream(fileName));
		int to = fileName.lastIndexOf('.');
		String toFileName = fileName.substring(0, to);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toFileName));
		int b;
	    byte[] d = new byte[1024];
	    try {
	    	while ((b = gzi.read(d)) > 0) {
	    		bos.write(d, 0, b);
    		}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	     
	    gzi.close();
	    bos.close();
	     
//	    File file = new File(toFileName);
//	    FileReader fileReader = new FileReader(file);
//	    BufferedReader buffferedReader = new BufferedReader(fileReader);
//
//	    String line = null;
//	    while ((line = buffferedReader.readLine()) != null) {
//	    	System.out.println(line);
//	    }
//	    buffferedReader.close();
//	    fileReader.close();
	}
	
	private static String getStartDate() throws SQLException {
		AppleMysql db = new AppleMysql();
		System.out.println("connected!");
		String lastDate = db.getLastDate();
		return lastDate;
	}
}
