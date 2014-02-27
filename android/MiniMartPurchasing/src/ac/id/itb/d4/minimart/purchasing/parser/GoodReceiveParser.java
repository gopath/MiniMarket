package ac.id.itb.d4.minimart.purchasing.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ac.id.itb.d4.minimart.purchasing.model.GoodReceive;

public class GoodReceiveParser {
	
	public Vector<GoodReceive> parseData(String data) {
		Vector<GoodReceive> v = new Vector<GoodReceive>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data.getBytes("utf-8"))));
				doc.getDocumentElement().normalize();
				
				System.out.println("[ProductParser]Root element :" + doc.getDocumentElement().getNodeName());				 
				NodeList nList = doc.getElementsByTagName("org.opencrx.kernel.product1.Product");
				
				for (int i = 0; i < nList.getLength(); i++) {
					v.addElement(parseProduct(nList, i));
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return v;
    }
	
	public GoodReceive parseProduct(NodeList nList, int index){
		GoodReceive product = new GoodReceive();
		
		Node nNode = nList.item(index);
		
		System.out.println("[ProductParser]Element Length :" + nList.getLength()); 
		System.out.println("[ProductParser]Current Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode; 
			System.out.println("Product href : " + eElement.getAttribute("href"));
			
			product.setProductName(eElement.getElementsByTagName("name").item(0).getTextContent());
			System.out.println("Product Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
			product.setDetailsDescription(eElement.getElementsByTagName("detailedDescription").item(0).getTextContent());
			System.out.println("Dtl Description : " + eElement.getElementsByTagName("detailedDescription").item(0).getTextContent());
			product.setDefaultQty(Float.parseFloat(eElement.getElementsByTagName("defaultQuantity").item(0).getTextContent()));
			System.out.println("Default Qty: " + eElement.getElementsByTagName("defaultQuantity").item(0).getTextContent());
			
		}
		
		return product;
	}
	
}
