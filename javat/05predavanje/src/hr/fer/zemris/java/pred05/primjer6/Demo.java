package hr.fer.zemris.java.pred05.primjer6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		List<Zaposlenik> lista = Baza.napuni(new ArrayList<>(), Zaposlenik::new);
		
		Set<String> preživjelaPrezimena = lista.stream()
			.filter(z -> z.getSifra().compareTo("2") >= 0)
			.map(z -> z.getPrezime())
			.filter(pr -> !pr.startsWith("A"))
			.collect(Collectors.toSet());
		
		// mapa zaposlenika, ključ je šifra
		Map<String, Zaposlenik> mapaZaposlenika = lista.stream()
				.collect(Collectors.toMap(Zaposlenik::getSifra, UnaryOperator.identity())); // z -> z
			
		// broj zaposlenika s imenom većim od 4 znaka
		long brojZaposlenika = lista.stream()
				.map(Zaposlenik::getIme)
				.filter(ime -> ime.length() > 4)
				.count();
			
		// suma duljina imena svih zaposlenika
		double sumaDuljinaImena = lista.stream()
				.mapToDouble(z -> z.getIme().length())
				.sum();
		
		// prosječna duljina svih imena
		OptionalDouble prosjecnaDuljinaImena = lista.stream()
				.mapToDouble(z -> z.getIme().length())
				.average();
		
		if (prosjecnaDuljinaImena.isPresent()) {
			System.out.println(prosjecnaDuljinaImena.getAsDouble());
		}
		
		preživjelaPrezimena.forEach(System.out::println);
	}
}
