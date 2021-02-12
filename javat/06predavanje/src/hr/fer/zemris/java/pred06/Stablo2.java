package hr.fer.zemris.java.pred06;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Stablo2 {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dragi korisiče...");
			return;
		}
		
		Path path = Paths.get(args[0]);
	 
		try {
			Files.walkFileTree(path, new IspisStabla());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class IspisStabla implements FileVisitor<Path> {
		private int razina;
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(razina * 2) + (razina == 0 ? dir.toAbsolutePath() : dir.getFileName()));
			razina++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			razina--;
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(razina * 2) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}
}
