package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.*;

/**
 * Model of one {@link GraphicalObject} - Sphere.
 * 
 * @author dbrcina
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Constant used when comparing two doubles.
	 */
	private static final double DELTA = 1E-9;

	/**
	 * Sphere's center.
	 */
	private Point3D center;

	/**
	 * Sphere's radius.
	 */
	private double radius;

	/**
	 * Coeficient for diffuse red component.
	 */
	private double kdr;

	/**
	 * Coeficient for diffuse green component.
	 */
	private double kdg;

	/**
	 * Coeficient for diffuse blue component.
	 */
	private double kdb;

	/**
	 * Coeficient for reflective red component.
	 */
	private double krr;

	/**
	 * Coeficient for reflective green component.
	 */
	private double krg;

	/**
	 * Coeficient for reflective blue component.
	 */
	private double krb;

	/**
	 * Shininess factor.
	 */
	private double krn;

	/**
	 * Constructor used for initialization of this Sphere.
	 * 
	 * @param center center.
	 * @param radius radius.
	 * @param kdr    diffuse component for red color.
	 * @param kdg    diffuse component for green color.
	 * @param kdb    diffuse component for blue color.
	 * @param krr    reflective component for red color.
	 * @param krg    reflective component for green color.
	 * @param krb    reflective component for blue color.
	 * @param krn    shininess factor.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D origin = ray.start;
		Point3D direction = ray.direction;
		double a = pow(direction.norm(), 2);
		double b = 2 * direction.scalarProduct(origin.sub(center));
		double c = pow(origin.sub(center).norm(), 2) - radius * radius;
		double discrim = b * b - 4 * a * c;

		if (discrim < 0) {
			return null;
		}

		double dist1 = (-b - sqrt(discrim)) / (2 * a);
		double dist2 = (-b + sqrt(discrim)) / (2 * a);

		if (dist1 < 0 && dist2 < 0) {
			return null;
		}

		double minDistance;
		if (abs(dist1 - dist2) < DELTA) {
			minDistance = dist2;
		} else {
			minDistance = dist1;
		}

		Point3D intersect = origin.add(direction.scalarMultiply(minDistance));

		return new RayIntersection(intersect, abs(minDistance), true) {

			@Override
			public Point3D getNormal() {
				return intersect.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
