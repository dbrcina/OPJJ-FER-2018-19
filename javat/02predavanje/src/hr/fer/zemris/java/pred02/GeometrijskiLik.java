package hr.fer.zemris.java.pred02;

import java.util.Objects;

public class GeometrijskiLik {

	private String ime;
	
	public GeometrijskiLik(String ime) {
		this.ime = ime;
	}
	
	public String getIme() {
		return this.ime;
	}
	
	public double getOpseg() {
		return 0.0;
	}
	
	public double getPovrsina() {
		return 0.0;
	}
	
	@Override
	public String toString() {
		return "Ja sam lik imena " + ime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
//		linije 40-41 se mogu ispustiti jer instanceof dopusta i provjeravanje null vrijednosti
//		if (obj == null)
//			return false;
		if (!(obj instanceof GeometrijskiLik))
			return false;
		GeometrijskiLik other = (GeometrijskiLik) obj;
		return Objects.equals(ime, other.ime);
	}
	
	
}
