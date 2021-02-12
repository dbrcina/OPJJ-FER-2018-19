package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents whole expression that needs to be evaluated. Expression
 * consist of one {@link FieldValueGetters} type getter, one {@link String} type
 * pattern and one {@link ComparisonOperators} type operator.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ConditionalExpression {

	/**
	 * Variable that stores {@link StudentRecord} field value.
	 */
	private IFieldValueGetter fieldValueGetter;

	/**
	 * Pattern that needs to be checked.
	 */
	private String pattern;

	/**
	 * Represents one of the operators from {@link ComparisonOperators} that needs
	 * to be used in an operation.
	 */
	private IComparisonOperator operator;

	/**
	 * Constructor that initializes fields of this class.
	 * 
	 * @param fieldValueGetter getter for one of the {@link StudentRecord} fields.
	 * @param pattern          pattern that needs to be tested.
	 * @param oper             operator from {@link ComparisonOperators}
	 * @throws NullPointerException if one of the given argurment is {@code null}.
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String pattern, IComparisonOperator operator) {
		this.fieldValueGetter = Objects.requireNonNull(fieldValueGetter, "Getter ne smije biti null!");
		this.pattern = Objects.requireNonNull(pattern, "Patern ne smije biti null!");
		this.operator = Objects.requireNonNull(operator, "Operator ne smije biti null!");
	}

	/**
	 * Getter for {@link #fieldValueGetter}.
	 * 
	 * @return {@link #fieldValueGetter}.
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Getter for {@link #pattern}.
	 * 
	 * @return {@link #pattern}.
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Getter for {@link #operator}.
	 * 
	 * @return {@link #operator}.
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}

}
