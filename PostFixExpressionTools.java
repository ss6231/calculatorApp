package calculatorApp;
import java.util.Scanner;

/**
 * 
 * @author SanaSheikh
 * This class keeps all the methods needed for the postfix expression: 1) converting to postfix and 2) evaluating the
 * postfix expression 
 *
 */
public class PostFixExpressionTools {
	private static Scanner infix;
	private static String postfix;
	private static String operator;
	private static Scanner postfixScanner;
	private static Scanner revScanner;
	private static Integer operand1;
	private static Integer operand2;
	private static Integer value;
	private static Integer result;
	
	//constructor is private because an instance of this class is not necessary 
	private PostFixExpressionTools () {
	}
	/**
	 * 
	 * @param exp - this is the infix expression as a String
	 * @return - returns the postfix version of the given infix expression 
	 * @throws PostFixException - if there are invalid operators
	 */
	public static String toPostFix (String exp) throws PostFixException {
		//this stack will hold the operators in the expression
		Stack<String> stack = new Stack<String>();
		infix = new Scanner (exp);
		//postfix represents the final postfix expression as a String
		postfix = "";
		while (infix.hasNext()) {
			//token is an operand, append to postfix expression
			if (infix.hasNextInt()) {
				postfix += infix.nextInt() + " ";
			}
			//else, token should be an operator 
			else {
				operator = infix.next();
				//token is left brace, push to stack 
				if (operator.equals("(")) {
					stack.push (operator);
				}
				//token is an operator
				//while stack is not empty and the top of the stack has greater or equal precedence, pop stack and
				//append to postfix String expression 
				else if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("x") 
						|| operator.equals("*") || operator.equals("^")) {
					if (!stack.isEmpty()) {
						while (higherPrecedence (stack.peek(), operator)){
							postfix += stack.pop() + " ";
							//when the stack becomes empty, break the loop and return to the next token in the infix
							//expression 
							if (stack.isEmpty()) {
								break;
							} //end if
						}//end while
					}//end if
					stack.push (operator);
				}//end else if 
				else if (operator.equals(")")) {
					while (!stack.isEmpty()) {
						//pop the stack if top of the stack is not a matching left brace and append 
						//to postfix String expression 
						if (!stack.peek().equals("(")) {
							postfix += stack.pop() + " ";
						}
						//if you have a matching left brace, then discard the left brace 
						else {
							stack.pop();
							break;
						}//end else
					}//end while
				}//end else if 
				//if an appropriate operator does not match, then it is automatically invalid
				//throw an exception 
				else {
					throw new PostFixException (operator + " is an invalid operator");
				}//end else 
			}//end outermost else statement
		}//end infix traversing while loop

		//now pop the stack and append to postfix expression while stack is not empty 
		while (!stack.isEmpty()) {
			postfix += stack.pop() + " ";
		}
		//return final postfix String expression 
		return postfix;
	}
	/**
	 * 
	 * @param op1 - String operator at the top of the stack
	 * @param op2 - String operator that is the current token in the infix expression
	 * @return - returns boolean value if top of the stack is greater than or equal to current token 
	 */
	public static boolean higherPrecedence (String op1, String op2) {
		//if top of stack is + or -, it will only be >= if op2 is also + or - 
		if (op1.equals ("+") || op1.equals ("-")) {
			return (op2.equals("+") || op2.equals("-"));
		}
		//if top of stack is x, *, or /, it will only be >= when op2 is also equal to: x, *, /, +, and -
		//another way of saying this is that op2 is not equal to ^ symbol
		else if (op1.equals ("x") || op1.equals ("*") || op1.equals("/")) {
			return (!op2.equals("^"));
		}
		//this will always be greater than or equal to the rest of the operators
		else if (op1.equals ("^")) {
			return true;
		}
		//for left braces, which are disregarded in operator precedence 
		else {
			return false;	
		}
	}
	/**
	 * 
	 * @param exp - String postfix expression 
	 * @return - return Integer value of the expression
	 * @throws PostFixException - throw exception if invalid operator or not enough operands or dividing by zero 
	 */
	public static Integer evaluatePo (String exp) throws PostFixException {
		//create new stack of Integer to store operands of the expression as well as the results of the operators 
		Stack<Integer> stack = new Stack<Integer>();
		postfixScanner = new Scanner (exp);
		Integer result = 0;
		while (postfixScanner.hasNext()) {
			//if token is a number, push it to the stack 
			if (postfixScanner.hasNextInt()) {
				int operand = postfixScanner.nextInt();
				stack.push(operand);
			}
			else {
				//if the stack is empty at this point, there are not enough operators - throw exception 
				if (stack.isEmpty ()) {
					throw new PostFixException ("Invalid: Too many operators for: " + exp );
				}
				String operator = postfixScanner.next();
				//if the token is equal to any of the valid operators, pop the top of the stack 
				if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("x") 
						|| operator.equals("*") || operator.equals("^")) {
					Integer operand2 = stack.pop();
					//if the stack is empty after this pop, there are not enough operands 
					if (stack.isEmpty()) {
						throw new PostFixException ("Invalid: Not enough operands for: " + exp);
					}
					//now pop for the next operands and perform the appropriate arithmetic evaluations 
					Integer operand1 = stack.pop();
					if (operator.equals("+")) {
						result = operand1 + operand2;
					}
					else if (operator.equals ("-")) {
						result = operand1 - operand2;
					}
					else if (operator.equals("/")) {
						//cannot divide by zero 
						if (operand2.equals(0)) {
							throw new PostFixException ("Invalid: Cannot divide by zero");
						}
						result = operand1 / operand2;
					}
					else if (operator.equals("x") || operator.equals("*")) {
						result = operand1*operand2;
					}
					else if (operator.equals("^")) {
						result = (int) Math.pow(operand1, operand2);
					}
					//finally push the result into the stack 
					stack.push (result);
				}//end outer if statement of operators 
			}//end outer else 
		}//end while 
		result = stack.pop();
		//if the stack is not empty after popping the result, there are too many operands 
		if (!stack.isEmpty()) {
			throw new PostFixException ("Invalid: Expression has too many operands for: " + exp);
		}
		//return final result 
		return result;
	}
	
}
