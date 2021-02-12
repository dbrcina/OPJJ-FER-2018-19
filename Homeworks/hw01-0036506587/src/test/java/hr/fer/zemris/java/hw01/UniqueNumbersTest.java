package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	@Test
	public void testAddNode() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 10);
		head = UniqueNumbers.addNode(head, 50);
		assertTrue(head.value == 21);
		assertTrue(head.left.value == 10);
		assertTrue(head.right.value == 50);
	}
	
	@Test
	public void sizeOfEmptyBinaryTree() {
		TreeNode head = null;
		assertEquals(0, UniqueNumbers.treeSize(head));
	}

	@Test
	public void sifeOfNotEmptyBinaryTree() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 76); // number 76 is already added
		head = UniqueNumbers.addNode(head, 35);
		// size is 4 because we tried to add the number that already exists
		assertEquals(4, UniqueNumbers.treeSize(head));
	}

	@Test
	public void containsValueEmptyBinaryTree() {
		TreeNode head = null;
		assertEquals(false, UniqueNumbers.containsValue(head, 42));
	}

	@Test
	public void containsValueNotEmptyBinaryTree() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 72);
		head = UniqueNumbers.addNode(head, 21);
		assertEquals(true, UniqueNumbers.containsValue(head, 72));
		assertEquals(false, UniqueNumbers.containsValue(head, 100));
	}
}
