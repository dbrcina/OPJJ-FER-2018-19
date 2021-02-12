package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.NowNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Engine used to generate <i>HTML</i> documents from scripts.
 * 
 * @author dbrcina
 *
 */
public class SmartScriptEngine {

	/**
	 * Document node.
	 */
	private DocumentNode documentNode;
	/**
	 * Request context.
	 */
	private RequestContext requestContext;
	/**
	 * Instance of {@link ObjectMultistack}
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Instance of {@link INodeVisitor}
	 */
	private INodeVisitor visitor = createVisitor();

	/**
	 * Constructor used for initialization.
	 * 
	 * @param documentNode   document node.
	 * @param requestContext request context.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Starts the engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

	/**
	 * Creates an instance of {@link INodeVisitor}.
	 * 
	 * @return visitor.
	 */
	private INodeVisitor createVisitor() {
		return new INodeVisitor() {
			@Override
			public void visitTextNode(TextNode node) {
				try {
					requestContext.write(node.getText());
				} catch (IOException e) {
					System.out.println("Error occured while writing to file.\nTerminating..");
					System.exit(-1);
				}
			}

			@Override
			public void visitForLoopNode(ForLoopNode node) {
				String variable = node.getVariable().asText();
				ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
				ValueWrapper end = new ValueWrapper(node.getEndExpression().asText());

				Element stepExpression = node.getStepExpression();
				ValueWrapper step = stepExpression == null ? new ValueWrapper(null)
						: new ValueWrapper(stepExpression.asText());

				multistack.push(variable, new ValueWrapper(start.getValue()));

				while (start.numCompare(end.getValue()) <= 0) {
					for (int i = 0; i < node.numberOfChildren(); i++) {
						node.getChild(i).accept(visitor);
					}
					start = multistack.peek(variable);
					start.add(step.getValue());
				}

				multistack.pop(variable);
			}

			@Override
			public void visitEchoNode(EchoNode node) {
				Stack<Object> tempStack = new Stack<>();
				Element[] elements = node.getElements();
				for (Element element : elements) {
					if (element instanceof ElementVariable) {
						tempStack.push(multistack.peek(((ElementVariable) element).asText()).getValue());
					} else if (element instanceof ElementOperator) {
						performBinaryOperation((ElementOperator) element, tempStack);
					} else if (element instanceof ElementFunction) {
						performFunction((ElementFunction) element, tempStack);
					} else {
						tempStack.push(element.asText());
					}
				}

				try {
					writeResults(tempStack);
				} catch (IOException e) {
					System.out.println("Error occured while writing to output stream!");
					System.exit(-1);
				}
			}

			@Override
			public void visitDocumentNode(DocumentNode node) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(visitor);
				}
			}

