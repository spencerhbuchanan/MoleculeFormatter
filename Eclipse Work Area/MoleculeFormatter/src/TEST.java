import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.namespace.QName;

public class TEST
{

	public static void main(String[] args) {
		//Molecule molecule = new Molecule("MoleculeTest");
		
		try
		{
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader("C:\\Users\\Spencer Buchanan\\OneDrive - Florida Gulf Coast University\\CompChem\\Cation.cml"));
			
			while(eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();
				
				//System.out.print(event.getEventType());
				
				if(event.getEventType() == XMLStreamConstants.START_DOCUMENT) System.out.println("Start Document");
				if(event.getEventType() == XMLStreamConstants.START_ELEMENT)
				{
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
					System.out.println("Start Element: " + qName);
					if(qName.equalsIgnoreCase("atom")) {
						//QName name = new QName("elementType");
						Attribute attribute = startElement.getAttributeByName(new QName("elementType"));
						System.out.println(attribute.getValue());
					}
				}
				if(event.getEventType() == XMLStreamConstants.END_ELEMENT)
				{
					EndElement endElement = event.asEndElement();
					String qName = endElement.getName().getLocalPart();
					System.out.println("End Element: " + qName);
				}
				if(event.getEventType() == XMLStreamConstants.CHARACTERS) System.out.println("Characters");
				
				/*switch(event.getEventType())
				{
					case XMLStreamConstants.START_DOCUMENT:
						System.out.println("Started Reading");
						break;
					case XMLStreamConstants.END_DOCUMENT:
						System.out.println(Document);
				}*/
			}
			
		} catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(XMLStreamException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NoSuchElementException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {}
		
	}

}
