package downloads;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlApplication {
	private static Connection con = null; //Database objects 
	private static Statement stat = null;
	private static ResultSet rs = null;
	private static PreparedStatement pst = null; 
	private static String queryLastSql = "SELECT * FROM `buy` WHERE 1";
	
	public static void main (String [] argv) throws IOException, URISyntaxException, SQLException{
		try{
  			Class.forName("com.mysql.jdbc.Driver"); // register Driver
  			con = DriverManager.getConnection(
  					"jdbc:mysql://localhost/johogo?useUnicode=true&characterEncoding=utf8",
  					"root",
  					"nccu6020");
  		} catch(ClassNotFoundException e){
  			System.out.println("DriverClassNotFound :"+e.toString());
  		} catch(SQLException x){
  			System.out.println("Exception :"+x.toString());
  		}
		
		try { 
    		stat = con.createStatement(); 
    		rs = stat.executeQuery(queryLastSql);
    		while(rs.next()) {
				String data = rs.getString("number");
    			System.out.println("comment: " + data);
    		}
    	} catch(SQLException e) { 
    		System.out.println("DropDB Exception :" + e.toString()); 
    	} finally { 
    		Close(); 
    	}
	}
	
	private static void Close() { 
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
