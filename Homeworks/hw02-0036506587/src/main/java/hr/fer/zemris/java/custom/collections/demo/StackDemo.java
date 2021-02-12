package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A program that illustrates generic methods when working with stack.
 * Parameters for this application are accepted through command line. It is
 * expected to be only one parameter(expression). Expression must be in postfix
 * representation and you can assume that everything is separeted by one (or
 * more) spaces. All operators work with and produce integer result.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class StackDemo {

	private static final String VALID_EXPRESSIONS = "[+-/*%]";
	/**
	 * An entry point of this application.
	 * 
	 * @param args arguments accepted through command line.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			errorPrint("Program očekuje samo jedan argument!");
		}
		if (args[0].isEmpty()) {
			errorPrint("Uneseno je nešto što nije niti broj niti operacija!");
		}
		String[] expression = args[0].split("\\s+"); // split expression by whitespaces
		for (int i = 0; i < expression.length; i++) {
			if (!expression[i].matches("-*\\d") && !expression[i].matches(VALID_EXPRESSIONS)) { // \d for digit number
				errorPrint("Uneseno je nešto što nije niti broj niti operacija!");
			}
		}
		if (!checkValue(expression)) { // check whether expression is in postfix form
			errorPrint("Izraz nije validan! Broj operacija uvijek mora za jedan biti manje od broja operanda!");
		}
		ObjectStack stack = new ObjectStack();
		parsePostfixExp(stack, expression);

		if (stack.size() > 1) {
			errorPrint("Na stogu se još uvijek nalazi više od jednog argumenta!"
					+ "\nIzraz je dobro unesen ali broj operanada i operacija ne valja!");
		} else {
			System.out.println("Rješenje zadanog izraza je " + stack.pop() + ".");
		}
	}

	private static boolean checkValue(String[] expression) {
		int counter = 0;
		for (String s : expression) {
			if (s.matches(VALID_EXPRESSIONS)) {
				counter -= 2;
			}
			counter++;
		}
		return counter == 1 ? true : false;
	}

	/**
	 * A method that parses the input expression. It iterrates through
	 * {@code expression} and check if everything is compatible for operation.
	 * 
	 * @param stack      a reference to stack that is used for this application.
	 * @param expression an array filled with expression parts splitted by
	 *                   whitespaces.
	 */
	private static void parsePostfixExp(ObjectStack stack, String[] expression) {
		for (String elem : expression) {
			try {
				Integer number = Integer.parseInt(elem);
				stack.push(number);
			} catch (NumberFormatException numForEx) {
				if (elem.matches(VALID_EXPRESSIONS)) {
					if (stack.size() >= 2) {
						int secondNum = (int) stack.pop();
						int firstNum = (int) stack.pop();
						try {
							int result = doOperation(firstNum, secondNum, elem);
							stack.push(result);
						} catch (ArithmeticException aritEx) {
							errorPrint("Dogodilo se dijeljenje s nulom!");
						}
					} else {
						errorPrint("Izraz nije zadan u postfix notaciji!");
					}
				}
			}
		}
	}

	/**
	 * A method that does the {@code operation} with {@code firstNum} and
	 * {@code secondNum}.
	 * 
	 * @param firstNum  an int type value.
	 * @param secondNum an int type value.
	 * @param operation can be from [+,-,/,*,%].
	 * @return a result after the operation is done.
	 * @throws ArithmeticException is thrown when user tries to divide with zero.
	 */
	private static int doOperation(int firstNum, int secondNum, String operation) {
		int result;
		if (operation.equals("+")) {
			result = firstNum + secondNum;
		} else if (operation.equals("-")) {
			result = firstNum - secondNum;
		} else if (operation.equals("/")) {
			result = firstNum / secondNum;
		} else if (operation.equals("*")){
			result = firstNum * secondNum;
		} else {
			result = firstNum % secondNum;
		}
		return result;
	}

	/**
	 * Prints on {@link System.out} when something is wrong with input or with
	 * program. After printing, the program is terminated.
	 * 
	 * @param msg a message that will be printed.
	 */
	private static void errorPrint(String msg) {
		System.err.println(msg);
		System.exit(0);
	}
}
