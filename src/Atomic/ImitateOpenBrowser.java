package Atomic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ImitateOpenBrowser {
	
	static ArrayList<String> addresses = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, URISyntaxException{
		String filename = "C:\\Users\\Leo\\Desktop\\test.csv";
		readAddress(filename);
//		System.out.println(addresses.get(1));
		for(int i=0; i<addresses.size(); i++){
			openBrowser(addresses.get(i));
		}
//		writeFile(filename);
	}
	
	private static void readAddress(String filename) {
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
			String line = "";
			while((line = br.readLine()) != null){
//				int start = line.indexOf("鄉") + 1;
//				line = line.substring(start);
//				line = line.replaceAll("民", "");
//				line = line.replaceAll("學", "");
//				line = line.replaceAll("級", "");
				addresses.add(line);
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void openBrowser(String address) throws IOException, URISyntaxException {
		String url = "https://www.google.com.tw/maps/place/%E6%96%B0%E5%8C%97%E5%B8%82%E8%B2%A2%E5%AF%AE%E5%8D%80%E7%A6%8F%E9%9A%86%E5%9C%8B%E6%B0%91%E5%B0%8F%E5%AD%B8/@25.017466,121.948094,17z/data=!3m1!4b1!4m2!3m1!1s0x345d5daadeeb6db1:0x6d4f686090334649?q=";
		url += address;
		if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
            System.out.println("download: " + url);
        }else{
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("xdg-open " + url);
        }
	}
}
