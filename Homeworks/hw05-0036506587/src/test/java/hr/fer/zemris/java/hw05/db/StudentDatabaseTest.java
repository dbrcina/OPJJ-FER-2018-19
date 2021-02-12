package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	private StudentDatabase db = new StudentDatabase(loader());

	@Test
	public void testForJMBAG() {
		assertEquals("Bosnić", db.forJMBAG("0000000003").getLastName());
		assertEquals("Ana", db.forJMBAG("0000000023").getFirstName());
		assertEquals("Krušelj Posavec", db.forJMBAG("0000000031").getLastName());
		assertEquals("Bojan", db.forJMBAG("0000000031").getFirstName());
		assertEquals("4", db.forJMBAG("0000000031").getFinalGrade());
		assertEquals(null, db.forJMBAG("11111111"));
	}

	@Test
	public void testFilterFalse() {
		List<StudentRecord> records = db.filter(record -> false);
		assertEquals(0, records.size());
	}

	@Test
	public void testFilterTrue() {
		List<StudentRecord> records = db.filter(record -> true);
		assertEquals(63, records.size());
	}

	private List<String> loader() {
		List<String> records = new ArrayList<>();
		try {
			records = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}

}
