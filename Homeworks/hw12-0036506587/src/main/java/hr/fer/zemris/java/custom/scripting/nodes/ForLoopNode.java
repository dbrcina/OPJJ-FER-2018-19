package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits from
 * {@link Node} class.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ForLoopNode extends Node {

	/**
	 * {@link ElementVariable} type variable used inside tags.
	 */
	private ElementVariable variable;
	/**
	 * Expression used inside tags. It represents expression's begining.
	 */
	private Element startExpression;
	/**
	 * Expression used inside tags. It represents expression's ending.
	 */
	private Element endExpression;
	/**
	 * Expression used inside tags. It represents whether any steps should be
	 * provided or not.
	 */
	private Element stepExpression;

	/**
	 * A constructor which initializes {@link ForLoopNode}'s properties.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for {@link ForLoopNode}'s <code>variable</code>.
	 * 
	 * @return {@code ForLoopNode}'s {@code variable}.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for {@link ForLoopNode}'s <code>startExpression</code>.
	 * 
	 * @return {@code ForLoopNode}'s {@code startExpression}.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for {@link ForLoopNode}'s <code>endExpression</code>.
	 * 
	 * @return {@code ForLoopNode}'s {@code endExpression}.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for {@link ForLoopNode}'s <code>stepExpression</code>.
	 * 
	 * @return {@code ForLoopNode}'s {@code stepExpression}.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		String text = "{$FOR ";
		text += variable + " ";
		text += startExpression + " ";
		text += endExpression + " ";

		if (stepExpression != null) {
			text += stepExpression + " ";
		}

		text += "$}";
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
