package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

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

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet implementing a simple <i>XLS</i> document creator with the help of
 * {@link HSSFWorkbook} class and its package.<br>
 *
 * Document consist of voting results of certain poll which is stored in current
 * session.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <i>.xls</i> document name.
	 */
	private static final String FILENAME = "Rezultati glasanja.xls";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename = \"" + FILENAME + "\"");

		long pollID = ((Poll) req.getSession().getAttribute(AttributesConstants.POLL)).getPollID();

		List<PollOption> results = DAOProvider.getDAO().getPollOptions(pollID, "pollID");
		results.sort(Comparator.comparing(PollOption::getVotesCount).reversed());

		// create xls document and write it to stream
		HSSFWorkbook hwb = createExcelFile(results);
		hwb.write(resp.getOutputStream());
		hwb.close();
	}

	/**
	 * Factory method used for creating a simple <i>XLS</i> document with two
	 * columns from <code>results</code>.
	 * 
	 * @param results results.
	 * @return new instance of {@link HSSFWorkbook}.
	 */
	private HSSFWorkbook createExcelFile(List<PollOption> results) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati glasanja");

		// first row
		fillFirstRow(sheet.createRow(0), hwb.createCellStyle());

		int r = 1;
		for (PollOption pollOption : results) {
			HSSFRow row = sheet.createRow(r++);
			row.createCell(0).setCellValue(pollOption.getOptionTitle());
			row.createCell(1).setCellValue(pollOption.getVotesCount());
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		return hwb;
	}

	/**
	 * Fills first row in <i>XLS</i> document.
	 * 
	 * @param rowhead   row head.
	 * @param cellStyle cell style.
	 */
	private void fillFirstRow(HSSFRow rowhead, CellStyle cellStyle) {
		Cell cell = rowhead.createCell(0);
		cell.setCellValue("Ime natjecatelja");

		cell = rowhead.createCell(1);
		cell.setCellValue("Broj glasova");
	}
}
