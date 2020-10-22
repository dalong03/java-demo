package p.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try (Scanner s = new Scanner(new File("entity/applicationContext.xml"))) {
			StringBuilder content = new StringBuilder();
			while (s.hasNextLine()) {
				content.append(s.nextLine());
			}
			Document document = DocumentHelper.parseText(content.toString());
			document.normalize();
			Element root = document.getRootElement();
			List<Element> el = root.elements();
			for (Element e : el) {
				System.out.println(e.getName() + ":" + e.getNodeType());
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
	}
}
