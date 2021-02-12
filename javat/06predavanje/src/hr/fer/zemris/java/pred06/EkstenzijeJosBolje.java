package hr.fer.zemris.java.pred06;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EkstenzijeJosBolje {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dragi korisiče...");
			return;
		}

		File staza = new File(args[0]);

		var rezultat = new BrojaloEkstenzija();
		
		Obilazak.obidi(staza, rezultat);

		Comparator<Map.Entry<String, Integer>> poBrojevima = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
		Comparator<Map.Entry<String, Integer>> poEkstenzijama = (p1, p2) -> p1.getKey().compareTo(p2.getKey());
		
		List<Map.Entry<String, Integer>> lista = new ArrayList<>(rezultat.mapa.entrySet());
		
		lista.sort(poBrojevima.thenComparing(poEkstenzijama));
		
		for (Map.Entry<String, Integer> par : lista) {
			System.out.printf("%s => %d%n", par.getKey(), par.getValue());
		}
	}

	private static class BrojaloEkstenzija extends PraznaObrada {
		private Map<String,Integer> mapa = new HashMap<>();

		@Override
		public void imamDatoteku(File file) {
			String ekstenzija = odrediEkstenziju(file.getName());
			mapa.compute(ekstenzija, (fileEkstenzija, count) -> count == null ? 1 : count + 1);
			// mapa.merge(ekstenzija, 1, (v, i) -> v + i);
			
		}
		
		private static String odrediEkstenziju(String name) {
			int index = name.lastIndexOf('.');
			if (index <= 0) {
				return "";
			}
			return name.substring(index + 1).toLowerCase();
		}
		
	}

}