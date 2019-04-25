package molecules;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.DoubleFunction;

/**
 * <h1>Molecule Exporter</h1>
 * Contains various export formats for molecules
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-24-04
 */
public class MoleculeExporter {
  /**
   * Exports the molecule following XYZ format
   * 
   * @param molecule
   * @param fileExportDestination
   * @throws IOException
   */
  static public void exportMoleculeAsXYZ(MoleculeModel molecule,
                                         File fileExportDestination) throws IOException {
    ArrayList<String> outputFile = new ArrayList<String>();

    /*
     * whitespaceBetweenValues: Amount of whitespace to put between element and each coordinate.
     * CURRENTLY: 7
     * 
     * coordinateFormat: Ensures that there are at least five digits after the decimal place. Also
     * acts as a double to String converter when used.
     * TODO: Adjust formatting so it can adaptively accommodate any number of digits before the decimal.
     */
    String whitespaceBetweenValues = "       ";
    DecimalFormat decimalFormatForCoordinates = new DecimalFormat("####0.00000");


    /*
     * coordinateFormatter
     * 
     * Input: Double Value
     * 
     * Output: Input Value, as a string, with an extra space at the beginning if the Input is
     * positive
     * 
     * Declared outside of following forEach to avoid re-creating the function for every atom.
     */
    DoubleFunction<String> coordinateFormatter = (coordinateValue) -> {
      if (coordinateValue >= 0) {
        return (" " + decimalFormatForCoordinates.format(coordinateValue));
      } else {
        return (decimalFormatForCoordinates.format(coordinateValue));
      }
    };

    molecule.forEachAtom((atom) -> {
      String atomElement = atom.getAtomElement();

      String atomX = coordinateFormatter.apply(atom.getAtomX());
      String atomY = coordinateFormatter.apply(atom.getAtomY());
      String atomZ = coordinateFormatter.apply(atom.getAtomZ());

      outputFile.add(atomElement + whitespaceBetweenValues + atomX + whitespaceBetweenValues + atomY
                     + whitespaceBetweenValues + atomZ + whitespaceBetweenValues);
    });

    /*
     * Adds the Number of atoms in the molecule to the top of the XYZ file, as well as the name of
     * the molecule on the following line
     */
    outputFile.add(0, Integer.toString(outputFile.size()));
    outputFile.add(1, molecule.getMoleculeName());

    Files.write(Paths.get(fileExportDestination.getAbsolutePath()),
                outputFile,
                Charset.forName("UTF-8"));
  }
}
