package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo program for {@link PrimesCollection}.
 * 
 * @author dbrcina
 *
 */
public class PrimesDemo2 {

	/**
	 * Main entry of this program.
	 * 
	 * @param args argument given through command line.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
