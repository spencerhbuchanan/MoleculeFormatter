/*
 * Author: Spencer Buchanan
 */

package molecules;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class MoleculeImporter
{
	/*
	 * Import .CML file
	 * 
	 * Given: Molecule to input into, file to be read (full path)
	 * 
	 * importCmlFile is intended to read a .cml file (path denoted by filePath) into
	 * a molecule class (passed through molecule reference)
	 * 
	 * Currently only known to be able to import BASIC cml files (like ones output from Avogadro)
	 */
	public static void importCmlFile(Molecule molecule, String filePath)
	{
		try
		{
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(filePath));

			while(eventReader.hasNext())
			{
				XMLEvent event = eventReader.nextEvent();

				if(event.getEventType() == XMLStreamConstants.START_DOCUMENT)
					System.out.println("Start Document");
				
				if(event.getEventType() == XMLStreamConstants.START_ELEMENT)
				{
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();

					switch(qName)
					{
						case "atom":
						{
							System.out.println("Start Element: " + qName);

							String atomID;
							String elementType;
							double x3;
							double y3;
							double z3;

							Attribute attribute = startElement.getAttributeByName(new QName("id"));
							System.out.println("\tAtom ID:\t" + attribute.getValue());
							atomID = attribute.getValue();
							attribute = startElement.getAttributeByName(new QName("elementType"));
							System.out.println("\tElement Type:\t" + attribute.getValue());
							elementType = attribute.getValue();

							attribute = startElement.getAttributeByName(new QName("x3"));
							System.out.println("\tX Coordinate:\t" + attribute.getValue());
							x3 = Double.parseDouble(attribute.getValue());

							attribute = startElement.getAttributeByName(new QName("y3"));
							System.out.println("\tY Coordinate:\t" + attribute.getValue());
							y3 = Double.parseDouble(attribute.getValue());

							attribute = startElement.getAttributeByName(new QName("z3"));
							System.out.println("\tZ Coordinate:\t" + attribute.getValue());
							z3 = Double.parseDouble(attribute.getValue());

							molecule.addAtom(atomID, elementType, x3, y3, z3);

							break;
						}
						case "bond":
						{
							System.out.println("Start Element: " + qName);

							String boundAtoms;
							short bondOrder;

							Attribute attribute = startElement.getAttributeByName(new QName("atomRefs2"));
							System.out.println("\tBound Atoms:\t" + attribute.getValue());
							boundAtoms = attribute.getValue();
							attribute = startElement.getAttributeByName(new QName("order"));
							System.out.println("\tBond Order:\t" + attribute.getValue());
							bondOrder = Short.parseShort(attribute.getValue());

							molecule.addBond(boundAtoms, bondOrder);

							break;
						}
						default:
					}

				}

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
		}
	}
}