
import java.util.ArrayList;

public class Molecule
{
	private String				moleculeName;

	private ArrayList<String>	atomID			= new ArrayList<String>();
	private ArrayList<String>	atomElement		= new ArrayList<String>();

	private ArrayList<Double>	atomXCoordinate	= new ArrayList<Double>();
	private ArrayList<Double>	atomYCoordinate	= new ArrayList<Double>();
	private ArrayList<Double>	atomZCoordinate	= new ArrayList<Double>();

	private ArrayList<String> boundAtoms = new ArrayList<String>();
	private ArrayList<Short> bondOrder = new ArrayList<Short>();

	public Molecule(String moleculeName)
	{
		this.moleculeName = moleculeName;
	}

	public Molecule()
	{
		this.moleculeName = "unnamedMolecule";
	}

	public void addAtom(String atomID)
	{
		this.atomID.add(atomID);
		this.atomElement.add("Nullium");

		this.atomXCoordinate.add(0.0);
		this.atomYCoordinate.add(0.0);
		this.atomZCoordinate.add(0.0);
	}

	public void addAtom(String atomID, String atomElement, double atomXCoordinate, double atomYCoordinate, double atomZCoordinate)
	{
		this.atomID.add(atomID);
		this.atomElement.add(atomElement);

		this.atomXCoordinate.add(atomXCoordinate);
		this.atomYCoordinate.add(atomYCoordinate);
		this.atomZCoordinate.add(atomZCoordinate);
	}

	public void addBond(String boundAtoms, short bondOrder)
	{
		this.boundAtoms.add(boundAtoms);
		this.bondOrder.add(bondOrder);
	}
	
	public String getElement(int atom)
	{
		return this.atomElement.get(atom);
	}

	public double getXCoordinate(int atom)
	{
		return this.atomXCoordinate.get(atom);
	}

	public double getYCoordinate(int atom)
	{
		return this.atomYCoordinate.get(atom);
	}

	public double getZCoordinate(int atom)
	{
		return this.atomZCoordinate.get(atom);
	}

	public void printMolecule()
	{

		System.out.println("Atoms in " + this.moleculeName);

		for(int i = 0; i < this.atomID.size(); i++)
		{
			System.out.println(this.atomID.get(i));
			System.out.println(this.atomElement.get(i));

			System.out.println(this.atomXCoordinate.get(i));
			System.out.println(this.atomYCoordinate.get(i));
			System.out.println(this.atomZCoordinate.get(i));

			System.out.println("");
		}
		
		System.out.println("Bonds");
		
		for(int i = 0; i < this.boundAtoms.size(); i++)
		{
			System.out.println(this.boundAtoms.get(i));
			System.out.println(this.bondOrder.get(i));
		}
		
	}
}
