package ReadFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadHtml {
	private static ArrayList<String> lists = new ArrayList<String>();
	private static ArrayList<HashMap<String, String>> articles = new ArrayList<HashMap<String, String>>();
	private static String sum;
	public static void main(String[] args){
		String url = "http://wjchang.pixnet.net/blog/listall/1";
		readListAll(url);
		for(int i=0; i<lists.size(); i++){
			readPost(lists.get(i));
		}
		for(int i=0; i<articles.size(); i++){
			sumArticles(articles.get(i));
		}
		saveFileTxt(sum);
	}
	
	private static void sumArticles(HashMap<String, String> hashMap) {
		String article = hashMap.get("title") + "\n\r" + hashMap.get("content");
		sum += "\n\r" + article; 
	}

	private static void saveFileTxt(String article) {
		String filename = "萬惡的人力資源主管部落格-1.txt";
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
			out.write(article);
			out.close();
			System.out.println("*** Also wrote this information to file: " + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void readPost(String post) {
		try {
			URL url = new URL(post);
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String title = "";
			String article = "";
			String line = "";
			HashMap<String, String> hm = new HashMap<>();
			while((line = in.readLine()) != null){
				if(line.contains("class=\"article-content-inner\"")){
					int start = 73;
					int end = line.indexOf("本文最早刊登於") - 20;
					article = line.substring(start, end);
					article = editArticle(article);
					hm.put("content", article);
				}else if(line.contains("class=\"header-title\"")){
					int h3_tag = line.indexOf("data-site-category-id");
					int start = line.indexOf(">", h3_tag) + 1;
					int end = line.indexOf("</h3>");
					title = line.substring(start, end);
					hm.put("title", title);
				}
			}
			articles.add(hm);
			in.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String editArticle(String article) {
		String result = article;
		result = result.replaceAll("</span>", "");
		result = result.replaceAll("<span style=\"font-size: 10pt;\">", "");
		result = result.replace("<span style=\"font-size: 13px;\">", "");
		result = result.replaceAll("<br />", "\n\r");
		return result;
	}
	
	private static void readListAll(String u) {
		try {
			URL url = new URL(u);
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String line = "";
			while((line = in.readLine()) != null){
				if(line.contains("http://wjchang.pixnet.net/blog/post/") && line.contains("opition-items")){
					int start = 0;
					int end = 0;
					start = line.indexOf("href") + 6;
					end = line.indexOf("\"", start);
					lists.add(line.substring(start, end));
				}
			}
			in.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
