package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class represents query filter. It takes a list of queries and does the
 * filtering for {@link StudentDatabase}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of {@link ConditionalExpression}.
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Constructor that takes a list of {@link ConditionalExpression}
	 * <code>expressiong</code>.
	 * 
	 * @param expressions {@link ConditionalExpression} expressions.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			if (expression.getFieldValueGetter() == FieldValueGetters.JMBAG) {
				if (!expression.getOperator().satisfied(record.getJmbag(), expression.getPattern())) {
					return false;
				}
			} else if (expression.getFieldValueGetter() == FieldValueGetters.FIRST_NAME) {
				if (!expression.getOperator().satisfied(record.getFirstName(), expression.getPattern())) {
					return false;
				}
			} else if (expression.getFieldValueGetter() == FieldValueGetters.LAST_NAME) {
				if (!expression.getOperator().satisfied(record.getLastName(), expression.getPattern())) {
					return false;
				}
			} else {
				throw new IllegalArgumentException("Nepoznati field name!");
			}
		}
		return true;
	}

}
