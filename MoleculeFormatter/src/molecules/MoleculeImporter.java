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

/**
 * <h1>Molecule Importer</h1> This class interprets files to import them into a
 * molecule
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class MoleculeImporter
{
	/**
	 * Imports a .CML file into the specified Molecule at the specified filePath
	 * 
	 * @param molecule
	 * @param filePath
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
							double xCoordinate;
							double yCoordinate;
							double zCoordinate;

							Attribute attribute = startElement.getAttributeByName(new QName("id"));
							System.out.println("\tAtom ID:\t" + attribute.getValue());
							atomID = attribute.getValue();

							attribute = startElement.getAttributeByName(new QName("elementType"));
							System.out.println("\tElement Type:\t" + attribute.getValue());
							elementType = attribute.getValue();

							attribute = startElement.getAttributeByName(new QName("x3"));
							System.out.println("\tX Coordinate:\t" + attribute.getValue());
							xCoordinate = Double.parseDouble(attribute.getValue());

							attribute = startElement.getAttributeByName(new QName("y3"));
							System.out.println("\tY Coordinate:\t" + attribute.getValue());
							yCoordinate = Double.parseDouble(attribute.getValue());

							attribute = startElement.getAttributeByName(new QName("z3"));
							System.out.println("\tZ Coordinate:\t" + attribute.getValue());
							zCoordinate = Double.parseDouble(attribute.getValue());

							molecule.addAtom(atomID, elementType, xCoordinate, yCoordinate, zCoordinate);

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
			// TODO Dear god please do something with these errors
			// TODO maybe a warning dialog or something? Like, for real?
			System.err.println("Are you sure that was a .CML file?");
			// e.printStackTrace();
		} catch(NoSuchElementException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
