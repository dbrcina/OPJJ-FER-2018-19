package hr.fer.zemris.java.hw06.crypto;

import static hr.fer.zemris.java.hw06.crypto.Util.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Simple program that illustrates encrypting and decrypting some files.
 * Arguments are given through command line.
 * 
 * @author dbrcina
 *
 */
public class Crypto {

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 3) {
			System.out.println(
					"Number of arguments entered through command line are invalid! (expected 2 or 3)\nExiting..");
			return;
		}
		
		try (Scanner sc = new Scanner(System.in)) {

			switch (args[0].toLowerCase().trim()) {

			// case for checksha
			case "checksha":
				System.out.print(
						"Please provide expected sha-256 digest for " + Paths.get(args[1]).getFileName() + ":\n> ");
				// 2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598
				String expectedDigest = sc.nextLine().trim();
				checksha(Paths.get(args[1]), expectedDigest);
				break;

			// case for encrypt and decrypt
			// code is mostly the same..
			case "encrypt":
			case "decrypt":
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				// e52217e3ee213ef1ffdee3a192e2ac7e
				String keyText = sc.nextLine().trim();

				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				// 000102030405060708090a0b0c0d0e0f
				String ivText = sc.nextLine().trim();

				boolean encrypt = args[0].equals("encrypt") ? true : false;
				crypto(Paths.get(args[1]), Paths.get(args[2]), keyText, ivText, encrypt);
				break;
				
			// just in case...
			default:
				throw new IllegalArgumentException("Operation is invalid!");
			}
		}
	}

	/**
	 * Method used for testing whether input <code>expectedDigest</code> sha-256
	 * digest is equal to actual digest found through given <code>path</code>.
	 * Result is printed to {@link System#out}.
	 * 
	 * @param path           {@link Path} where file is.
	 * @param expectedDigest expected digest.
	 */
	private static void checksha(Path path, String expectedDigest) {
		try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[4096];
			while (true) {
				int read = is.read(buff);
				if (read < 0) {
					break;
				}
				md.update(buff, 0, read);
			}
			byte[] digestArray = md.digest();
			String actualDigest = Util.bytetohex(digestArray);
			if (actualDigest.equals(expectedDigest)) {
				System.out
						.println("Digesting completed. Digest of " + path.getFileName() + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + path.getFileName()
						+ " does not match the expected digest. Digest was: " + actualDigest);
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used for encrypting/decrypting files as determined by flag
	 * <code>encrypt</code>. If <code>encrypt</code> value is <code>true</code>,
	 * encryption is done, otherwise decryption. Internally, instance of
	 * {@link Cipher} is used and it's methods.
	 * 
	 * @param from    {@link Path} from where data is encrypted/decrypted.
	 * @param to      {@link Path} to where data is encrypted/decrypted.
	 * @param keyText password.
	 * @param ivText  initialization vector.
	 * @param encrypt boolean flag.
	 */
	private static void crypto(Path from, Path to, String keyText, String ivText, boolean encrypt) {
		SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		try (InputStream is = Files.newInputStream(from, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(to, StandardOpenOption.CREATE)) {

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			byte[] inputBytes = new byte[4096];

			while (true) {
				int read = is.read(inputBytes);
				if (read < 0) {
					break;
				}
				os.write(cipher.update(inputBytes, 0, read));
			}

			os.write(cipher.doFinal());

			System.out.println((encrypt == true ? "Encryption" : "Decryption") + " completed. Generated file "
					+ to.getFileName() + " based on " + from.getFileName());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IOException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}
	}
}