package UserResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class GoogleComments {
	public static void main(String arg[]) {
		String filename = "D:\\Hyxen\\Google_comments\\Barcode\\reviews_com.hyxen.app.Barcode_201407.csv";
		ArrayList<String[]> data = readCSV(filename);
//		for(int i=0; i<data.size(); i++){
//			System.out.println(data.get(i));
//		}
		ArrayList<HashMap<String, String>> info = repairData(data);
		info = classify(info);
		String info_csv = arraylistConvertCSV(info);
		writeCSV(info_csv, filename);
	}

	private static ArrayList<String[]> readCSV(String filename){
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"Unicode"));
//			String line;
			String line = br.readLine();
			String[] column = line.split(",");
			list.add(column);
			while((line = br.readLine()) != null){
				line.replaceAll(",", "，");
//				System.out.println(line);
				String[] info = line.split(",");
				list.add(info);
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	private static ArrayList<HashMap<String, String>> repairData( ArrayList<String[]> data) {
		ArrayList<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
		String[] keys = data.get(0); 
		for(int i=1; i<data.size(); i++){
			HashMap<String, String> hm = new HashMap<String, String>();
			String[] piece = data.get(i);
			for(int j=0; j<keys.length; j++){
				hm.put(keys[j], "");
			}
			for(int k=0; k<piece.length; k++){
				hm.put(keys[k], piece[k]);
			}
			info.add(hm);
		}
		return info;
	}
	
	private static ArrayList<HashMap<String, String>> classify(ArrayList<HashMap<String, String>> info_pre) {
		String[] keys = {"﻿Package Name", "App Version", "Review Submit Date and Time", "Category of problem",
		            "Review Last Update Date and Time", "Star Rating", "Review Title",
		            "Review Text", "Developer Reply Date and Time", "Developer Reply Text"};
		ArrayList<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
		for(int i=0; i<info_pre.size(); i++){
			HashMap<String, String> hm = new HashMap<String, String>();
			for(int j=0; j<keys.length; j++){
				if(keys[j] == "Review Text"){
					// create category -> keywords file
					 if(info_pre.get(i).get(keys[j]).contains("廣告")){
						 hm.put("Category of problem", "廣告");
						 hm.put("Review Text", info_pre.get(i).get(keys[j]));
					 }else{
						 hm.put("Category of problem", "");
						 hm.put("Review Text", info_pre.get(i).get(keys[j]));
					 }
				}else if(keys[j] == "Category of problem"){
					continue;
				}else{
					hm.put(keys[j], info_pre.get(i).get(keys[j]));
				}
			}
			info.add(hm);
		}
		return info;
	}

	private static String arraylistConvertCSV(ArrayList<HashMap<String, String>> info) {
		String info_csv = "";
		
		// add column
		HashMap<String, String> hm_column = info.get(0);
		for(Entry<String, String> entry: hm_column.entrySet()){
			info_csv += entry.getKey();
			info_csv += ",";
		}
		info_csv = info_csv.substring(0,info_csv.length()-2);
		info_csv += "\r\n";
		
		for(int i=0; i<info.size(); i++){
			HashMap<String, String> hm = info.get(i);
			for(Entry<String, String> entry: hm.entrySet()){
				info_csv += entry.getValue();
				info_csv += ",";
			}
			info_csv = info_csv.substring(0,info_csv.length()-2);
			info_csv += "\r\n";
		}
		return info_csv;
	}
	
	private static void writeCSV(String info_csv, String filename_pre) {
		int length = filename_pre.length();
		String filename = filename_pre.substring(0, length-4);
		filename += "_done.csv";
		
		System.out.println(info_csv);
		System.out.println(filename);
		
		try {
//            FileWriter fstream = new FileWriter(filename, false);
//            BufferedWriter out = new BufferedWriter(fstream);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "Cp1252"));
            out.write(info_csv);
            out.close();
            System.out.println("*** Also wrote this information to file: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
