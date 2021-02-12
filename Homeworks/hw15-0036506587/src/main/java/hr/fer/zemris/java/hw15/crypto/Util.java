package hr.fer.zemris.java.hw15.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class which provides method for encryption of some password.
 * 
 * @author dbrcina.
 *
 */
public class Util {

	/**
	 * Encrypts provided <code>password</code> into hex-encoded hash.
	 * 
	 * @param password password.
	 * @return encoded password.
	 */
	public static String encrypt(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
		}
		byte[] encrypted = md.digest(password.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encrypted);
	}

	/**
	 * Converts an byte array into hex-decimal representation.
	 * 
	 * @param encrypted an array.
	 * @return hex decimal representation.
	 */
	private static String bytesToHex(byte[] encrypted) {
		StringBuilder sb = new StringBuilder();
		for (byte b : encrypted) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
