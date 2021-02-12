package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that simulates a simple binary tree and it's properties.
 * 
 * @author Darijo Brčina
 *
 */
public class UniqueNumbers {
	/**
	 * A structure that represents the common node in a binary tree. A node has it's
	 * int type value and reference to it's left and right node.
	 *
	 */
	public static class TreeNode {
		/**
		 * A reference on the left node.
		 */
		TreeNode left;
		/**
		 * A reference on the right node.
		 */
		TreeNode right;
		int value;
	}

	public static void main(String[] args) {
		TreeNode head = null;

		try (Scanner sc = new Scanner(System.in)) {
			head = fillBinaryTree(head, sc);
		}

		if (head != null) {
			System.out.print("Ispis od najmanjeg: ");
			writeTreeInOrder(head);
			System.out.printf("%n");
			System.out.print("Ispis od najvećeg: ");
			writeTreeOutOrder(head);
		}
	}

	/**
	 * A method that prints binary tree in reversed order, from rith through head to
	 * left.
	 * 
	 * @param head a variable that represents the main head of the binary tree.
	 */
	private static void writeTreeOutOrder(TreeNode head) {
		if (head == null) {
			return;
		}
		writeTreeOutOrder(head.right);
		System.out.print(head.value + " ");
		writeTreeOutOrder(head.left);
	}

	/**
	 * A method which prints binary tree inorder, from left through head to right.
	 * 
	 * @param head a variable that represents the main head of the binary tree.
	 */
	private static void writeTreeInOrder(TreeNode head) {
		if (head == null) {
			return;
		}
		writeTreeInOrder(head.left);
		System.out.print(head.value + " ");
		writeTreeInOrder(head.right);

	}

	/**
	 * A method which user uses to fill up a binary tree through keyboard.
	 * 
	 * @param head a variable that represents the main head of the binary tree.
	 * @param sc
	 * @return the main head of the new binary tree.
	 */
	private static TreeNode fillBinaryTree(TreeNode head, Scanner sc) {
		System.out.print("Unesite broj > ");
		while (sc.hasNext()) {
			String token = sc.next();
			if (token.toLowerCase().equals("kraj")) {
				break;
			}
			try {
				int number = Integer.parseInt(token);
				if (containsValue(head, number)) {
					System.out.println("Broj " + number + " već postoji. Preskačem.");
				} else {
					head = addNode(head, number);
					System.out.println("Dodano.");
				}
			} catch (NumberFormatException ex) {
				System.out.println("'" + token + "' nije cijeli broj!");
			}
			System.out.print("Unesite broj > ");
		}
		return head;
	}

	/**
	 * A method that adds the specified value to the binary tree only if the value
	 * does not already exists.
	 * 
	 * @param head  a variable that represents main head of the binary tree.
	 * @param value an int type variable
	 * @return the main head of the new binary tree.
	 */
	static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
			return head;
		}
		if (!containsValue(head, value)) {
			if (head.value < value) {
				head.right = addNode(head.right, value);
			} else {
				head.left = addNode(head.left, value);
			}
		}
		return head;
	}

	/**
	 * A method which checks whether given value already exists in the binary tree.
	 * 
	 * @param head  a variable that represents main head of the binary tree.
	 * @param value an int type variable
	 * @return true if the value exists or false if it does not.
	 */
	static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		if (head.value == value) {
			return true;
		}
		if (head.value > value) {
			return containsValue(head.left, value);
		} else {
			return containsValue(head.right, value);
		}
	}

	/**
	 * A method that calculates binary tree size.
	 * 
	 * @param head a variable that represents main head of the binary tree.
	 * @return tree size as an int value.
	 */
	static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}
		return 1 + treeSize(head.left) + treeSize(head.right);
	}
}
