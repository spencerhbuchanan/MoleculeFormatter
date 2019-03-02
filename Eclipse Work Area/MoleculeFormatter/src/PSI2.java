/*
 * Author: Spencer Buchanan
 * 
 * Desc: PSI2 Specific requirements
 */

import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

public class PSI2 {
  public static void runPSI2(Scanner psiScanner) { // Pass the scanner from the menu to this
                                                   // function
    System.out.println("Enter two numbers, separated by a new line.");

    short numberOne, numberTwo = 0;

    try {
      String inputOne = psiScanner.nextLine();
      String inputTwo = psiScanner.nextLine();

      numberOne = Short.parseShort(inputOne);
      numberTwo = Short.parseShort(inputTwo);

    } catch (NumberFormatException e) {
      System.err.println("Error. Input NAN");

      return;
    }

    System.out.println("You entered the numbers " + numberOne + " and " + numberTwo);

    Random rng = new Random();

    System.out.println("Printing a random number between zero and the absolute value of numberOne: "
        + rng.nextInt(Math.abs(numberOne)));

    System.out.println("Evaluating numberOne and numberTwo with ternary construct.");

    String greater = numberOne > numberTwo ? "#1 is greater" : "#2 is greater";
    System.out
        .println("numberOne > numberTwo ? \"#1 is greater\" : \"#2 is greater\" =\t" + greater);

    System.out.println("numberOne + numberTwo: " + (numberOne + numberTwo));
    System.out.println("numberOne - numberTwo: " + (numberOne - numberTwo));
    System.out.println("numberOne * numberTwo: " + (numberOne * numberTwo));
    System.out.println("numberOne / numberTwo: " + ((double) numberOne / (double) numberTwo));
    System.out.println("numberOne % numberTwo: " + (numberOne % numberTwo)); // +, -, *, /, and %
                                                                             // are evaluated left
                                                                             // to right



    System.out.println("++numberOne = " + ++numberOne);
    numberOne--;
    System.out.println("--numberTwo = " + --numberTwo);
    numberTwo++;

    numberOne += numberTwo;

    System.out.println("numberOne += numberTwo (equivalent to #1 + #2): " + numberOne);

    System.out.print("\n");

    String numOneString = Short.toString(numberOne);
    String numTwoString = Short.toString(numberTwo);

    System.out.println(
        "Result of numOneString.compareTo(numTwoString)" + numOneString.compareTo(numTwoString));
  }
}
