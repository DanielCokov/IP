import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.omg.CORBA.NameValuePair;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Iterate {
	
	static Map<String, Integer> attributesMap = new HashMap<>();

	public static void main(String[] args) throws Exception {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(new FileInputStream(new File("input.xml")));
		
		NodeList entries = doc.getElementsByTagName("item");
		
		int num = entries.getLength();
		
		for (int i=0; i<num; i++) {
			Element node = (Element) entries.item(i);
			listAllAttributes(node);
		}
		System.out.println(attributesMap);
	}

	public static void listAllAttributes(Element element) {		
		
		NamedNodeMap attributes = element.getAttributes();

	
	
		int numAttrs = attributes.getLength();

		for (int i = 0; i < numAttrs; i++) {
			Attr attr = (Attr) attributes.item(i);
			
			String attrName = attr.getNodeName();
			String attrValue = attr.getNodeValue();
			
			int newAttrValue = Integer.parseInt(attrValue);
			
			System.out.println("Found attribute: " + attrName + " - " + attrValue);
			
			int oldAttrValue = 0;
			
			if( attributesMap.containsKey(attrName)){
				
				oldAttrValue = attributesMap.get(attrName);
				
			} 
			
			int sum = oldAttrValue + newAttrValue ;
			
			attributesMap.put(attrName, sum);
		}
		
	}
}

