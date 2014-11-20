package calculatorApp;
import java.util.Scanner;

/**
 * 
 * @author SanaSheikh
 *This class contains all the methods needed for: 1) the conversion into prefix and 2) the evaluation of the given 
 *prefix form 
 */
public class PrefixExpressionTools {
	private static Scanner infix;
	private static String prefix;
	private static String revString;
	private static Scanner revScanner;
	private static Integer operand1;
	private static Integer operand2;
	private static String operator;
	private static Integer value;
	private static Integer result;
	
	private PrefixExpressionTools () {
	}
	
	/**
	 * 
	 * @param revString - this is the String infix expression
	 * @return - returns the String representation of the prefix form 
	 * @throws PreFixException - throws exception with invalid operators/operands 
	 */
	public static String toPrefix (String exp) throws PreFixException {
		//create new String stack to store operators 
		Stack<String> stack = new Stack<String> ();
		//convert the infix expression to postfix
		String postfix = toPostfix (exp);
		//reverse the postfix expression so that it will return a prefix expression with inverted parenthesis
		String reverse = reverse (postfix);
		String x = "";
		//revert the inverted parenthesis and concatenate
		for (int i = 0; i < reverse.length(); i++) {
			if (reverse.charAt(i)==(')')) {
				x+='(' + " ";
			}
			else if (reverse.charAt(i)==('(')) {
				x+=')' + " ";
			}
			else {
				x+=reverse.charAt(i);
			}
		}
		//return final prefix expression 
		return x;
	}
	
	/**
	 * 
	 * @param expression - this is the prefix String expression
	 * @return - return integer value of the expression 
	 * @throws PreFixException - throw exception with invalid operators/operands/dividing by zero 
	 */
	public static Integer evaluatePre (String expression) throws PreFixException  {
		//create new Integer to store the operands and results 
		Stack<Integer> stack = new Stack<Integer>();
		//reverse the prefix expression so it can be read from "right to left"
		String revString = reverse(expression);
		
		revScanner = new Scanner (revString);
			while (revScanner.hasNext()) {
				if (revScanner.hasNextInt()) {
					//push the operand to the stack 
					value = revScanner.nextInt();
					stack.push(value);
				}
				else {
					//pop stack if token is an operator instead of an operand 
					operator = revScanner.next();
					operand2 = stack.pop();
					//if stack is empty after first pop, there are not enough operators 
					if (stack.isEmpty()) {
						throw new PreFixException ("Invalid: Not enough operators in: " + expression);
					}
					//pop the second operand  and perform appropriate operations on the two operands 
					operand1 = stack.pop();
					if (operator.equals("+")) {
						result = operand1 + operand2;
					}
					else if (operator.equals("-")) {
						result = operand1-operand2;
					}
					else if (operator.equals("/")) {
						//throw exception if attempting to divide by zero 
						if (operand2.equals(0)){
							throw new PreFixException ("Invalid: Cannot divide by zero");
						}
						result = operand1/operand2;
						
					}
					else if (operator.equals("*") || operator.equals("x")) {
						result = operand1*operand2;
					}
					else if (operator.equals("^")) {
						result = (int) Math.pow(operand1, operand2);
					}
					//if there is no matching operation, then it is invalid - throw an exception 
					else {
						throw new PreFixException ("Invalid: " + operator + " is not a number or operator");
					}
					//push the result into the stack 
					stack.push(result); //result should be only element in the stack now 
				}
			}
		//outside of the while loop
		result = stack.pop();
		if (!stack.isEmpty()) {
			throw new PreFixException ("Invalid: Not enough operands in " + expression);
		}
		//return final result 
		return result;
		
	}
	
	/**
	 * 
	 * @param expression - given String expression (either infix or prefix) 
	 * @return - String expression in reverse order 
	 */
	private static String reverse (String expression) {
		//inverts the prefix expression so it can be read from "right to left"
		String reverseInfix = " ";
		Scanner x = new Scanner (expression);
		while (x.hasNext()) {
			reverseInfix = x.next() + " " + reverseInfix;
		}
		return reverseInfix;
	}
	/**
	 * 
	 * @param exp - String infix expression
	 * @return - returns String postfix expression
	 * @throws PreFixException - throws exception if an operator is invalid
	 * This method is identical the toPostfix method in PostFixExpressionTools class. I separated it because
	 * this should throw a PreFixException rather than a PostFixException
	 */
	public static String toPostfix (String exp) throws PreFixException {
		Stack<String> stack = new Stack<String>();
		infix = new Scanner (exp);
		String postfix = "";
		while (infix.hasNext()) {
			//token is an operand
			if (infix.hasNextInt()) {
				postfix += infix.nextInt() + " ";
			}
			//exp: 15 5
			//stack: /
			else {
				operator = infix.next();
				//token is left brace
				if (operator.equals("(")) {
					stack.push (operator);
				}
				//token is an operator
				else if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("x") 
						|| operator.equals("*") || operator.equals("^")) {
					if (!stack.isEmpty()) {
						while (PostFixExpressionTools.higherPrecedence (stack.peek(), operator)){
							postfix += stack.pop() + " ";
							if (stack.isEmpty()) {
								break;
							}
						}
					}
					stack.push (operator);
				}
				else if (operator.equals(")")) {
					while (!stack.isEmpty()) {
						if (!stack.peek().equals("(")) {
							postfix += stack.pop() + " ";
						}
						else {
							stack.pop();
							break;
						}
					}
				}
				else {
					throw new PreFixException (operator + " is an invalid operator");
				}
			}
		}
		while (!stack.isEmpty()) {
			postfix += stack.pop() + " ";
		}
		return postfix;
	}
}
