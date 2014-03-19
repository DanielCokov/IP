import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
		
			System.out.println("============================================");
			System.out.println(attributesMap);
			
				String total = attributesMap.toString();

				Document document = db.newDocument();
			
				Element item = document.createElement("item"); 
				item.setAttribute("kolichestvo", total);         
				document.appendChild(item);                    
		  

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				DOMSource source = new DOMSource(document);          
				StreamResult result = new StreamResult("out.xml");  
				transformer.transform(source, result);
			
	}
		
	public static void listAllAttributes(Element element) {		
		
		NamedNodeMap attributes = element.getAttributes();

		int numAttrs = attributes.getLength();

		for (int i = 0; i < numAttrs; i++) {
			
			Attr attr = (Attr) attributes.item(i);
			
			String attrName = attr.getNodeName();
			String attrValue = attr.getNodeValue();
			
			int newAttrValue = Integer.parseInt(attrValue);
			
			//System.out.println(  attrName + " - " + attrValue);
			
			int oldAttrValue = 0;
			
				if( attributesMap.containsKey(attrName)){
				
					oldAttrValue = attributesMap.get(attrName);
				
				} 
			
			int sum = oldAttrValue + newAttrValue ;
			
			attributesMap.put(attrName, sum);
		}
		
	}
	
}

