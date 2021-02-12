package hr.fer.zemris.java.raytracer;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.pow;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * An implmentation of ray caster parallely by {@link ForkJoinPool}.
 * 
 * @author dbrcina
 *
 */
public class RayCasterParallel {

	/**
	 * Constant used when comparing two doubles.
	 */
	private static final double DELTA = 1E-9;

	/**
	 * Constant representing ambiental color value.
	 */
	private static final int AMBIENTAL_COLOR_VALUE = 15;

	/**
	 * Constant representing minumum int value for color.
	 */
	private static final int MIN_COLOR_VALUE = 0;

	/**
	 * Constant representing maximum int value for color.
	 */
	private static final int MAX_COLOR_VALUE = 255;

	/**
	 * Main entry of this program.
	 * 
	 * @param args args.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				// vector from eye to view
				Point3D og = view.sub(eye);
				// normalized version of view-up vector
				Point3D vuv = viewUp.normalize();

				// eye direction
				Point3D zAxis = og.normalize();
				// j component
				Point3D yAxis = vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv))).normalize();
				// i component
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				// screen corner
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));
				// scene
				Scene scene = RayTracerViewer.createPredefinedScene();

				// creation of parallel implementation with RecursiveTask
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CasterAction(0, height - 1, width, height, horizontal, vertical, red, green, blue, eye,
						xAxis, yAxis, zAxis, screenCorner, scene, cancel));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};

	}

	private static class CasterAction extends RecursiveAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Minimum y-component.
		 */
		private int yMin;

		/**
		 * Maximum y-component.
		 */
		private int yMax;

		/**
		 * Frame's width.
		 */
		private int width;

		/**
		 * Frame's height.
		 */
		private int height;

		/**
		 * Horizontal line on frame.
		 */
		private double horizontal;

		/**
		 * Vertical line on frame.
		 */
		private double vertical;

		/**
		 * An array representing number of red color - rgb.
		 */
		private short[] red;

		/**
		 * An array representing number of green color - rgb.
		 */
		private short[] green;

		/**
		 * An array representing number blue color of pixels - rgb.
		 */
		private short[] blue;

		/**
		 * Eye position.
		 */
		private Point3D eye;

		/**
		 * <i><b> i </b></i> component.
		 */
		private Point3D xAxis;

		/**
		 * <i><b> j </b></i> component.
		 */
		private Point3D yAxis;

		/**
		 * Eye direction.
		 */
		private Point3D zAxis;

		/**
		 * Position of screen corner.
		 */
		private Point3D screenCorner;

		/**
		 * Graphical scene.
		 */
		private Scene scene;

		/**
		 * Boolean flag which tells whether painting is necessary
		 */
		private AtomicBoolean cancel;

		/**
		 * Constant representing maximum treshold when creating new
		 * {@link RecursiveAction}.
		 */
		private static final int TRESHOLD = 16;

