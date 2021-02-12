package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.crypto.Util;

public class UtilTest {

	@Test
	public void testFromNullToByteArray() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}

	@Test
	public void testInvalidLengthStringToByteArray() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01a"));
	}

	@Test
	public void testFromEmptyStringToByteArray() {
		byte[] data = Util.hextobyte("");
		assertTrue(data.length == 0);
	}

	@Test
	public void testFromHexToByteArray() {
		byte[] data = Util.hextobyte("01aE22");
		byte[] correctData = { 1, -82, 34 };
		assertTrue(data.length == 3);
		assertArrayEquals(correctData, data);
	}

	@Test
	public void testFromNullByteArrayToString() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}

	@Test
	public void testFromByteArrayToString() {
		byte[] data = { 1, -82, 34 };
		String hex = Util.bytetohex(data);
		assertEquals("01ae22", hex);
	}
}
