package calculatorApp;
import java.util.Scanner;
import java.io.*;

/**
 * @author SanaSheikh
 * /**
 * This program takes an input file of infix expressions and creates a new file displaying corresponding postfix
 * expressions and answers. If a third input file is given, the program will display corresponding prefix expressions
 * and answers
 * @param args - user input. The program will print an error and exit the program if no files are given. If one input
 * file is given and it exists, it will create an output file on its own for the postfix results. The prefix results
 * will only be printed when 3 arguments are given on the command line.
 */

public class ConsoleCalculator {
	public static void main(String[] args) {
		Scanner infix = null;
		PrintWriter prefixResult = null;
		PrintWriter postfixResult = null;
		File prefixFile = null;
		File postfixFile = null;
		boolean thirdFile = false;
		
		//no arguments are given 
		if (args.length < 1) {
			System.err.println ("ERROR: You did not enter enough files");
			System.exit(1);
		}
		//infix file does not exist
		if (!new File (args[0]).exists()) {
			System.err.println ("ERROR: File does not exist");
			System.exit(3);
		}
		//when only the input file is given, create a postfix results file
		if (args.length == 1) {
			postfixFile = new File ("postfixResults.txt");
		}
		else if (args.length == 2) {
			postfixFile = new File (args[1]);
		}
		//create a prefix results file if three arguments are given 
		else if (args.length == 3) {
			postfixFile = new File (args[1]);
			prefixFile = new File (args[2]);
			thirdFile = true;
		}
		//attempt to create scanner and respective printwriters
		try {
			infix = new Scanner (new File (args[0]));
			postfixResult = new PrintWriter (postfixFile);
			if (thirdFile) {
				prefixResult = new PrintWriter (prefixFile);
			}
		}
		catch (FileNotFoundException ex) {
			System.err.println (ex.toString());
		}
		while (infix.hasNext()) {
			String expression = infix.nextLine();
			try {
				//call postfix method to convert to postfix form 
				String postfix = PostFixExpressionTools.toPostFix (expression);
				//evaluate given postfix form and display on the output file 
				Integer resultPo = PostFixExpressionTools.evaluatePo(postfix);
				postfixResult.println (postfix + " = " + resultPo);	
			}
			//catch postfix exception and print invalid to output file
			catch (PostFixException ex) {
				System.err.println (ex.toString());
				postfixResult.println ("Invalid");
			}
			try {
				if (thirdFile) {
					//call prefix method only if third argument is given. conver to prefix form
					String prefix = PrefixExpressionTools.toPrefix(expression);
					//evaluate give prefix form and display on the output file 
					Integer result = PrefixExpressionTools.evaluatePre(prefix);
					prefixResult.println (prefix + " = " + result);
				}
			}
			//catch prefix exception and print invalid to output file 
			catch (PreFixException ex) {
				System.err.println (ex.toString());
				prefixResult.println ("Invalid");
			}
			//catch any overlooked exceptions 
			catch (Exception ex) {
				System.err.println (ex.toString());
			}
		}
		//close postfix file
		if (postfixResult != null) {
			postfixResult.close();
			System.out.println ("Postfix result file is closed");
		}
		//close prefix file
		if (prefixResult != null) {
			prefixResult.close();
			System.out.println ("Prefix result file is closed");
		}
		
	}

}
