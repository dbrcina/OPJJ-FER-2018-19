package hr.fer.zemris.java.pred03;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik { // postoji defaultni konstruktor ali se ne može instancirati novi primjerak abstraktne klase

	public abstract boolean sadrziTocku(int x, int y);

	public void popuniLik(Slika slika) {
		int sirina = slika.getSirina(); // može se i u for petlji također deklarirat
		int visina = slika.getVisina();
		
		for (int y = 0;  y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}