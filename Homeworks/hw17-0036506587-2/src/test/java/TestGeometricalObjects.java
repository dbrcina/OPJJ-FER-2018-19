import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;

public class TestGeometricalObjects {

	@Test
	public void testLine() {
		Line line = new Line(new Point(50, 90), new Point(30, 10), new Color(255, 255, 0));
		System.out.println(line);
		System.out.println(line.fileRepresentation());
	}
	
	@Test
	public void testCircle() {
		Circle circle = new Circle(new Point(40, 40), 18, Color.BLUE);
		System.out.println(circle);
		System.out.println(circle.fileRepresentation());
	}
	
	@Test
	public void testFilledCircle() {
		FilledCircle circle = new FilledCircle(new Point(40, 40), 18, Color.BLUE, Color.RED);
		System.out.println(circle);
		System.out.println(circle.fileRepresentation());
	}
}
