package ac.id.itb.d4.minimart.manager.parser;

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

import ac.id.itb.d4.minimart.manager.model.Documents;


public class DocumentParser {
	
	public Vector<Documents> parseData(String data) {
		Vector<Documents> v = new Vector<Documents>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data.getBytes("utf-8"))));
				doc.getDocumentElement().normalize();
				
				System.out.println("[DocumentParser]Root element :" + doc.getDocumentElement().getNodeName());				 
				NodeList nList = doc.getElementsByTagName("org.opencrx.kernel.document1.Document");
				
				for (int i = 0; i < nList.getLength(); i++) {
					v.addElement(parseDocument(nList, i));
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
	
	public Documents parseDocument(NodeList nList, int index){
		Documents document = new Documents();
		
		Node nNode = nList.item(index);
		
		System.out.println("[DocumentParser]Element Length :" + nList.getLength()); 
		System.out.println("[DocumentParser]Current Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode; 			document.setAuthor(eElement.getElementsByTagName("author").item(0).getTextContent());
			System.out.println("Author : " + eElement.getElementsByTagName("author").item(0).getTextContent());
			
			document.setLocation(eElement.getElementsByTagName("location").item(0).getTextContent());
			System.out.println("Location : " + eElement.getElementsByTagName("location").item(0).getTextContent());
			
			document.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
			System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
			
			document.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
			System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
			
			document.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
			System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
			
			
				
		}
		
		return document;
	}
	
}
