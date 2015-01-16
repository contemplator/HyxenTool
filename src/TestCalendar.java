import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TestCalendar {
	public static void main (String [] argv){
		Date dd = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dd);
		for(int i=0; i>-1460; i--){
			cal.add(Calendar.DATE, -1);
            
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String newDate = format.format(cal.getTime()).toString();
			System.out.println(newDate);
		}
	}
}
