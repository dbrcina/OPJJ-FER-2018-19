package hr.fer.java.pred07.dretve;

public class ParalelnoUvecavanjeUzMutex2 {

	// volatile - ne cachera se
	private volatile static int brojac;

	public static void main(String[] args) {
//		PosaoDretve posao = new PosaoDretve(500_000);

		final int BROJ_RADNIKA = 5;

		Thread[] radnici = new Thread[BROJ_RADNIKA];
		for (int i = 0; i < radnici.length; i++) {
			radnici[i] = new Thread(new PosaoDretve(500_000), "radnik " + (i + 1));
		}
		for (Thread radnik : radnici) {
			radnik.start();
		}

//		Thread radnik = new Thread(posao, "radnik");
//		radnik.start();

		// zablokirati dretvu koja izvodi main dok radnik ne zavrsi
		for (Thread radnik : radnici) {
			while (true) {
				try {
					radnik.join();
				} catch (InterruptedException e) {
					continue;
				}
				break;
			}
		}
		System.out.printf("Rezultat je %d.%n", brojac);
	}

	private static class PosaoDretve implements Runnable {
		private int brojUvecavanja;

		public PosaoDretve(int brojUvecavanja) {
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for (int i = 0; i < brojUvecavanja; i++) {
				synchronized (this) {
					brojac++;
				}
			}
		}
	}
}
