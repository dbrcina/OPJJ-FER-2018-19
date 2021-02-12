package hr.fer.zemris.java.pred05.primjer4;

import java.util.HashMap;
import java.util.Map;
import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		Map<Zaposlenik, Double> mapa = Baza.napuni(new HashMap<>(), Zaposlenik::new);
		mapa.forEach((z, b) -> System.out.format("%s -> %f%n", z, b));
		
		Zaposlenik zap = new Zaposlenik("1", "Perić", "Pero");
		System.out.println("Bonus od Pere je: " + mapa.get(zap));
	}
}
