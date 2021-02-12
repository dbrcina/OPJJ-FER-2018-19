package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * This class models one student record.
 * 
 * @author dbrcina
 *
 */
public class StudentRecord {

	/**
	 * Student's jmbag.
	 */
	private String jmbag;

	/**
	 * Student's last name.
	 */
	private String lastName;

	/**
	 * Student's first name.
	 */
	private String firstName;

	/**
	 * Student's points on mi exam.
	 */
	private double miPoints;

	/**
	 * Student's points on zi exam.
	 */
	private double ziPoints;

	/**
	 * Student's lab points.
	 */
	private double labPoints;

	/**
	 * Student's final grade.
	 */
	private int grade;

	/**
	 * Constructor used for creating a new instance of {@link StudentRecord}.
	 * 
	 * @param jmbag     jmbag.
	 * @param lastName  last name.
	 * @param firstName first name.
	 * @param miPoints  mi points.
	 * @param ziPoinst  zi points.
	 * @param labPoints lab points.
	 * @param grade     grade.
	 * @throws NullPointerException if {@code jmbag}, {@code lastName} or
	 *                              {@code firstName} is {@code null}.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double miPoints, double ziPoints,
			double labPoints, int grade) {
		this.jmbag = Objects.requireNonNull(jmbag, "Jmbag cannot be null!");
		this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null!");
		this.firstName = Objects.requireNonNull(firstName, "First name cannot be null!");
		this.miPoints = miPoints;
		this.ziPoints = ziPoints;
		this.labPoints = labPoints;
		this.grade = grade;
	}

	/**
	 * Getter for {@link #jmbag}
	 * 
	 * @return {@link #jmbag}.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for {@link #lastName}.
	 * 
	 * @return {@link #lastName}.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for {@link #firstName}.
	 * 
	 * @return {@link #firstName}.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for {@link #miPoints}.
	 * 
	 * @return {@link #miPoints}.
	 */
	public double getMiPoints() {
		return miPoints;
	}

	/**
	 * Getter for {@link #ziPoints}.
	 * 
	 * @return {@link #ziPoints}.
	 */
	public double getZiPoints() {
		return ziPoints;
	}

	/**
	 * Getter for {@link #labPoints}.
	 * 
	 * @return {@link #labPoints}.
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * Getter for {@link #grade}.
	 * 
	 * @return {@link #grade}.
	 */
	public int getGrade() {
		return grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" 
				+ miPoints + "\t" + ziPoints + "\t" + labPoints + "\t" + grade;
	}
}
