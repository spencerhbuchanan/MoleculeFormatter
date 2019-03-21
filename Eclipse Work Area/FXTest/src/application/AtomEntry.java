package application;

public class AtomEntry extends Atom
{
	public AtomEntry(	String atomID, 
					String atomElement, 
					Double atomX, 
					Double atomY, 
					Double atomZ)
	{
		this.setAtomID(atomID);
		this.setAtomElement(atomElement);
		
		this.setAtomX(atomX);
		this.setAtomY(atomY);
		this.setAtomZ(atomZ);
	}
}
