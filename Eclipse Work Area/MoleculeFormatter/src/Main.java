/*
 * Author: Spencer Buchanan
 */

import molecules.Molecule;

import java.util.Scanner;

public class Main
{

	public static void main(String[] args) throws InterruptedException {
		
		short menuSelection = 0;

		Scanner inputScanner = new Scanner(System.in);
		
		Molecule molecule = new Molecule("REBECCA");
		
		do 
		{
			menuSelection = 0;
						
			System.out.printf(	"Menu Options:\n"
					+ 		"\t1. Go To PSI Specific Items\n"
					+ 		"\t2. Import Molecule\n"
					+ 		"\t3. Print Molecule\n"
					+ 		"\t-1. Exit program");
			
			System.out.flush();

			try {	
				
				String menuString = inputScanner.nextLine(); 		// Inputs whole line into temp variable
				menuSelection = Short.parseShort(menuString); 	// Tries to parse as short
				
			} catch(NumberFormatException e) {							//If not a number, show an error
				System.err.println("Error. Input NAN");
				Thread.sleep(1000);
				continue;
			}
			
			switch(menuSelection)
			{
				case -1:											//Asking to exit program, let skip the switch
					System.out.println("Ending...");
					break;
					
				case 1:
					PSI2.runPSI2(inputScanner);
					System.out.println("PSI2 DONE");
					break;
					
				case 2:
					molecule.importFile(inputScanner.nextLine());
					System.out.println("File imported.");
					break;
					
				case 3:
					molecule.printMolecule();
					break;
				
				default:
					System.out.println("Not an option... Reloading in a second...");
					Thread.sleep(1000);
			}
			
		} while (menuSelection != -1);
		
		inputScanner.close();
		
		/*
		Molecule molecule = new Molecule("MoleculeTest");
		molecule.importFile("Included");
		molecule.printMolecule();
		*/
	}
}
