/*
 * Author:	Spencer Buchanan
 * 
 * Desc:		Class to contain and describe an atom
 * 
 * Stores:	atomID
 * 			atomElement
 * 			atomX
 * 			atomY
 * 			atomZ
 */

package molecules;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Atom
{
	private StringProperty	atomID		= new SimpleStringProperty();
	private StringProperty	atomElement	= new SimpleStringProperty();

	private DoubleProperty	atomX		= new SimpleDoubleProperty();
	private DoubleProperty	atomY		= new SimpleDoubleProperty();
	private DoubleProperty	atomZ		= new SimpleDoubleProperty();

	/*
	 * Atom ID/Element get/set/properties
	 */
	
	public void setAtomID(String atomID)
	{
		this.atomID.set(atomID);
	}
	
	public void setAtomElement(String atomElement)
	{
		this.atomElement.set(atomElement);
	}
	
	public String getAtomID()
	{
		return this.atomID.get();
	}
	
	/*
	 * Atom Element get/set/properties
	 */
	
	public String getAtomElement()
	{
		return this.atomElement.get();
	}
	
	public StringProperty atomIDProperty()
	{
		return this.atomID;
	}
	
	public StringProperty atomElementProperty()
	{
		return this.atomElement;
	}
	
	/*
	 * XYZ Coordinate get/set/properties
	 */
	
	public void setAtomX(double atomX)
	{
		this.atomX.set(atomX);
	}
	
	public void setAtomY(double atomY)
	{
		this.atomY.set(atomY);
	}
	
	public void setAtomZ(double atomZ)
	{
		this.atomZ.set(atomZ);
	}
	
	public double getAtomX()
	{
		return this.atomX.get();
	}
	
	public double getAtomY()
	{
		return this.atomY.get();
	}
	
	public double getAtomZ()
	{
		return this.atomZ.get();
	}
	
	public DoubleProperty atomXProperty()
	{
		return this.atomX;
	}
	
	public DoubleProperty atomYProperty()
	{
		return this.atomY;
	}
	
	public DoubleProperty atomZProperty()
	{
		return this.atomZ;
	}
}
