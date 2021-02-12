package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {

	@Test
	public void testGet() {
		ValueWrapper valueWrapper = new ValueWrapper(20);
		assertEquals(20, valueWrapper.getValue());
		assertEquals(Integer.class, valueWrapper.getValue().getClass());
	}

	@Test
	public void testSet() {
		ValueWrapper valueWrapper = new ValueWrapper(20);

		valueWrapper.setValue("2.23");
		assertEquals("2.23", valueWrapper.getValue());
		assertEquals(String.class, valueWrapper.getValue().getClass());

		valueWrapper.setValue(true);
		assertEquals(Boolean.class, valueWrapper.getValue().getClass());
	}

	@Test
	public void testAddBothNull() {
		ValueWrapper valueWrapper = new ValueWrapper(null);
		valueWrapper.add(new ValueWrapper(null).getValue());
		assertEquals(0, valueWrapper.getValue());
	}

	@Test
	public void testAddOneNull() {
		ValueWrapper valueWrapper = new ValueWrapper(null);
		valueWrapper.add(new ValueWrapper(23.5).getValue());
		assertEquals(23.5, valueWrapper.getValue());
	}

	@Test
	public void testAddInt() {
		ValueWrapper valueWrapper = new ValueWrapper(32);
		valueWrapper.add(new ValueWrapper("8").getValue());
		valueWrapper.add(new ValueWrapper(10).getValue());
		assertEquals(50, valueWrapper.getValue());
	}

	@Test
	public void testAddIntDouble() {
		ValueWrapper valueWrapper = new ValueWrapper(32);
		valueWrapper.add(new ValueWrapper("8.0").getValue());
		valueWrapper.add(new ValueWrapper(10.0).getValue());
		assertEquals(50.0, valueWrapper.getValue());
		assertTrue(valueWrapper.getValue() instanceof Double);
	}

	@Test
	public void testAddDouble() {
		ValueWrapper valueWrapper = new ValueWrapper(32.0);
		valueWrapper.add(new ValueWrapper("0.8E1").getValue());
		valueWrapper.add(new ValueWrapper(10).getValue());
		assertEquals(50.0, valueWrapper.getValue());
	}

	@Test
	public void testAddStringIntDouble() {
		ValueWrapper valueWrapper = new ValueWrapper("32.0");
		valueWrapper.add(new ValueWrapper("8").getValue());
		valueWrapper.add(new ValueWrapper("1e1").getValue());
		assertEquals(50.0, valueWrapper.getValue());
	}

	@Test
	public void testAddWrongType() {
		ValueWrapper vv1 = new ValueWrapper(Boolean.valueOf(true));
		assertThrows(RuntimeException.class, () -> vv1.add(Integer.valueOf(5)));

		ValueWrapper vv2 = new ValueWrapper(Integer.valueOf(5));
		assertThrows(RuntimeException.class, () -> vv2.add(Boolean.valueOf(true)));
	}

	@Test
	public void testSubstract() {
		ValueWrapper valueWrapper = new ValueWrapper(32.0);
		valueWrapper.substract(new ValueWrapper(2).getValue());
		valueWrapper.substract(new ValueWrapper("0.1e2").getValue());
		assertEquals(20.0, valueWrapper.getValue());
	}

	@Test
	public void testSubstractWrongType() {
		ValueWrapper valueWrapper = new ValueWrapper(32.0);
		assertThrows(RuntimeException.class, () -> valueWrapper.substract(new ValueWrapper("asasa").getValue()));
		assertThrows(RuntimeException.class,
				() -> valueWrapper.substract(new ValueWrapper(Boolean.valueOf(false)).getValue()));
		assertTrue(valueWrapper.getValue() instanceof Double);
	}

	@Test
	public void testMultiply() {
		ValueWrapper valueWrapper = new ValueWrapper("2");
		valueWrapper.multiply(new ValueWrapper(2).getValue());
		assertEquals(4, valueWrapper.getValue());

		valueWrapper.multiply(new ValueWrapper("2.0").getValue());
		assertEquals(8.0, valueWrapper.getValue());

		valueWrapper.multiply(new ValueWrapper("1E1").getValue());
		assertEquals(80.0, valueWrapper.getValue());
		assertTrue(valueWrapper.getValue() instanceof Double);
	}

	@Test
	public void testMultiplyWrongType() {
		ValueWrapper valueWrapper = new ValueWrapper("2");
		assertThrows(RuntimeException.class, () -> valueWrapper.multiply(new ValueWrapper("asasa").getValue()));
		assertThrows(RuntimeException.class,
				() -> valueWrapper.multiply(new ValueWrapper(Boolean.valueOf(false)).getValue()));
	}

	@Test
	public void testDivide() {
		ValueWrapper valueWrapper = new ValueWrapper("2");
		valueWrapper.divide(new ValueWrapper("0.1e1").getValue());
		assertEquals(2.0, valueWrapper.getValue());

		valueWrapper.divide(new ValueWrapper(0.5).getValue());
		assertEquals(4.0, valueWrapper.getValue());
		assertTrue(valueWrapper.getValue() instanceof Double);
	}

	@Test
	public void testDivideByZero() {
		ValueWrapper valueWrapper = new ValueWrapper("2");
		assertThrows(RuntimeException.class, () -> valueWrapper.divide(new ValueWrapper(0).getValue()));
		assertThrows(RuntimeException.class, () -> valueWrapper.divide(new ValueWrapper("0").getValue()));
	}

	@Test
	public void testNumCompareBothNull() {
		ValueWrapper valueWrapper = new ValueWrapper(null);
		assertEquals(0, valueWrapper.numCompare(new ValueWrapper(null).getValue()));
	}

	@Test
	public void testNumCompareOneNull() {
		ValueWrapper valueWrapper = new ValueWrapper(null);
		assertEquals(-1, valueWrapper.numCompare(new ValueWrapper(6).getValue()));
	}
	
	@Test
	public void testNumCompareGreater() {
		ValueWrapper valueWrapper = new ValueWrapper("2e1");
		assertEquals(1, valueWrapper.numCompare(new ValueWrapper("2.0").getValue()));
	}

	@Test
	public void testNumCompareEqual() {
		ValueWrapper valueWrapper = new ValueWrapper("2e1");
		assertEquals(0, valueWrapper.numCompare(new ValueWrapper(20.0).getValue()));
	}

	@Test
	public void testNumCompareLesser() {
		ValueWrapper valueWrapper = new ValueWrapper("2e1");
		assertEquals(-1, valueWrapper.numCompare(new ValueWrapper("30.0").getValue()));
	}

	@Test
	public void testNumCompareWrongType() {
		ValueWrapper valueWrapper = new ValueWrapper("2e1");
		assertThrows(RuntimeException.class, () -> valueWrapper.numCompare(new ValueWrapper("asasa").getValue()));
		assertThrows(RuntimeException.class,
				() -> valueWrapper.numCompare(new ValueWrapper(Boolean.valueOf(true)).getValue()));
	}
}
