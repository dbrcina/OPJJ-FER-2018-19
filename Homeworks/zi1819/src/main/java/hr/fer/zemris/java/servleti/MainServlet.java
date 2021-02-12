package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

	private String imgDir = "/WEB-INF/images";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path path = Paths.get(req.getServletContext().getRealPath(imgDir));

		if (!Files.exists(path)) {
			Files.createDirectory(path);
		}

		MyVisitor visitor = new MyVisitor();
		Files.walkFileTree(path, visitor);

		List<Path> paths = visitor.getPaths();
		List<String> imena = paths.stream().map(p -> p.getFileName().toString()).collect(Collectors.toList());
		imena.sort(String::compareTo);

		req.setAttribute("imena", imena);
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}

	private static class MyVisitor extends SimpleFileVisitor<Path> {
		private List<Path> paths = new ArrayList<>();

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			paths.add(file);
			return FileVisitResult.CONTINUE;
		}

		public List<Path> getPaths() {
			return paths;
		}
	}
}
