package ReadFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class ReadAdComment {
	private static ArrayList<String> data = new ArrayList<String>(); 
	public static void main(String[] args){
		try{
//			JFileChooser chooser = new JFileChooser();
//			chooser.showOpenDialog(null);
//			String filename = chooser.getSelectedFile().getPath();
			String filename = "/Users/idlefox/Desktop/Google comment/有軌時刻表/reviews_com.hyxen.app.RailTimeline_201411.csv";
//			System.out.println(filename);
			String keyword = "廣告";
			checkFile(filename, keyword);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void checkFile(String filename, String keyword) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "unicode"));
			String line = "";
			while((line = br.readLine()) != null){
				if(line.contains(",1,")){
					data.add(line);
					System.out.println(line);
				}else if(line.contains(",2,")){
					data.add(line);
					System.out.println(line);
				}else if(line.contains(",3,")){
					data.add(line);
					System.out.println(line);
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
