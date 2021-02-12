import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		sc.close();

		List<String> inputList = new ArrayList<>();
		for (String s : line.split("")) {
			inputList.add(s);
		}

		boolean accepted = S(inputList);
		
		if (accepted && inputList.isEmpty()) {
			System.out.println("\nDA");
		} else {
			System.out.println("\nNE");
		}
	}

	private static boolean S(List<String> inputList) {
		System.out.print("S");
		if (inputList.isEmpty())
			return false;
		String s = inputList.remove(0);
		boolean accepted = false;
		if (s.equals("a")) {
			accepted = A(inputList);
			if (accepted) {
				accepted = B(inputList);
			}
		} else if (s.equals("b")) {
			accepted = B(inputList);
			if (accepted) {
				accepted = A(inputList);
			}
		}
		return accepted;
	}

	private static boolean A(List<String> inputList) {
		System.out.print("A");
		if (inputList.isEmpty())
			return false;
		String s = inputList.remove(0);
		if (s.equals("a"))
			return true;
		else if (s.equals("b"))
			return C(inputList);
		else {
			inputList.add(0, s);
			return false;
		}
	}

	private static boolean B(List<String> inputList) {
		System.out.print("B");
		if (inputList.isEmpty()) {
			return true;
		}
		String s1 = inputList.remove(0);
		boolean accepted = true;
		if (s1.equals("c") && !inputList.isEmpty()) {
			String s2 = inputList.remove(0);
			if (s2.equals("c") && !inputList.isEmpty()) {
				accepted = S(inputList);
				String s3 = inputList.remove(0);
				if (accepted && s3.equals("b")) {
					String s4 = inputList.remove(0);
					if (s4.equals("c")) {
						accepted = true;
					} else {
						inputList.add(0, s4);
					}
				} else {
					accepted = false;
					inputList.add(0, s3);
				}
			} else {
				accepted = false;
				inputList.add(0, s2);
			}
		} else {
			inputList.add(0, s1);
		}
		return accepted;
	}

	private static boolean C(List<String> inputList) {
		System.out.print("C");
		boolean accepted = A(inputList);
		if (accepted) {
			accepted = A(inputList);
		}
		return accepted;
	}
}
