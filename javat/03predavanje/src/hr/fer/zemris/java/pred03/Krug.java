package hr.fer.zemris.java.pred03;

import java.util.Objects;

public class Krug extends GeometrijskiLik {

	private int centarX;
	private int centarY;
	private int radius;

	public Krug(int centarX, int centarY, int radius) {
		super();
		this.centarX = centarX;
		this.centarY = centarY;
		this.radius = radius;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		double udaljenost = Math.sqrt(Math.pow(x - centarX, 2) + Math.pow(y - centarY, 2));
		return udaljenost <= radius;
	}
	
	public int getCentarX() {
		return centarX;
	}

	public int getCentarY() {
		return centarY;
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public int hashCode() {
		return Objects.hash(centarX, centarY, radius);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Krug))
			return false;
		Krug other = (Krug) obj;
		return centarX == other.centarX && centarY == other.centarY && radius == other.radius;
	}

}
