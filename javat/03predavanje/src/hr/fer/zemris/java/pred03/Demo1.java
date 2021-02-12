package hr.fer.zemris.java.pred03;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demo1 {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = { new Pravokutnik(10, 10, 20, 30), new Krug(50, 50, 10),
				new Pravokutnik(80, 10, 10, 30) };
		
		Slika slika = new Slika(100, 100);
		
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		slika.nacrtajSliku(System.out);
		PrikaznikSlike.prikaziSliku(slika, 4);
	}
	
}
