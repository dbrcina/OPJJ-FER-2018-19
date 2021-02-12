package hr.fer.zemris.java.pred02;

public class Primjer2 {

	public static void main(String[] args) {
		Pravokutnik p = new Pravokutnik("L1", 10, 10, 30, 10);
		
		System.out.println(p.getIme());
		
		System.out.printf("Površina lika je: %f.%n", p.getPovrsina());
		
		GeometrijskiLik l = p;
		
		System.out.printf("Površina lika je: %f.%n", l.getPovrsina()); // gleda se na pravokutnik kroz naočale geometrijskog lika, ali se metoda poziva iz tablice pravokutnika.. 
	}
}
