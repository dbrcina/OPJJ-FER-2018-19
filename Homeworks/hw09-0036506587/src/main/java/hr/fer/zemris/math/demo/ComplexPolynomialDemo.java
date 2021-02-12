package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class ComplexPolynomialDemo {

	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		ComplexPolynomial pol = new ComplexPolynomial(Complex.ONE, new Complex(5, 0), new Complex(2, 0),
				new Complex(7, 2));

		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
		System.out.println(pol.derive());
	}
}
