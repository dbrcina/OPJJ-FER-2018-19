package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import hr.fer.zemris.math.ComplexUtil;

/**
 * Program that simulates <i>Newton-Raphson</i> fractal iteration.
 * 
 * @author dbrcina
 *
 */
public class Newton {

	/**
	 * Constant representing convergence treshold.
	 */
	private static final double CONVERGENCE_TRESHOLD = 0.002;

	/**
	 * Constant representing maximum iterations.
	 */
	private static final int MAX_ITER = 500;

	/**
	 * Representation of {@link ComplexRootedPolynomial} expression.
	 */
	private static ComplexRootedPolynomial rootedPolynomial;

	/**
	 * Representation of {@link ComplexPolynomial} expression.
	 */
	private static ComplexPolynomial polynomial;

	/**
	 * Derivation of {@link #polynomial}.
	 */
	private static ComplexPolynomial derived;

	/**
	 * Main entry of this program.
	 * 
	 * @param args args.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		try (Scanner sc = new Scanner(System.in)) {
			List<Complex> roots = input(sc);
			System.out.println("Image of fractal will appear shortly. Thank you.");

			rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(new Complex[roots.size()]));
			polynomial = rootedPolynomial.toComplexPolynom();
			derived = polynomial.derive();

			FractalViewer.show(new MyProducer());
		}
	}

	/**
	 * Helper method used for reading user's input from {@link System#in}.
	 * 
	 * @param sc scanner.
	 * @return list of parsed {@link Complex} number.
	 */
	private static List<Complex> input(Scanner sc) {
		int i = 1;
		List<Complex> roots = new ArrayList<>();

		while (true) {
			System.out.print("Root " + i + "> ");
			String line = sc.nextLine();

			if (line.equalsIgnoreCase("done")) {
				if (roots.size() < 2) {
					System.out.println("Please provide 2 roots!");
					continue;
				}
				break;
			}

			Complex root = null;
			try {
				root = ComplexUtil.parse(line);
			} catch (IllegalArgumentException e) {
				System.out.println("Provided complex number is invalid! Please re-enter.");
				continue;
			}

			roots.add(root);
			i++;
		}

		return roots;
	}

	/**
	 * Model of {@link Callable} job.
	 * 
	 * @author dbrcina
	 *
	 */
	public static class Job implements Callable<Void> {

		/**
		 * Minimum real value.
		 */
		double reMin;

		/**
		 * Maximum real value.
		 */
		double reMax;

		/**
		 * Minimum imag valuel.
		 */
		double imMin;

		/**
		 * Maximum imag value.
		 */
		double imMax;

		/**
		 * Frame's width.
		 */
		int width;

		/**
		 * Frame's hight.
		 */
		int height;

		/**
		 * Minimum y-component.
		 */
		int yMin;

		/**
		 * Maximum y-component.
		 */
		int yMax;

		/**
		 * Maximum number of iterations.
		 */
		int m;

		/**
		 * Data array.
		 */
		short[] data;

		/**
		 * Boolean flag which tells whether painting is necessary
		 */
		AtomicBoolean cancel;

		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data, AtomicBoolean cancel) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			double module;
			for (int y = yMin; y < yMax; y++) {
				if (cancel.get()) {
					break;
				}
				for (int x = 0; x < width; x++) {
					double real = (double) x / (width - 1) * (reMax - reMin) + reMin;
					double imag = (double) (height - 1 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(real, imag);
					int iter = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					} while (module > CONVERGENCE_TRESHOLD && iter < MAX_ITER);
					int index = rootedPolynomial.indexOfClosestRootFor(zn, CONVERGENCE_TRESHOLD);
					data[offset++] = (short) (index + 1);
				}
			}
			return null;
		}
	}

	/**
	 * An implementation of {@link IFractalProducer}.
	 * 
	 * @author dbrcina
	 *
	 */
	public static class MyProducer implements IFractalProducer {

		/**
		 * Thread pool.
		 */
		private ExecutorService pool;

		/**
		 * Default constructor that creates new {@link #pool}.
		 */
		public MyProducer() {
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
				Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			System.out.println("Calculating..");
			int m = MAX_ITER;
			short[] data = new short[width * height];
			final int tapes = 8 * Runtime.getRuntime().availableProcessors();
			int yPerTape = height / tapes;
			System.out.println("Y - per tapes: " + yPerTape);

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < tapes; i++) {
				int yMin = i * yPerTape;
				int yMax = (i + 1) * yPerTape;

				if (i == tapes - 1) {
					yMax = height - 1;
				}

				Callable<Void> job = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				results.add(pool.submit(job));
			}

			for (Future<Void> result : results) {
				try {
					result.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Calculation done");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

	}
}
