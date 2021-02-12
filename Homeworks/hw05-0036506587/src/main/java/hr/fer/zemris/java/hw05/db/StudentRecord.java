package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents a student record.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class StudentRecord {

	/**
	 * Constant for minimum grade.
	 */
	private static final int MIN_GRADE = 1;

	/**
	 * Constant for maximum grade.
	 */
	private static final int MAX_GRADE = 5;

	/**
	 * Student's identification.
	 */
	private final String jmbag;

	/**
	 * Student's name.
	 */
	private final String firstName;

	/**
	 * Student's last name.
	 */
	private final String lastName;

	/**
	 * Student's final grade.
	 */
	private String finalGrade;

	/**
	 * Constructor that initializes student's record.
	 * 
	 * @param jmbag      student's jmbag
	 * @param firstName  student's name.
	 * @param lastName   student's last name.
	 * @param finalGrade student's final grade.
	 * @throws NullPointerException     if {@code lastName} or {@code firstName} is
	 *                                  {@code null}.
	 * @throws IllegalArgumentException if {@code finalGrade} is not from
	 *                                  {@link #MIN_GRADE} inclusive to
	 *                                  {@link #MAX_GRADE} inclusive.
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, String finalGrade) {
		this.jmbag = Objects.requireNonNull(jmbag, "Jmbag ne smije biti null");
		this.firstName = Objects.requireNonNull(firstName, "Ime ne smije biti null!");
		this.lastName = Objects.requireNonNull(lastName, "Prezime ne smiji biti null!");

		int grade = Integer.parseInt(finalGrade);
		if (grade < MIN_GRADE || grade > MAX_GRADE) {
			throw new IllegalArgumentException("Ocjena nije iz intervala [" + MIN_GRADE + ", " + MAX_GRADE + "]!");
		}

		this.finalGrade = finalGrade;
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
		return jmbag.equals(other.jmbag);
	}

	/**
	 * Getter for student's jmbag.
	 * 
	 * @return {@link #jmbag}.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for student's name.
	 * 
	 * @return {@link #firstName}.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for student's last name.
	 * 
	 * @return {@link #lastName}.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for student's final grade.
	 * 
	 * @return {@link #finalGrade}.
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

}
