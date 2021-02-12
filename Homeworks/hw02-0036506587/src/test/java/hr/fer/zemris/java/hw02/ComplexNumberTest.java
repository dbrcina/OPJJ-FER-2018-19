package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void testConstructor() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(1, c.getReal());
		assertEquals(1, c.getImaginary());
		assertEquals(Math.sqrt(2), c.getMagnitude());
		assertEquals(0.25 * Math.PI, c.getAngle());
	}

	@Test
	public void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(5);
		assertEquals(5, c.getReal());
		assertEquals(0, c.getImaginary());
		assertEquals(5, c.getMagnitude());
		assertEquals(0, c.getAngle());
	}

	@Test
	public void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(5);
		assertEquals(0, c.getReal());
		assertEquals(5, c.getImaginary());
		assertEquals(5, c.getMagnitude());
		assertEquals(0.5 * Math.PI, c.getAngle());
	}

	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(3, 0.5);
		assertEquals(2.6327476856711183, c.getReal());
		assertEquals(1.438276615812609, c.getImaginary());
		assertEquals(3, c.getMagnitude());
		assertEquals(0.5, c.getAngle());
	}

	@Test
	public void testParse() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse(""));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("fafas"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-+2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("--2.71"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-2.71+-3.15i"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i351"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("-i3.17"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("i3-"));
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("+ii"));

		ComplexNumber c = ComplexNumber.parse("2.5-3i");
		assertEquals(2.5, c.getReal());
		assertEquals(-3, c.getImaginary());
		assertEquals(Math.sqrt(61) / 2, c.getMagnitude());
		assertEquals(-0.8760580505981934, c.getAngle());
		
		ComplexNumber c1 = ComplexNumber.parse("-i");
		assertEquals(0, c1.getReal());
		assertEquals(-1, c1.getImaginary());

	}

	@Test
	public void testForGetReal() {
		ComplexNumber c = new ComplexNumber(2, 4);
		assertEquals(2, c.getReal());
	}

	@Test
	public void testForGetImaginary() {
		ComplexNumber c = new ComplexNumber(2, 4);
		assertEquals(4, c.getImaginary());
	}

	@Test
	public void testForGetMagnitude() {
		ComplexNumber c = new ComplexNumber(2, 4);
		assertEquals(2 * Math.sqrt(5), c.getMagnitude());
	}

	@Test
	public void testForGetAngle() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(0.25 * Math.PI, c.getAngle());
	}

	@Test
	public void testForAdd() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(1, -1);
		ComplexNumber c3 = c1.add(c2);
		assertEquals(3, c3.getReal());
		assertEquals(2, c3.getImaginary());
	}

	@Test
	public void testForSub() {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = new ComplexNumber(1, -1);
		ComplexNumber c3 = c1.sub(c2);
		assertEquals(1, c3.getReal());
		assertEquals(4, c3.getImaginary());
	}

	@Test
	public void testForMul() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, 0.5);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(2, 0.5);
		ComplexNumber c3 = c1.mul(c2);
		assertEquals(6 * Math.cos(1), c3.getReal());
		assertEquals(6 * Math.sin(1), c3.getImaginary());
	}

	@Test
	public void testForDiv() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(3, 0.5);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(2, 0.5);
		ComplexNumber c3 = c1.div(c2);
		assertEquals(1.5, c3.getReal());
		assertEquals(0, c3.getImaginary());
	}

	@Test
	public void testForPower() {
		ComplexNumber c = new ComplexNumber(3, 4);

		// check for invalid n
		assertThrows(IllegalArgumentException.class, () -> c.power(-2));

		// chech for valid n
		assertEquals(1, c.power(0).getReal());
		assertEquals(0, c.power(0).getImaginary());
		assertEquals(1.8545904360032244, c.power(2).getAngle());
	}

	@Test
	public void testForRoot() {
		ComplexNumber c = new ComplexNumber(1, -1);

		// check for invalid n
		assertThrows(IllegalArgumentException.class, () -> c.root(-2));

		// check for valid n
		ComplexNumber[] numbs = c.root(2);
		assertEquals(-0.3926990816987242, numbs[0].getAngle()); // -0.125 * PI
	}
}
