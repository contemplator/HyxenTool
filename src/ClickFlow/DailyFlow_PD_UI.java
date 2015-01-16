package ClickFlow;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

public class DailyFlow_PD_UI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	TextField text_link = new TextField(" ", 50);
    TextField text_startdate = new TextField(" ", 50);
    TextField text_enddate = new TextField(" ", 50);
    Button submit = new Button("submit");
    TextArea output = new TextArea();
	
	public DailyFlow_PD_UI(){
		frame = new JFrame();
	}
	
	public void run(){
		frame.setSize(600, 400);
		frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Panel p1 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p1.setSize(100, 50);
        Label label_link = new Label("link");
        p1.add(label_link);
        p1.add(text_link);
        
        Panel p2 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p2.setSize(100, 50);
        Label label_startdate = new Label("startdate");
        p2.add(label_startdate);
        p2.add(text_startdate);
        
        Panel p3 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p3.setSize(100, 50);
        Label label_enddate = new Label("endate");
        p3.add(label_enddate);
        p3.add(text_enddate);
        
        Panel p4 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p4.setSize(100, 50);
        submit.addActionListener(this);
        p4.add(submit);
        
        Panel p5 = new Panel(new FlowLayout(FlowLayout.LEADING));
        p4.setSize(300, 200);
        p4.add(output);
        
        frame.add(p1);
        frame.add(p2);
        frame.add(p3);
        frame.add(p4);
        frame.add(p5);
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		DailyFlow_PD_UI gui = new DailyFlow_PD_UI();
        gui.run();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String link = null;
		String startdate = null;
		String enddate = null;
		List<String> dates = null;
		if (e.getSource() == submit && text_link != null && text_startdate != null && text_enddate != null){
			link = text_link.getText(); //LDP-bearmission3
			startdate = text_startdate.getText(); //20140401
			enddate = text_enddate.getText(); //20140425
		}
		
		startdate = startdate.substring(0, 4) + "-" + startdate.substring(4, 6) + "-" + startdate.substring(6, 8);
		enddate = enddate.substring(0, 4) + "-" + enddate.substring(4, 6) + "-" + enddate.substring(6, 8);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		output.setText(link + "\r\n" + startdate + "\r\n" + enddate);
		try{
		      Date date1 = formatter.parse(startdate);
		      Calendar calendar = Calendar.getInstance();
		      calendar.setTime(date1);
		      java.util.Date date2 = formatter.parse(enddate);
		      output.setText("");
		      while(calendar.getTime().compareTo(date2)<=0){
//		    	  dates.add(formatter.format(calendar.getTime()));
//		    	  output.append(dates.get(i));
//		    	  i++;
		    	  output.append("\n" + getFlow(link,formatter.format(calendar.getTime())));
//		    	  output.append("\n" + formatter.format(calendar.getTime()));
		    	  calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
		      }
		}catch(Exception e0){
		      e0.printStackTrace();
		}
		
//		for(int i=0; i<dates.size(); i++){
//			output.append(getFlow(link, dates.get(i)));
//		}
	}
	
	public static String getFlow(String piping, String date){
		String link = "http://s.ad-locus.com/detail.php?db=";
		String flow = "";
		int tot = 0;
		try {
			link += piping;
			URL url = new URL(link);
			URLConnection yc = url.openConnection(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String input = "";
			while ((input = in.readLine()) != null) {
				if(input.contains(date)){
					int score;
					score = Integer.parseInt(input.substring(29));
					tot += score;
				}
			}
			flow = date +"：" + tot;
			in.close();
		} 	catch (MalformedURLException e) {
		} 	catch (IOException e) {
		} finally {
		}
		
		return flow;
	}
}
