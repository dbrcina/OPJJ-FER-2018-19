package hr.fer.zemris.java.pred05.primjer5;

import java.util.Comparator;

public class Zaposlenik {

	private String sifra;
	private String prezime;
	private String ime;
	private double placa;

	public static final Comparator<Zaposlenik> PO_IMENU = (z1, z2) -> z2.getIme().compareTo(z1.getIme());
	public static final Comparator<Zaposlenik> PO_PREZIMENU = (z1, z2) -> z2.getPrezime().compareTo(z1.getPrezime());
	public static final Comparator<Zaposlenik> PO_SIFRI = (z1, z2) -> z1.getSifra().compareTo(z2.getSifra());

	public Zaposlenik(String sifra, String prezime, String ime) {
		super();
		this.sifra = sifra;
		this.prezime = prezime;
		this.ime = ime;
	}

	public String getSifra() {
		return sifra;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getIme() {
		return ime;
	}

	public double getPlaca() {
		return placa;
	}

	public void setPlaca(double placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return String.format("Zaposlenik: šifra=%s, prezime=%s, ime=%s, plaća=%f", sifra, prezime, ime, placa);
	}

}
