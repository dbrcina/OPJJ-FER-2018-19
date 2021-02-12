package hr.fer.zemris.java.pred05.primjer2;

import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		Set<Zaposlenik> set = Baza.napuni(new TreeSet<>(), Zaposlenik::new);
		
		set.forEach(System.out::println);
		
		Zaposlenik zap = new Zaposlenik("1", "Perić", "Pero");
		System.out.println(set.contains(zap));
	}
}
