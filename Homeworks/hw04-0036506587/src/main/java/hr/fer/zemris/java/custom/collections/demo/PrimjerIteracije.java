package hr.fer.zemris.java.custom.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

public class PrimjerIteracije {

	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println("---------------------------------------------------");

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}

		System.out.println("---------------------------------------------------");

		// uklanjanje ocjene za Ivanu na korektan način (nema iznimke)
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}

		// ovdje se događa IllegalStateException jer se pokušava dva puta obrisati
		// element.
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}

		// ovdje se događa ConcurrentModificationException jer se kolekcija mijenja
		// tokom iteriranja od strane korisnika a ne kroz iterator
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				examMarks.remove("Ivana");
//			}
//		}

		// isprintaju se svi elementi i onda se svaki od njih obriše
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter1 = examMarks.iterator();
		while (iter1.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter1.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter1.remove();
		}
		System.out.printf("%nVeličina: %d%n", examMarks.size());
	}

}
