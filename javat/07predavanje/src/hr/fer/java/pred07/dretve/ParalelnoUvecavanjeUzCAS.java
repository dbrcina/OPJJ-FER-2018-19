package hr.fer.java.pred07.dretve;

import java.util.concurrent.atomic.AtomicInteger;

public class ParalelnoUvecavanjeUzCAS {

	// volatile - ne cachera se
	private static AtomicInteger brojac = new AtomicInteger();

	public static void main(String[] args) {
		PosaoDretve posao = new PosaoDretve(500_000);

		final int BROJ_RADNIKA = 5;

		Thread[] radnici = new Thread[BROJ_RADNIKA];
		for (int i = 0; i < radnici.length; i++) {
			radnici[i] = new Thread(posao, "radnik " + (i + 1));
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
		System.out.printf("Rezultat je %d.%n", brojac.get());
	}

	private static class PosaoDretve implements Runnable {
		private int brojUvecavanja;

		public PosaoDretve(int brojUvecavanja) {
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for (int i = 0; i < brojUvecavanja; i++) {
				// brojac++;
				while (true) {
					int staro = brojac.get();
					int novo = staro + 1;
					if (brojac.compareAndSet(staro, novo)) {
						break;
					}
				}
			}
		}
	}
}
