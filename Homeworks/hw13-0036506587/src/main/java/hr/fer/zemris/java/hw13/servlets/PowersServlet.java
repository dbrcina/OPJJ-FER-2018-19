package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * <p>
 * Servlet implementation which creates an <i>.xls</i> document.<br>
 * Each sheet in that .xls document consist of two columns where first column is
 * filled with numbers from <i>a</i> to <i>b</i> and second with <i>i-th</i>
 * powers of each number from first column.<br>
 * <i>i-th</i> power is determined by number of current sheet.
 * </p>
 * 
 * <p>
 * It takes three arguments from <i>URL</i>:
 * <li><i>a</i> - first number</li>
 * <li><i>b</i> - last number</li>
 * <li><i>n</i> - number of sheets</li>
 * </p>
 * 
 * <p>
 * Numbers <i>a</i> and <i>b</i> need to be from [{@value #MIN_INT_VALUE} ,
 * {@value #MAX_INT_VALUE}].<br>
 * Number <i>n</i> needs to be from [{@value #MIN_EXP_VALUE},
 * {@value #MAX_EXP_VALUE}].
 * </p>
 * 
 * If any of the parameters is not provided or is invalid, this servlet
 * delegates error page with appropriate error message to user.
 * 
 * @author dbrcina
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Minimum value for int parameter.
	 */
	private static final int MIN_INT_VALUE = -100;
	/**
	 * Maximum value for int parameter.
	 */
	private static final int MAX_INT_VALUE = 100;
	/**
	 * Minimum value for exponent parameter.
	 */
	private static final int MIN_EXP_VALUE = 1;
	/**
	 * Maximum value for exponent parameter.
	 */
	private static final int MAX_EXP_VALUE = 5;
	/**
	 * Error message thrown when int value is invalid.
	 */
	private static final String INT_INTERVAL_ERROR = "[" + MIN_INT_VALUE + ", " + MAX_INT_VALUE + "]";
	/**
	 * Error message thrown when exponent value is invalid.
	 */
	private static final String EXPO_INTERVAL_ERROR = "[" + MIN_EXP_VALUE + ", " + MAX_EXP_VALUE + "]";
	/**
	 * Excel document name.
	 */
	private static final String FILENAME = "table.xls";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;
		try {
			a = parseParameter(req.getParameter("a"), "a", i -> i < MIN_INT_VALUE || i > MAX_INT_VALUE);
			b = parseParameter(req.getParameter("b"), "b", i -> i < MIN_INT_VALUE || i > MAX_INT_VALUE);
			n = parseParameter(req.getParameter("n"), "n", i -> i < MIN_EXP_VALUE || i > MAX_EXP_VALUE);
		} catch (Exception e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename = \"" + FILENAME + "\"");

		HSSFWorkbook hwb = createExcelFile(a, b, n);
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

	/**
	 * Helper method used for creation of <i>.xls</i> document.
	 * 
	 * @param a number a.
	 * @param b number b.
	 * @param n number n.
	 * @return new instance of {@link HSSFWorkbook}
	 * @see {@link PowersServlet} for further documentation.
	 */
	private HSSFWorkbook createExcelFile(Integer a, Integer b, Integer n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			// create i-th sheet
			HSSFSheet sheet = hwb.createSheet(i + "-th power");

			// create head row
			HSSFRow rowhead = sheet.createRow(0);
			CellStyle cellStyle = hwb.createCellStyle();
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);

			// fill first cell
			Cell cell = rowhead.createCell(0);
			cell.setCellValue("x");
			cell.setCellStyle(cellStyle);

			// fill second sell
			cell = rowhead.createCell(1);
			cell.setCellValue("x ^ " + i);
			cell.setCellStyle(cellStyle);

			// fill in the rest of the table
			for (int x = a, j = 1; x <= b; x++, j++) {
				HSSFRow row = sheet.createRow(j);
				row.createCell(0, CellType.NUMERIC).setCellValue(x);
				row.createCell(1, CellType.NUMERIC).setCellValue(Math.pow(x, i));
			}

			sheet.autoSizeColumn(1);
		}
		return hwb;
	}

	/**
	 * Helper method used for parsing provided parameter.<br>
	 * If <code>parName</code> is <code>null</code> or <code>parName</code> cannot
	 * be parsed into int value, an appropriate message is returned.<br>
	 * If parsed int value is not in right boundaries, an appropriate message is
	 * returned.
	 * 
	 * @param parName string representation of parameter.
	 * @param literal parameter literal.
	 * @param tester  used for testing boundaries.
	 * @return parsed int value as a result.
	 * @throws Exception if something is invalid.
	 */
	private Integer parseParameter(String parName, String literal, Predicate<Integer> tester) throws Exception {
		int par = 0;
		// check if null
		if (parName == null) {
			throw new Exception("Parameter \"" + literal + "\" was not provided!");
		}
		// parse
		try {
			par = Integer.parseInt(parName);
		} catch (NumberFormatException e) {
			throw new Exception("Parameter \"" + literal + "\" cannot be parsable as a number!");
		}
		// check boundaries
		if (tester.test(par)) {
			throw new Exception("Parameter \"" + literal + "\" needs to be from "
					+ (literal.equals("n") ? EXPO_INTERVAL_ERROR : INT_INTERVAL_ERROR) + "!");
		}

		return par;
	}
}
