package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * Helper class for {@link Crypto} class. It provides two public static methods:
 * {@link #bytetohex(byte[])} and {@link #hextobyte(String)}.
 * 
 * @author dbrcina
 *
 */
public class Util {

	/**
	 * Utility method used for transforming {@link String} values into byte array.
	 * 
	 * @param keyText String value that needs to be transformerd.
	 * @return byte array representing given {@link String} value.
	 * @throws IllegalArgumentException if the length is not divisible by 2.
	 * @throws NullPointerException     if the {@code keyText} is {@code null}.
	 */
	public static byte[] hextobyte(String keyText) {
		if (Objects.requireNonNull(keyText).isEmpty()) {
			return new byte[0];
		}

		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Key's length is not divisible by 2!");
		}

		int len = keyText.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Utility method used for generating Hexadecimal {@link String} representation
	 * of given <code>byteArray</code>.
	 * 
	 * @param byteArray byte array.
	 * @return Hexadecimal {@link String}.
	 * @throws NullPointerException if the given {@code byteArray} is {@code null}.
	 */
	public static String bytetohex(byte[] byteArray) {
		Objects.requireNonNull(byteArray);
		StringBuilder sb = new StringBuilder();
		for (byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
