import molecules.Molecule;

import java.io.Console;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args) {
		short menuSelection = 0;
		
		Scanner inputScanner = new Scanner(System.in);
		
		do 
		{
			menuSelection = 0;
			
			
			
			System.out.printf(	"Menu Options:\n"
					+ 		"\t1. Go To PSI Specific Items\n"
					+ 		"\t2. Import Molecule\n"
					+ 		"\t3. Print Molecule\n");
			
			System.out.flush();
			
			menuSelection = inputScanner.nextShort();
			
			switch(menuSelection)
			{
				case 1:
					break;
				
				case 2:
					break;
				
				case 3:
					break;
				
				default:
					System.out.println("Not an option.");
			}
			
			
		} while (menuSelection > 0);
		
		inputScanner.close();
		
		/*
		Molecule molecule = new Molecule("MoleculeTest");
		molecule.importFile("Included");
		molecule.printMolecule();
		*/
	}
}
