package hr.fer.zemris.java.pred03;

public class Demo2 {

	public interface Processor {
		void process(double value, double transformValue);
	}

	public static class ispisiSamoVrijednost implements Processor {
		@Override
		public void process(double value, double transformValue) {
			System.out.println(transformValue);

		}
	}

	interface Transformer {
		double transformer(double value);
	}

	public static class Add3Transformer implements Transformer {
		@Override
		public double transformer(double value) {
			return value + 3;
		}
	}

	public static class SquareTransformer implements Transformer {
		@Override
		public double transformer(double value) {
			return value * value;
		}
	}

	public static void main(String[] args) {
		double[] unos = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		String operacija = "kvadriraj";

		if (!operacija.equals("dodaj3") && !operacija.equals("kvadriraj")) {
			System.out.println("Dragi korisniče,....");
			return;
		}

		Transformer t = null;
		if (operacija.equals("dodaj3")) {
			t = new Add3Transformer();
		} else {
			t = new SquareTransformer();
		}

		@SuppressWarnings("unused")
		Processor p = new ispisiSamoVrijednost();

		Processor p2 = new Processor() {
			@Override
			public void process(double value, double transformValue) {
				System.out.printf("%f -> %f%n", value, transformValue);
			}
		};
		
		obradiBrojeve(unos, t, p2);
	}

	public static void obradiBrojeve(double[] unos, Transformer t, Processor p) {
		double[] rezultat = new double[unos.length];
		for (int i = 0; i < rezultat.length; i++) {
			rezultat[i] = t.transformer(unos[i]);
		}

		for (int i = 0; i < rezultat.length; i++) {
			p.process(unos[i], rezultat[i]);
		}
	}
}
