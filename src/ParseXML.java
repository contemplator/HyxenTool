import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXML {
	// 可以搭配  https://www.dropbox.com/s/x68otfjxq6wcdiy/ParseXML.xlsm 檔案一起使用
	public void getAllUserNames(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            if (file.exists()) {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
 
                // Print root element of the document
                System.out.println("Root element of the document: "
                        + docEle.getNodeName());
 
                NodeList museumList = docEle.getElementsByTagName("museum");
 
                // Print total student elements in document
                System.out.println("Total museums: " + museumList.getLength());
 
                if (museumList != null && museumList.getLength() > 0) {
                    for (int i = 0; i < museumList.getLength(); i++) {
 
                        Node node = museumList.item(i);
 
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
 
                            System.out.println("=====================");
 
                            Element e = (Element) node;
                            NodeList nodeList = e.getElementsByTagName("name");
                            System.out.println("Name: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue());
 
                            nodeList = e.getElementsByTagName("city");
                            System.out.println("City: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue());
 
                            nodeList = e.getElementsByTagName("address");
                            System.out.println("address: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                            .getNodeValue());
                        }
                    }
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
         
    	ParseXML parser = new ParseXML();
        parser.getAllUserNames("c:\\test.xml");
    }
}
