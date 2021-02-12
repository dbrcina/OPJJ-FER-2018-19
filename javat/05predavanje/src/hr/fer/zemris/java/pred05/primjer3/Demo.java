package hr.fer.zemris.java.pred05.primjer3;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		Comparator<Zaposlenik> comp = (z1, z2) -> -z1.getSifra().compareTo(z2.getSifra());
		
		Set<Zaposlenik> set = Baza.napuni(new TreeSet<>(comp), Zaposlenik::new);
		set.forEach(System.out::println);
	}
}
