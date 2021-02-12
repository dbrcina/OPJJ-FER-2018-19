package hr.fer.zemris.java.pred02;

public class Primjer1 {

	public static void main(String[] args) {
		GeometrijskiLik lik1 = new GeometrijskiLik("L1");
		GeometrijskiLik lik2 = new GeometrijskiLik("L2");
		GeometrijskiLik lik3 = lik1;
		
		System.out.printf("Ime prvog like je %s.%n", lik1.getIme()); // GeometrijskiLik::getIme(lik1);...ovo se zapravo događa, strojni kod metode getIme prima this..
		System.out.printf("Ime drugog like je %s.%n", lik2.getIme()); // GeometrijskiLik::getIme(lik2);...ovo se zapravo događa, strojni kod metode getIme prima this..
		System.out.printf("Ime trećeg like je %s.%n", lik3.getIme()); // GeometrijskiLik::getIme(lik3);...ovo se zapravo događa, strojni kod metode getIme prima this..
	}
}
