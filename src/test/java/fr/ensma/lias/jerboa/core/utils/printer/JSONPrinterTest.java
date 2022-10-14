package fr.ensma.lias.jerboa.core.utils.printer;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.Application;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

/**
 * JSONPrinterTest
 */
public class JSONPrinterTest {

	private ModelerGenerated modeler = new ModelerGenerated();

	public JSONPrinterTest() throws JerboaException {}


	private HistoryRecord setupHistoryRecord(String path, int specEntryIndex, int pNameIndex,
			int pIdIndex) throws IOException, JerboaException {

		try {
			ParametricSpecification paramSpec =
					JSONPrinter.importParametricSpecification(path, modeler);
			Application entry = paramSpec.getApplication(specEntryIndex);

			PersistentName PN = entry.getPersistentNames().get(pNameIndex);
			PersistentID PI = PN.getPIs().get(pIdIndex);
			JerboaOrbit orbitType = PN.getOrbitType();
			return new HistoryRecord(PI, orbitType, paramSpec);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file by `path`");
			return null;
		}
	}

	@Test
	public void test_HistoryRecord_import_specParam_from_JSON()
			throws IOException, JerboaException {

		ParametricSpecification spec = JSONPrinter.importParametricSpecification(
				"ParametricSpecification_createface-insertvertex-triangulate-insertvertex_Test.json",
				modeler);
		assertEquals(4, spec.getParametricSpecification().size());
	}

	@Test
	public void test_HistoryRecord_export_HistoryRecord_to_JSON()
			throws IOException, JerboaException {
		String exportPath = "./src/test/resources";
		String fileName = "HR_createface-insertvertex-triangulate-insertvertex_Generated.json";
		HistoryRecord hrToExport = setupHistoryRecord(
				"ParametricSpecification_createface-insertvertex-triangulate-insertvertex_Test.json",
				4, 0, 0);
		assert (hrToExport != null);
		hrToExport.export(exportPath, fileName);
		File f = new File(exportPath + "/" + fileName);
		assert (f.exists() && !f.isDirectory());
	}

}
