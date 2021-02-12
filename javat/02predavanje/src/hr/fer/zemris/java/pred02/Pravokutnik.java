package hr.fer.zemris.java.pred02;

import java.util.Objects;

public class Pravokutnik extends GeometrijskiLik {

	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;

	public Pravokutnik(String ime, int vrhX, int vrhY, int sirina, int visina) {
		super(ime);
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}

	public int getVrhX() {
		return vrhX;
	}

	public int getVrhY() {
		return vrhY;
	}

	public int getSirina() {
		return sirina;
	}

	public int getVisina() {
		return visina;
	}

	@Override
	public double getPovrsina() {
		return sirina * visina;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(sirina, visina, vrhX, vrhY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Pravokutnik))
			return false;
		Pravokutnik other = (Pravokutnik) obj;
		return sirina == other.sirina && visina == other.visina && vrhX == other.vrhX && vrhY == other.vrhY;
	}

	@Override
	public String toString() {
		return super.toString() + " a ostalo je " + vrhX + ", " + vrhY + ", " + sirina + ", " + visina;
	}
}
