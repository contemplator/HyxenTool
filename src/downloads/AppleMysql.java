package downloads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Leo
 * I ignore creating table and dropping table function
 * connect the db named hyxen
 */
public class AppleMysql {
	private Connection con = null; 
	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	private String insertSql = "insert into i_downloads(id, appname, date, daily_device_installs, total_install) " + 
			"select ifNULL(max(id),0)+1,?,?,?,? FROM i_downloads";
	private String queryLastSql = "SELECT * FROM `i_downloads` WHERE 1 ORDER BY date DESC limit 0,1;";
	
	public AppleMysql(){
		try{
  			Class.forName("com.mysql.jdbc.Driver"); // register Driver
  			con = DriverManager.getConnection(
  					"jdbc:mysql://localhost/hyxen?useUnicode=true&characterEncoding=utf8",
  					"root",
  					"fourteen");
  		} catch(ClassNotFoundException e){
  			System.out.println("DriverClassNotFound :"+e.toString());
  		} catch(SQLException x){
  			System.out.println("Exception :"+x.toString());
  		}
	}
	
	public void insertTable(String appname, String date, int daily_install){
		try {
  			stat = con.createStatement();
  			rs = stat.executeQuery("SELECT max(total_install) FROM i_downloads WHERE appname = '" + appname + "';");
  			int total_install = 0;
  			if(rs.next()) {
  				total_install = rs.getInt("max(total_install)") + daily_install;
  			}else{
  				total_install = 0;
  			}
  			pst = con.prepareStatement(insertSql);
  			pst.setString(1, appname); 
  			pst.setString(2, date);
  			pst.setInt(3, daily_install);
  			pst.setInt(4, total_install);
  			pst.executeUpdate();
  		} catch(SQLException e) { 
  			System.out.println("InsertDB Exception :" + e.toString()); 
  		} finally { 
  			Close(); 
  		}
	}
	
	public String getLastDate(){
    	String date = "";
    	try { 
    		stat = con.createStatement(); 
    		rs = stat.executeQuery(queryLastSql);
    		while(rs.next()) {
				date = rs.getString("date");
    			System.out.println("The last date of data is on " + rs.getString("date"));
    		}
    	} catch(SQLException e) { 
    		System.out.println("DropDB Exception :" + e.toString()); 
    	} finally { 
    		Close(); 
    	}
		return date;
    }
	
	private void Close() { 
    	try {
    		if(rs!=null) { 
    			rs.close(); 
    			rs = null; 
    		} 
    		if(stat!=null) { 
    			stat.close(); 
    			stat = null; 
    		} 
    		if(pst!=null) { 
    			pst.close(); 
    			pst = null; 
    		} 
    	} catch(SQLException e) { 
    		System.out.println("Close Exception :" + e.toString()); 
    	} 
    }
}
