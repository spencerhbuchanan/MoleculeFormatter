package molecules;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * <h1>Atom</h1> The Atom class stores a single atom, with its ID, Element, and
 * XYZ coordinates.
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class Atom
{
	private StringProperty	atomID		= new SimpleStringProperty();
	private StringProperty	atomElement	= new SimpleStringProperty();

	private DoubleProperty	atomX		= new SimpleDoubleProperty();
	private DoubleProperty	atomY		= new SimpleDoubleProperty();
	private DoubleProperty	atomZ		= new SimpleDoubleProperty();

	/**
	 * Sets the atoms atomID
	 * 
	 * @param atomID What to set the Atoms ID to
	 */
	public void setAtomID(String atomID)
	{
		this.atomID.set(atomID);
	}

	/**
	 * Sets the atoms element
	 * 
	 * @param atomElement What to set the Atoms Element to
	 */
	public void setAtomElement(String atomElement)
	{
		this.atomElement.set(atomElement);
	}

	/**
	 * Gets the Atoms atomID
	 * 
	 * @return String returns the Atoms ID
	 */
	public String getAtomID()
	{
		return this.atomID.get();
	}

	/**
	 * Gets the Atoms element
	 * 
	 * @return String returns the Atoms ID
	 */
	public String getAtomElement()
	{
		return this.atomElement.get();
	}

	/**
	 * Gets the atomID's StringProperty
	 * 
	 * @return StringProperty returns the atomID's StringProperty
	 */
	public StringProperty atomIDProperty()
	{
		return this.atomID;
	}

	/**
	 * Gets the atomElements's StringProperty
	 * 
	 * @return StringProperty returns the atomID's StringProperty
	 */
	public StringProperty atomElementProperty()
	{
		return this.atomElement;
	}

	/**
	 * Sets the Atom's X coordinate
	 * 
	 * @param atomX
	 */
	public void setAtomX(double atomX)
	{
		this.atomX.set(atomX);
	}

	/**
	 * Sets the Atom's Y coordinate
	 * 
	 * @param atomY
	 */
	public void setAtomY(double atomY)
	{
		this.atomY.set(atomY);
	}

	/**
	 * Sets the Atom's Z coordinate
	 * 
	 * @param atomZ
	 */
	public void setAtomZ(double atomZ)
	{
		this.atomZ.set(atomZ);
	}

	/**
	 * Gets the Atom's X coordinate
	 * 
	 * @return double The Atom's X coordinate
	 */
	public double getAtomX()
	{
		return this.atomX.get();
	}

	/**
	 * Gets the Atom's x coordinate
	 * 
	 * @return double The Atom's Y coordinate
	 */
	public double getAtomY()
	{
		return this.atomY.get();
	}

	/**
	 * Gets the Atom's Z coordinate
	 * 
	 * @return double The Atom's Z coordinate
	 */
	public double getAtomZ()
	{
		return this.atomZ.get();
	}

	/**
	 * Gets the Atom's X coordinate DoubleProperty
	 * 
	 * @return DoubleProperty The Atom's X coordinate DoubleProperty
	 */
	public DoubleProperty atomXProperty()
	{
		return this.atomX;
	}

	/**
	 * Gets the Atom's Y coordinate DoubleProperty
	 * 
	 * @return DoubleProperty The Atom's Y coordinate DoubleProperty
	 */
	public DoubleProperty atomYProperty()
	{
		return this.atomY;
	}

	/**
	 * Gets the Atom's Z coordinate DoubleProperty
	 * 
	 * @return DoubleProperty The Atom's Z coordinate DoubleProperty
	 */
	public DoubleProperty atomZProperty()
	{
		return this.atomZ;
	}
}
