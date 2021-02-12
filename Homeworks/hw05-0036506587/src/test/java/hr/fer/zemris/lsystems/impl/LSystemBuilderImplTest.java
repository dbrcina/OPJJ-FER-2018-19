package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;

public class LSystemBuilderImplTest {

	@Test
	public void testTriangle() {
		LSystemViewer.showLSystem(new LSystem() {
			
			@Override
			public String generate(int arg0) {
				return "";
			}
			
			@Override
			public void draw(int level, Painter painter) {
				painter.drawLine(0.1, 0.1, 0.9, 0.1, Color.RED, 1f);
				painter.drawLine(0.9, 0.1, 0.9, 0.9, Color.GREEN, 1f);
				painter.drawLine(0.9, 0.9, 0.1, 0.1, Color.BLUE, 1f);
			}
		});
	}
	
	@Test
	public void testGenerate0() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		impl.setAxiom("F");
		impl.registerProduction('F', "F+F--F+F");
		assertEquals("F", impl.build().generate(0));
	}

	@Test
	public void testGenerate1() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		impl.setAxiom("F");
		impl.registerProduction('F', "F+F--F+F");
		assertEquals("F+F--F+F", impl.build().generate(1));
	}
	
	@Test
	public void testGenerate2() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		impl.setAxiom("F");
		impl.registerProduction('F', "F+F--F+F");
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", impl.build().generate(2));
	}
}