		/**
		 * Constructor used for initializing this action.
		 * 
		 * @param yMin         minimum y component.
		 * @param yMax         maximum y component.
		 * @param width        frame's width.
		 * @param height       frame's height.
		 * @param horizontal   horizontal line.
		 * @param vertical     vertical line.
		 * @param red          array for red color.
		 * @param green        array for green color.
		 * @param blue         array for blue color.
		 * @param eye          eye position.
		 * @param xAxis        i component.
		 * @param yAxis        j component.
		 * @param zAxis        eye direction.
		 * @param screenCorner screen corner position.
		 * @param scene        scene.
		 * @param cancel       boolean flag.
		 */
		public CasterAction(int yMin, int yMax, int width, int height, double horizontal, double vertical, short[] red,
				short[] green, short[] blue, Point3D eye, Point3D xAxis, Point3D yAxis, Point3D zAxis,
				Point3D screenCorner, Scene scene, AtomicBoolean cancel) {
			this.yMin = yMin;
			this.yMax = yMax;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.eye = eye;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.zAxis = zAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.cancel = cancel;
		}

		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= TRESHOLD) {
				computeDirect();
				return;
			}
			int limiter = yMin + (yMax - yMin) / 2;

			invokeAll(
					new CasterAction(yMin, limiter, width, height, horizontal, vertical, red, green, blue, eye, xAxis,
							yAxis, zAxis, screenCorner, scene, cancel),
					new CasterAction(limiter + 1, yMax, width, height, horizontal, vertical, red, green, blue, eye,
							xAxis, yAxis, zAxis, screenCorner, scene, cancel));
		}

		/**
		 * Method used for executing this action.
		 */
		private void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get()) {
					break;
				}
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1.0))
							.sub(yAxis.scalarMultiply(y * vertical / (height - 1.0))));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > MAX_COLOR_VALUE ? MAX_COLOR_VALUE : rgb[0];
					green[offset] = rgb[1] > MAX_COLOR_VALUE ? MAX_COLOR_VALUE : rgb[1];
					blue[offset] = rgb[2] > MAX_COLOR_VALUE ? MAX_COLOR_VALUE : rgb[2];
					offset++;
				}
			}
		}
	}

	/**
	 * Method used for tracing which pixel needs to be colored.
	 * 
	 * @param scene scene.
	 * @param ray   ray.
	 * @param rgb   an array rgb.
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = rgb[1] = rgb[2] = MIN_COLOR_VALUE;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		determineColorFor(scene, ray, closest, rgb);
	}

	/**
	 * Method used for finding closest intersection of <code>ray</code> with
	 * {@link GraphicalObject} object.
	 * 
	 * @param scene scene.
	 * @param ray   ray.
	 * @return new instance of {@link RayIntersection} or <code>null</code> if there
	 *         is not any interesection.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection closestIntersection = null;
		for (GraphicalObject object : objects) {
			RayIntersection current = object.findClosestRayIntersection(ray);
			if (current == null) {
				continue;
			}
			if (closestIntersection == null || current.getDistance() < closestIntersection.getDistance()) {
				closestIntersection = current;
			}
		}
		return closestIntersection;
	}

	/**
	 * Helper method used for determing color for intersection <code>s</code>.
	 * 
	 * @param scene scene.
	 * @param ray   ray.
	 * @param s     ray intersection.
	 * @param rgb   an array rgb.
	 */
	private static void determineColorFor(Scene scene, Ray ray, RayIntersection s, short[] rgb) {
		rgb[0] = rgb[1] = rgb[2] = AMBIENTAL_COLOR_VALUE;
		List<LightSource> sources = scene.getLights();
		for (LightSource source : sources) {
			Point3D sourcePoint = source.getPoint();
			Ray ray1 = Ray.fromPoints(sourcePoint, s.getPoint());
			RayIntersection s1 = findClosestIntersection(scene, ray1);
			if (s1 != null) {
				double sDistance = sourcePoint.sub(s.getPoint()).norm();
				double s1Distance = sourcePoint.sub(s1.getPoint()).norm();
				if (abs(s1Distance - sDistance) <= DELTA) {
					diffuseComponent(s1, source, rgb);
					reflectiveComponent(s1, ray, source, rgb);
				}
			}
		}
	}

	/**
	 * Calculates diffuse component for <code>s</code>.
	 * 
	 * @param s      ray intersection.
	 * @param source source.
	 * @param rgb    an array rgb.
	 */
	private static void diffuseComponent(RayIntersection s, LightSource source, short[] rgb) {
		Point3D l = source.getPoint().sub(s.getPoint()).normalize();
		Point3D n = s.getNormal();
		double max = max(l.scalarProduct(n), 0);

		rgb[0] += source.getR() * s.getKdr() * max;
		rgb[1] += source.getG() * s.getKdg() * max;
		rgb[2] += source.getB() * s.getKdb() * max;

	}

	/**
	 * Calculates reflective component for <code>s</code>.
	 * 
	 * @param s      ray intersection.
	 * @param ray    ray.
	 * @param source source.
	 * @param rgb    an array rgb.
	 */
	private static void reflectiveComponent(RayIntersection s, Ray ray, LightSource source, short[] rgb) {
		Point3D l = source.getPoint().sub(s.getPoint());
		Point3D n = s.getNormal();
		Point3D projectionln = n.scalarMultiply(l.scalarProduct(n));
		Point3D r = projectionln.scalarMultiply(2.0).sub(l).normalize();
		Point3D v = ray.start.sub(s.getPoint()).normalize();

		double product = r.scalarProduct(v);

		if (product >= 0) {
			product = pow(product, s.getKrn());
			rgb[0] += source.getR() * s.getKrr() * product;
			rgb[1] += source.getG() * s.getKrg() * product;
			rgb[2] += source.getB() * s.getKrb() * product;
		}
	}

}
