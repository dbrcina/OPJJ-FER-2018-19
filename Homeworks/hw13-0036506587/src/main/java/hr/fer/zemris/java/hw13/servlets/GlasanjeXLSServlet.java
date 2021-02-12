package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedMap;

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

import hr.fer.zemris.java.hw13.FileClass;
import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * Servlet implementing a simple <i>XLS</i> document creator with the help of
 * {@link HSSFWorkbook} class and its package.
 * 
 * @author dbrcina
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Definition file.
	 */
	private static final String DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";
	/**
	 * Results file.
	 */
	private static final String RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";
	/**
	 * <i>.xls</i> document name.
	 */
	private static final String FILENAME = "Voting results.xls";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename = \"" + FILENAME + "\"");

		// results path
		String resultsName = req.getServletContext().getRealPath(RESULTS_PATH);
		Path resultsPath = Paths.get(resultsName);
		// load bands
		SortedMap<String, Band> bands = FileClass.loadBands(req.getServletContext().getRealPath(DEFINITION_PATH));

		// create results file if it does not exist
		if (!Files.exists(resultsPath)) {
			FileClass.createResultsFile(resultsName, bands);
		}

		// read all lines and sort them by number of votes
		Map<String, Integer> results = FileClass.validateResults(resultsPath, bands);

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
	private HSSFWorkbook createExcelFile(Map<String, Integer> results) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Voting results");

		CellStyle cellStyle = hwb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);

		// first row
		fillFirstRow(sheet.createRow(0), cellStyle);

		int r = 1;
		for (Map.Entry<String, Integer> entry : results.entrySet()) {
			HSSFRow row = sheet.createRow(r++);
			row.createCell(0, CellType.NUMERIC).setCellValue(entry.getKey());
			row.createCell(1, CellType.NUMERIC).setCellValue(entry.getValue());
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
		Cell cell = rowhead.createCell(0, CellType.STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Band name");

		cell = rowhead.createCell(1, CellType.STRING);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Number of votes");
	}
}
