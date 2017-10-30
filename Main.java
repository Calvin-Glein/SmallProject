import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static ArrayList<ElementImported> elements = new ArrayList<ElementImported>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			File fXmlFile = new File("C:\\Users\\Glenn\\Downloads\\elements.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Element");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;


					elements.add(new ElementImported(eElement.getElementsByTagName("Type").item(0).getTextContent(),
							eElement.getElementsByTagName("Text").item(0).getTextContent(),
							(int) Math.round(Float.parseFloat(eElement.getElementsByTagName("XCoordinate").item(0).getTextContent())),
							(int) Math.round(Float.parseFloat(eElement.getElementsByTagName("YCoordinate").item(0).getTextContent()))));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		WriteToFile();

	}

	public static void WriteToFile() {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String header = "import javax.swing.JFrame;\r\n" + "import javax.swing.JLabel;\r\n"
					+ "import javax.swing.JTextField;\r\n" + "\r\n" + "public class NoLayoutTest extends JFrame {\r\n"
					+ "\r\n" + "  public static void main(String[] args) {\r\n"
					+ "    JFrame.setDefaultLookAndFeelDecorated(true);\r\n"
					+ "    JFrame frame = new JFrame(\"NoLayout Test\");\r\n"
					+ "    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\r\n"
					+ "    frame.setLayout(null); \r\n";

			String content = "";

			for (int i = 0; i < elements.size(); i++) {
				
			
				System.out.println(elements.get(i).getxCoordinate());
				if (elements.get(i).getType().equals("label")) {
					content += " \n JLabel " + NumberToWords(i) + " = new JLabel(\"" + elements.get(i).getText()
							+ "\");\r\n" +"" +   NumberToWords(i) + ".setBounds(" + elements.get(i).getxCoordinate() + ", "
							+ elements.get(i).getyCoordinate() + ", 100, 20); ";

					content += "\r\n frame.add(" + NumberToWords(i) + "); \r\n ";

				}
			}
			content += "\r\n frame.setExtendedState(JFrame.MAXIMIZED_BOTH); \r\n" + 
					";\r\n" + "    frame.setVisible(true);\r\n";
			content += "  }\r\n" + "}";

			fw = new FileWriter("‪outputOfParser.java");
			bw = new BufferedWriter(fw);
			bw.write(header + content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
	
	static String unitsMap[] = { "zero", "one", "two", "three", "four", "five","six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	static String tensMap[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };


	public static String NumberToWords(int number) {
		if (number == 0)
			return "zero";

		if (number < 0)
			return "minus " + NumberToWords(Math.abs(number));

		String words = "";

		if ((number / 1000000000) > 0) {
			words += NumberToWords(number / 1000000000) + " billion ";
			number %= 1000000000;
		}

		if ((number / 1000000) > 0) {
			words += NumberToWords(number / 1000000) + " million ";
			number %= 1000000;
		}

		if ((number / 1000) > 0) {
			words += NumberToWords(number / 1000) + " thousand ";
			number %= 1000;
		}

		if ((number / 100) > 0) {
			words += NumberToWords(number / 100) + " hundred ";
			number %= 100;
		}

		if (number > 0) {
			if (number < 20)
				words += unitsMap[number];
			else {
				words += tensMap[number / 10];
				if ((number % 10) > 0)
					words += "-" + unitsMap[number % 10];
			}
		}

		return words;
	}
}
