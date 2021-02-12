package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo program for {@link PrimesCollection}.
 * 
 * @author dbrcina
 *
 */
public class PrimesDemo1 {

	/**
	 * Main entry of this program.
	 * 
	 * @param args argument given through command line.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}

}
