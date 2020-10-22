package p.xml;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomTest {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse("entity/applicationContext.xml");
			document.normalize();
			Element root = document.getDocumentElement();

			NodeList nl = root.getChildNodes();
			System.out.println(nl.getLength());
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				System.out.println(node.getNodeName() + ":" + node.getNodeType() + ":" + node.getNodeValue());
			}
			System.out.println("解析完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);

	}
}
