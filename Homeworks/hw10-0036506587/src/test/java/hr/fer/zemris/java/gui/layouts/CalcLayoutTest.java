package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {

	@Test
	public void testInvalidRows() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(-1, 3)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(0, 3)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), "6,3"));
	}

	@Test
	public void testInvalidColumns() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(3, -1)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(3, 0)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), "3,8"));
	}

	@Test
	public void testInvalidFirstRow() {
		JPanel panel = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(1, 3)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), new RCPosition(1, 2)));
		assertThrows(CalcLayoutException.class, () -> panel.add(new JLabel(), "1,5"));
	}

	@Test
	public void testAddSamePosition() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel(), "1,1");
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), "1,1"));
	}
	
	@Test
	public void testPrefSize1() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
	
	@Test
	public void testPrefSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}