			@Override
			public void visitNowNode(NowNode node) {
				if (node.getFormater() == null) {
					try {
						requestContext.write(LocalDateTime.now().format(node.getDefFormat()));
					} catch (IOException e) {
					}
				} else {
					DateTimeFormatter format = DateTimeFormatter.ofPattern(node.getFormater());
					try {
						requestContext.write(format.format(LocalDateTime.now()));
					} catch (IOException e) {
					}
				}
			}
		};
	}

	/**
	 * Writes result using {@link RequestContext#write(String)} method.
	 * 
	 * @param stack stack
	 * @throws IOException if something happens when writing to stream.
	 */
	private void writeResults(Stack<Object> stack) throws IOException {
		for (int i = 0; i < stack.size(); i++) {
			requestContext.write(stack.get(i).toString());
		}
	}

	/**
	 * Performs binary operation as determined by <code>element</code> operator.
	 * Legal operators are: <i>"+", "-", "*", "/ "</i>.
	 * 
	 * @param element element operator.
	 * @param stack   stack.
	 * @return instance of {@link ValueWrapper} as a result.
	 */
	private void performBinaryOperation(ElementOperator operator, Stack<Object> stack) {
		Object value = new ValueWrapper(stack.pop()).getValue();
		ValueWrapper first = new ValueWrapper(stack.pop());
		switch (operator.asText()) {
		case "+":
			first.add(value);
			break;
		case "-":
			first.substract(value);
			break;
		case "*":
			first.multiply(value);
			break;
		case "/":
			first.divide(value);
		default:
			break;
		}
		stack.push(first.getValue());
	}

	/**
	 * Helper method used for delegation of other methods that perform given
	 * <code>function</code>.
	 * 
	 * @param function function.
	 * @param stack    stack.
	 */
	private void performFunction(ElementFunction function, Stack<Object> stack) {
		switch (function.asText()) {
		case "sin":
			performSin(stack);
			break;
		case "decfmt":
			performDecfmt(stack);
			break;
		case "dup":
			performDup(stack);
			break;
		case "swap":
			performSwap(stack);
			break;
		case "setMimeType":
			performSetMimeType(stack);
			break;
		case "paramGet":
			performParamGet(n -> requestContext.getParameter(n), stack);
			break;
		case "pparamGet":
			performParamGet(n -> requestContext.getPersistentParameter(n), stack);
			break;
		case "tparamGet":
			performParamGet(n -> requestContext.getTemporaryParameter(n), stack);
			break;
		case "pparamSet":
			performParamSet((n, v) -> requestContext.setPersistentParameter(n, v), stack);
			break;
		case "tparamSet":
			performParamSet((n, v) -> requestContext.setTemporaryParameter(n, v), stack);
			break;
		case "pparamDel":
			performParamDel(n -> requestContext.removePersistentParameter(n), stack);
			break;
		case "tparamDel":
			performParamDel(n -> requestContext.removeTemporaryParameter(n), stack);
			break;
		case "now":
			performNow(stack);
			break;
		default:
			throw new RuntimeException("Invalid function!");
		}
	}

	private void performNow(Stack<Object> stack) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern((String) stack.pop());
		stack.push(LocalDateTime.now().format(format));
	}

	/**
	 * <b><i>sin(x)</i></b>
	 * 
	 * <p>
	 * Calculates <i>sinus</i> from given argument and stores the result back to
	 * stack.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * x = pop(), r = sin(x), push(r)
	 * </pre>
	 * 
	 * @param stack stack.
	 */
	private void performSin(Stack<Object> stack) {
		ValueWrapper value = new ValueWrapper(stack.pop());
		double x = Double.parseDouble(value.toString());
		stack.push(new ValueWrapper(Math.sin(Math.toRadians(x))).getValue());
	}

	/**
	 * <b><i>decfmt(x,f)</i></b>
	 * 
	 * <p>
	 * Formats decimal number using given format <code>f</code> which is compatible
	 * with {@link DecimalFormat}; produces a string. <code>x</code> can be integer,
	 * double or string representation of a number.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * f = pop(), x = pop(), r = decfmt(x,f), push(r)
	 * </pre>
	 * 
	 * @param stack stack.
	 */
	private void performDecfmt(Stack<Object> stack) {
		DecimalFormat format = new DecimalFormat((String) stack.pop());
		ValueWrapper x = new ValueWrapper(stack.pop());
		String value = x.toString();
		stack.push(format.format(Double.parseDouble(value)));
	}

	/**
	 * <b><i>dup()</i></b>
	 * 
	 * <p>
	 * Duplicates current top value from stack.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * x = pop(), push(x), push(x)
	 * </pre>
	 * 
	 * @param stack stack
	 */
	private void performDup(Stack<Object> stack) {
		ValueWrapper x = new ValueWrapper(stack.pop());
		stack.push(x.getValue());
		stack.push(x.getValue());
	}

	/**
	 * <b><i>swap()</i></b>
	 * 
	 * <p>
	 * Replaces the order of two topmost items on stack.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * a = pop(), b = pop(), push(a), push(b)
	 * </pre>
	 * 
	 * @param stack stack.
	 */
	private void performSwap(Stack<Object> stack) {
		ValueWrapper a = (ValueWrapper) stack.pop();
		ValueWrapper b = (ValueWrapper) stack.pop();
		stack.push(a);
		stack.push(b);
	}

	/**
	 * <b><i>setMimeType(x)</i></b>
	 * 
	 * <p>
	 * Takes string <code>x</code> and calls
	 * <code>requestContext.setMimeType(x)</code>.<br>
	 * Does not produce any result.
	 * 
	 * @param stack stack.
	 * @see RequestContext#setMimeType(String)
	 */
	private void performSetMimeType(Stack<Object> stack) {
		String x = (String) stack.pop();
		requestContext.setMimeType(x);
	}

	/**
	 * <b><i>paramGet(name, defValue); pparamGet(name, defValue); tparamGet(name,
	 * defValue)</i></b>
	 * 
	 * <p>
	 * Obtains from <code>requestContext</code> parameters map a value mapped for
	 * <code>name</code> and pushes it onto stack. If there is no such mapping, it
	 * pushes instead <code>defValue</code> onto stack.<br>
	 * This method is used for all three getters of parameters which specification
	 * is given through {@link Function} argument <code>action</code>.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * dv = pop(), name = pop(), value = reqCtx.getParam(name), 
	 * push (value == null ? defValue : value
	 * </pre>
	 * 
	 * @param action action.
	 * @param stack  stack.
	 * @see RequestContext#getParameter(String)
	 * @see RequestContext#getPersistentParameter(String)
	 * @see RequestContext#getTemporaryParameter(String)
	 */
	private void performParamGet(Function<String, String> action, Stack<Object> stack) {
		String dv = (String) stack.pop();
		String name = (String) stack.pop();
		String value = action.apply(name);
		stack.push(value == null ? dv : value);
	}

	/**
	 * <b><i>pparamSet(value, name); tparamSet(value, name)</i></b>
	 * 
	 * <p>
	 * Stores a <code>value</code> into {@link #requestContext}
	 * <i>persistent/temporary</i> parameters map.<br>
	 * This method is used for both setters of parameters which specification is
	 * given through {@link BiConsumer} argument <code>action</code>.<br>
	 * Conceptually:
	 * 
	 * <pre>
	 * name = pop(), value = pop(), reqCtx.setParam(name,value)
	 * </pre>
	 * 
	 * @param action action.
	 * @param stack  stack.
	 * @see RequestContext#setPersistentParameter(String, String)
	 * @see RequestContext#setTemporaryParameter(String, String)
	 */
	private void performParamSet(BiConsumer<String, String> action, Stack<Object> stack) {
		String name = (String) stack.pop();
		String value = new ValueWrapper(stack.pop()).toString();
		action.accept(name, value);
	}

	/**
	 * <b><i>pparamDel(name); tparamDel(name)</i></b>
	 * 
	 * <p>
	 * Removes assosiation for <code>name</code> from {@link #requestContext}
	 * <i>persistent/temporary</i> parameters map.<br>
	 * This method is used for both remove methods of parameters which specification
	 * is given through {@link Consumer} argument <code>action</code>.
	 * 
	 * @param action action.
	 * @param stack  stack.
	 * @see RequestContext#removePersistentParameter(String)
	 * @see RequestContext#removeTemporaryParameter(String)
	 */
	private void performParamDel(Consumer<String> action, Stack<Object> stack) {
		String name = (String) stack.pop();
		action.accept(name);
	}
}
