//package hr.fer.zemris.java.custom.scripting.demo;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import hr.fer.zemris.java.custom.scripting.elems.Element;
//import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
//import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
//import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
//import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
//import hr.fer.zemris.java.custom.scripting.nodes.Node;
//import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
//import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
//
///**
// * An application that simulates <i>Visitor design pattern</i> on instances of
// * {@link Node}.
// * 
// * <p>
// * Program takes one argument through command line, path to some file and prints
// * its content on {@link System#out}.
// * </p>
// * 
// * @author dbrcina
// *
// */
//public class TreeWriter {
//
//	/**
//	 * Main entry of this program.
//	 * 
//	 * @param args arguments given through command line.
//	 * @throws UnsupportedEncodingException ignored.
//	 * @throws IOException                  if something happens while reading from
//	 *                                      file.
//	 */
//	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//		if (args.length != 1) {
//			System.out.println("Program expects one argument, path to some file.\nExiting..");
//			return;
//		}
//
//		Path path = Paths.get(args[0]);
//
//		if (!Files.exists(path)) {
//			System.out.println(path + " doesn't exist.\nExiting..");
//			return;
//		}
//
//		String docBody = new String(Files.readAllBytes(path), "UTF-8");
//		SmartScriptParser p = new SmartScriptParser(docBody);
//		WriterVisitor visitor = new WriterVisitor();
//		p.getDocumentNode().accept(visitor);
//	}
//
//	/**
//	 * An implementation of {@link INodeVisitor}.
//	 * 
//	 * @author dbrcina
//	 *
//	 */
//	public static class WriterVisitor implements INodeVisitor {
//		/**
//		 * An instance of {@link StringBuilder} used to buffer output text.
//		 */
//		private StringBuilder sb = new StringBuilder();
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public void visitTextNode(TextNode node) {
//			String text = node.getText();
//			text = text.replaceAll("\\\\", "\\\\\\\\").replaceAll("[{]", "\\\\{");
//			sb.append(text);
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public void visitForLoopNode(ForLoopNode node) {
//			sb.append("{$FOR ");
//			sb.append(node.getVariable() + " ");
//			sb.append(node.getStartExpression() + " ");
//			sb.append(node.getEndExpression() + " ");
//
//			Element stepExpression = node.getStepExpression();
//			if (stepExpression != null) {
//				sb.append(stepExpression + " ");
//			}
//
//			sb.append("$}");
//
//			for (int i = 0; i < node.numberOfChildren(); i++) {
//				node.getChild(i).accept(this);
//			}
//
//			sb.append("{$END$}");
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public void visitEchoNode(EchoNode node) {
//			sb.append("{$= ");
//			Element[] elements = node.getElements();
//			for (Element element : elements) {
//				sb.append(element + " ");
//			}
//			sb.append("$}");
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public void visitDocumentNode(DocumentNode node) {
//			int len = node.numberOfChildren();
//			for (int i = 0; i < len; i++) {
//				Node n = node.getChild(i);
//				if (n instanceof TextNode) {
//					visitTextNode((TextNode) n);
//				} else if (n instanceof EchoNode) {
//					visitEchoNode((EchoNode) n);
//				} else if (n instanceof ForLoopNode) {
//					visitForLoopNode((ForLoopNode) n);
//				}
//			}
//			System.out.println(sb.toString());
//		}
//
//	}
//}
