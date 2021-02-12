package hr.fer.zemris.java.pred05.primjer5;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Comparator<Zaposlenik> comp = (z1, z2) -> {
			int r = z1.getPrezime().compareTo(z2.getPrezime());
			if (r != 0)
				return r;
			r = z1.getIme().compareTo(z2.getIme());
			if (r != 0)
				return r;
			return r = z1.getSifra().compareTo(z2.getSifra());
		};

		Comparator<Zaposlenik> comp2 = Zaposlenik.PO_PREZIMENU.reversed().thenComparing(Zaposlenik.PO_IMENU)
				.thenComparing(Zaposlenik.PO_SIFRI).reversed();

		Set<Zaposlenik> set = Baza.napuni(new TreeSet<>(comp2), Zaposlenik::new);
		set.forEach(System.out::println);
	}
}
