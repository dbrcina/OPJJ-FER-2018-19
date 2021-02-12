package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.*;

/**
 * Illustration of some generic methods for collections.
 * 
 * @author dbrcina
 *
 */
public class Demo {

	public static void main(String[] args) {

		List<Integer> l1 = new ArrayIndexedCollection<>();
		l1.add(3);
		l1.add(10);
		l1.add(12);

		ElementsGetter<Integer> getter = l1.createElementsGetter();
		System.out.println("Ispis za ArrayIndexedCollection:"); // 3 10 12
		while (getter.hasNextElement()) {
			System.out.println(getter.getNextElement());
		}
		
		////////////////////////////////////////////////////////////////
		System.out.println("------------------------------------------");

		List<Integer> l2 = new LinkedListIndexedCollection<>(l1);
		l2.insert(50, 0);
		l2.remove(1);

		System.out.println("Ispis za LinkedListIndexedCollection:"); // 50 10 12
		ElementsGetter<Integer> getter2 = l2.createElementsGetter();
		while (getter2.hasNextElement()) {
			System.out.println(getter2.getNextElement());
		}
		
		////////////////////////////////////////////////////////////////
		System.out.println("------------------------------------------");

		List<String> l3 = new ArrayIndexedCollection<>(3);
		l3.add("Mia");
		l3.add("Jasna");
		l3.add("Iva");
		l3.add("Petra");

		List<String> l4 = new LinkedListIndexedCollection<>();
		l4.addAllSatisfying(l3, name -> name.length() == 3);

		System.out.println("Ispis za LinkedListIndexedCollection...sva imena sa duljinom 3:"); // Mia Iva
		l4.forEach(System.out::println);
		
		////////////////////////////////////////////////////////////////
		System.out.println("------------------------------------------");
		
		ObjectStack<Integer> stack = new ObjectStack<>();

		stack.push(10);
		stack.push(30);
		
		try {
			for (int i = 0; i < 3; i++) {
				System.out.println(stack.pop());
			}
		} catch (EmptyStackException e) {
			System.out.println(e.getMessage());
		}
	}
}
