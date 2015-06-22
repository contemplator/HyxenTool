package downloads;

import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlTest {
	private Connection con = null; //Database objects 
	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null; 
	
	private String droptableSQL = "DROP TABLE downloads "; 
	  
	private String createtableSQL = "CREATE TABLE downloads (" + 
    "   id     INTEGER " + 
    "  	, appname    VARCHAR(30) " + 
    "  	, date  DATE)" + 
    "  	, current_device_installs	INTEGER" + 
    "	, daily_device_installs		INTEGER" + 
    "	, daily_device_uninstalls 	INTEGER" +
    "	, total_installs	INTEGER";
	
	private String insertSQL = "insert into downloads(id, appname, date, current_device_installs," +
			"daily_device_installs, daily_device_uninstalls, total_install) " + 
		      "select ifNULL(max(id),0)+1,?,?,?,?,?,? FROM downloads"; 
		  
  	private String selectSQL = "SELECT * FROM `downloads` WHERE 1 ORDER BY date DESC limit 0,1;";
  	
  	public MysqlTest(){
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
  	
  	public void createTable() { 
  		try { 
			stat = con.createStatement(); 
			stat.executeUpdate(createtableSQL); 
  		} catch(SQLException e) { 
  			System.out.println("CreateDB Exception :" + e.toString()); 
  		} finally { 
  			Close(); 
  		} 
    }
  	
  	public void insertTable( String appname,String date, int install, int d_install,
  			int d_uninstall){ 
  		try {
  			stat = con.createStatement();
  			rs = stat.executeQuery("SELECT max(total_install) FROM downloads WHERE appname = '" + appname + "';");
  			int total_install = 0;
  			while(rs.next()) {
  				total_install = rs.getInt("max(total_install)") + d_install;
  			}
  			pst = con.prepareStatement(insertSQL);
  			pst.setString(1, appname); 
  			pst.setString(2, date);
  			pst.setInt(3, install);
  			pst.setInt(4, d_install);
  			pst.setInt(5, d_uninstall);
  			pst.setInt(6, total_install);
  			pst.executeUpdate();
  		} catch(SQLException e) { 
  			System.out.println("InsertDB Exception :" + e.toString()); 
  		} finally { 
  			Close(); 
  		}
    }
  	
    public void dropTable() { 
    	try { 
    		stat = con.createStatement(); 
    		stat.executeUpdate(droptableSQL); 
    	} catch(SQLException e) { 
    		System.out.println("DropDB Exception :" + e.toString()); 
    	} finally { 
    		Close(); 
    	} 
    } 
    
    public ResultSet SelectTable() { 
    	try { 
    		stat = con.createStatement(); 
    		rs = stat.executeQuery(selectSQL);    		 
    		while(rs.next()) {
				System.out.println(rs.getInt("id")+"\t\t"+ rs.getString("name")+"\t\t"+rs.getString("passwd"));
    		}
    	} catch(SQLException e) { 
    		System.out.println("DropDB Exception :" + e.toString()); 
    	} finally { 
    		Close(); 
    	}
		return rs; 
    }
    
    public String getLastData(){
    	String data = "";
    	try { 
    		stat = con.createStatement(); 
    		rs = stat.executeQuery(selectSQL);
    		while(rs.next()) {
				data = rs.getString("date");
    			System.out.println("The last date of data is on " + rs.getString("date"));
    		}
    	} catch(SQLException e) { 
    		System.out.println("DropDB Exception :" + e.toString()); 
    	} finally { 
    		Close(); 
    	}
		return data;
    }
    //完整使用完資料庫後,記得要關閉所有Object 
    //否則在等待Timeout時,可能會有Connection poor的狀況 
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
