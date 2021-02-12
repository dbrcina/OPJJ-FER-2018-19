package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

	/**
	 * Constant that is used to check whether two doubles are equal.
	 */
	private static final double DELTA = 1e-6;

	@Test
	public void testConstructor() {
		Vector2D vector = new Vector2D(5, 10);

		assertEquals(5.0, vector.getX());
		assertEquals(10, vector.getY());
	}

	@Test
	public void testGetX() {
		Vector2D vector = new Vector2D(-2.3, 15);

		assertEquals(-2.3, vector.getX());
	}

	@Test
	public void testGetY() {
		Vector2D vector = new Vector2D(-2.3, 15);

		assertEquals(15.0, vector.getY());
	}

	@Test
	public void testTranslateNull() {
		Vector2D vector = new Vector2D(-2.3, 15);

		// it throws
		assertThrows(NullPointerException.class, () -> vector.translate(null));
	}

	@Test
	public void testTranslate() {
		Vector2D vector = new Vector2D(-2.3, 15);
		vector.translate(new Vector2D(12.7, -3));

		assertEquals(-2.3 + 12.7, vector.getX());
		assertEquals(15.0 - 3, vector.getY());
	}

	@Test
	public void testTranslated() {
		Vector2D vector = new Vector2D(-2.3, 15);
		Vector2D newVector = vector.translated(new Vector2D(12.7, -3));

		assertEquals(-2.3 + 12.7, newVector.getX());
		assertEquals(15.0 - 3, newVector.getY());
	}

	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(10.0, 10);
		vector.rotate(Math.PI / 4);

		assertEquals(0.0, vector.getX(), DELTA);
		assertEquals(10.0 * Math.sqrt(2), vector.getY(), DELTA);

		vector.rotate(Math.PI / 4);

		assertEquals(-10.0, vector.getX(), DELTA);
		assertEquals(10.0, vector.getY(), DELTA);
	}

	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(10.0, 10);
		Vector2D newVector = vector.rotated(Math.PI / 4);

		assertEquals(0.0, newVector.getX(), DELTA);
		assertEquals(10.0 * Math.sqrt(2), newVector.getY(), DELTA);

		newVector = newVector.rotated(Math.PI / 4);

		assertEquals(-10.0, newVector.getX(), DELTA);
		assertEquals(10.0, newVector.getY(), DELTA);
	}

	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(10.0, 10);
		vector.scale(2.5);

		assertEquals(25.0, vector.getX(), DELTA);
		assertEquals(25.0, vector.getY(), DELTA);
	}

	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(10.0, 10);
		Vector2D newVector = vector.scaled(4.5);

		assertEquals(45.0, newVector.getX(), DELTA);
		assertEquals(45.0, newVector.getY(), DELTA);
	}

	@Test
	public void testCopy() {
		Vector2D vector = new Vector2D(10.0, 10);
		Vector2D newVector = vector.copy();

		assertEquals(vector, newVector);
		assertEquals(vector.getX(), newVector.getX(), DELTA);
		assertEquals(vector.getY(), newVector.getY(), DELTA);
	}
}
