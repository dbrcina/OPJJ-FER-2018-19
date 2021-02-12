package hr.fer.zemris.java.pred06;

import java.io.File;

public class StabloBolje {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dragi korisiče...");
			return;
		}

		File staza = new File(args[0]);

		Obilazak.obidi(staza, new IspisStabla());
	}

	private static class IspisStabla implements Obrada {
		private int razina;
		
		@Override
		public void ulazimUDirektorij(File dir) {
			System.out.println(" ".repeat(razina * 2) + (razina == 0 ? dir.getAbsolutePath() : dir.getName()));
			razina++;
		}

		@Override
		public void izlazimIzDirektorija(File dir) {
			razina--;
		}

		@Override
		public void imamDatoteku(File file) {
			System.out.println(" ".repeat(razina * 2) + file.getName());
			
		}
		
	}

}
