package ac.id.itb.d4.minimart.employee.parser;

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

import ac.id.itb.d4.minimart.employee.model.Account;

public class ActivitiesParser {
	
	public Vector<Account> parseData(String data) {
		Vector<Account> v = new Vector<Account>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			try {
				doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data.getBytes("utf-8"))));
				doc.getDocumentElement().normalize();
				
				System.out.println("[AccountParser]Root element :" + doc.getDocumentElement().getNodeName());				 
				NodeList nList = doc.getElementsByTagName("org.opencrx.kernel.account1.Contact");
				
				for (int i = 0; i < nList.getLength(); i++) {
					v.addElement(parseAccount(nList, i));
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
	
	public Account parseAccount(NodeList nList, int index){
		Account account = new Account();
		
		Node nNode = nList.item(index);
		
		System.out.println("[AccountParser]Element Length :" + nList.getLength()); 
		System.out.println("[AccountParser]Current Element :" + nNode.getNodeName());
 
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode; 
			System.out.println("Account href : " + eElement.getAttribute("href"));
			
			account.setLastName(eElement.getElementsByTagName("lastName").item(0).getTextContent());
			System.out.println("Last Name : " + eElement.getElementsByTagName("lastName").item(0).getTextContent());
			account.setObjectId(eElement.getElementsByTagName("identity").item(0).getTextContent());
			System.out.println("Identity : " + eElement.getElementsByTagName("identity").item(0).getTextContent());
			account.setFullName(eElement.getElementsByTagName("fullName").item(0).getTextContent());
			System.out.println("Full Name : " + eElement.getElementsByTagName("fullName").item(0).getTextContent());
			account.setCreatedAt(eElement.getElementsByTagName("createdAt").item(0).getTextContent());
			System.out.println("Modified At : " + eElement.getElementsByTagName("createdAt").item(0).getTextContent());
			account.setModifiedAt(eElement.getElementsByTagName("modifiedAt").item(0).getTextContent());
			System.out.println("Modified At : " + eElement.getElementsByTagName("modifiedAt").item(0).getTextContent());
			account.setVcard(eElement.getElementsByTagName("vcard").item(0).getTextContent());
			System.out.println("Vcard : " + eElement.getElementsByTagName("vcard").item(0).getTextContent());
			
		}
		
		return account;
	}
	
}
