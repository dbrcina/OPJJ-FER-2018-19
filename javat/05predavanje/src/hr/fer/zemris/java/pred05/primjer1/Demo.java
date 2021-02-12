package hr.fer.zemris.java.pred05.primjer1;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.pred05.zaposlenici.util.Baza;

public class Demo {

	public static void main(String[] args) {
		List<Zaposlenik> lista = Baza.napuni(new ArrayList<>(), Zaposlenik::new);
		
		// new IZaposlenikCreator<Zaposlenik>() {
		//	public Zaposlenik create(String sifra, String prezime, String ime) {
		//		return new Zaposlenik(sifra,prezime,ime)
		//	}
		
		// (sifra,prezime,ime) -> new Zaposlenik(sifra,prezime,ime)
		
		lista.forEach(System.out::println);
		
		Zaposlenik zap = new Zaposlenik("1", "Perić", "Pero");
		System.out.println(lista.contains(zap));
	}
	
}
