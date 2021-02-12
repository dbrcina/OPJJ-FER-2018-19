package hr.fer.java.pred07.dretve;

public class ParalelnoUvecavanjeUzMutex {

	// volatile - ne cachera se
	private volatile static int brojac;

	public static void main(String[] args) {
		Object mutex = new Object();
		PosaoDretve posao = new PosaoDretve(mutex, 500_000);

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
		System.out.printf("Rezultat je %d.%n", brojac);
	}

	private static class PosaoDretve implements Runnable {
		private int brojUvecavanja;
		private Object mutex;

		public PosaoDretve(Object mutex, int brojUvecavanja) {
			this.mutex = mutex;
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for (int i = 0; i < brojUvecavanja; i++) {
				synchronized (mutex) {
					brojac++;
				}

			}
		}
	}
}
