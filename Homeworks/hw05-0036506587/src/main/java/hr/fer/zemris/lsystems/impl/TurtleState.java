package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Model representing a "turtle" that is used for drawing pictures.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class TurtleState {

	/**
	 * Turtle's current position.
	 */
	private Vector2D position;

	/**
	 * Turtle's current direction.
	 */
	private Vector2D direction;

	/**
	 * Turtle's current color.
	 */
	private Color color;

	/**
	 * Turtle's current effective length.
	 */
	private double effectiveLength;

	/**
	 * Constructor that initializes properties.
	 * 
	 * @param position        position in coordinate system.
	 * @param direction       normalized vector.
	 * @param color           color to be set.
	 * @param effectiveLength length.
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Copies current turtle.
	 * 
	 * @return new instace of {@link TurtleState} with current properties copied.
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), new Color(color.getRGB()), effectiveLength);
	}

	/**
	 * Getter for turtle's current position.
	 * 
	 * @return turtle's current positon.
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Setter for turtle's position.
	 * 
	 * @param position position.
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Getter for turtle's current direction.
	 * 
	 * @return turtle's current direction.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Setter for turtle's direction.
	 * 
	 * @param direction direction.
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Getter for turtle's current color.
	 * 
	 * @return turtle's current color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for turtle's color.
	 * 
	 * @param color color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for turtle's current effective length.
	 * 
	 * @return turtle's current effective length.
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Setter for turtle's effective length.
	 * 
	 * @param effectiveLength effective length.
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}

}
