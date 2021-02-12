package hr.fer.zemris.java.pred04;

import java.util.Iterator;

public class Demo1 {

	public static void main(String[] args) {
		Kvartet<String> k1 = new Kvartet<>("Ana", "Jasna", "Ivana", "Petra");
		Kvartet<Integer> k2 = new Kvartet<>(5, 1, 4, 42);

		Iterator<String> it1 = k1.iterator();
		while (it1.hasNext()) {
			System.out.println(it1.next());
		}
		System.out.println("----------------");
		for (String elem : k1) {
			System.out.println(elem);
		}
		System.out.println("----------------");
		for (Integer i : k2) {
			System.out.println(i);
		}

		Iterator<String> it2 = k1.new IteratorImpl4();
	}
}
