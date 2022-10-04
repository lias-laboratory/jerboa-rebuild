package fr.ensma.lias.jerboa.core.utils.printer;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecifications;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.SpecificationEntry;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

/**
 * JSONPrinterTest
 */
public class JSONPrinterTest {

	private ModelerGenerated modeler = new ModelerGenerated();

	public JSONPrinterTest() throws JerboaException {}

	@Test
	public void test_HistoryRecord_import_specParam_from_JSON()
			throws IOException, JerboaException {

		ParametricSpecifications spec = JSONPrinter.importParametricSpecification(
				"ParametricSpecification_insert-vertex-created-edge.json", modeler);
		assertEquals(4, spec.getSpec().size());
	}

	private HistoryRecord setupHistoryRecord(String path, int specEntryIndex, int pNameIndex,
			int pIdIndex) throws IOException, JerboaException {

		try {
			ParametricSpecifications paramSpec =
					JSONPrinter.importParametricSpecification(path, modeler);
			SpecificationEntry entry = paramSpec.getSpecEntry(specEntryIndex);

			PersistentName PN = entry.getPNs().get(pNameIndex);
			PersistentID PI = PN.getPIs().get(pIdIndex);
			JerboaOrbit orbitType = PN.getOrbitType();
			return new HistoryRecord(PI, orbitType, paramSpec);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file by `path`");
			return null;
		}
	}

	@Test
	public void test_HistoryRecord_export() throws IOException, JerboaException {
		// HistoryRecord hr = setupHistoryRecord(
		// "ParametricSpecification_triangulate-triangulated-face.json",
		// 3, 0, 0);
		// hr.export();
		// TODO: levelOrbit now points to list of levelEvents
		HistoryRecord hr = setupHistoryRecord(
				"ParametricSpecification_insert-vertex-created-edge.json", 4, 0, 0);
		hr.export("HR-insert-vertex-created-edge.json");
		// hr = setupHistoryRecord("case1.json", 3, 0, 0);
		// hr.export("HR-case1.json");
		// hr = setupHistoryRecord("case2.json", 4, 0, 0);
		// hr.export("HR-case2.json");
		hr = setupHistoryRecord("case3-but-triangulated.json", 5, 0, 0);
		hr.export("HR-case3.json");
		// hr = setupHistoryRecord("case4.json", 3, 0, 0);
		// hr.export("HR-case4.json");
		hr = setupHistoryRecord("case3-alt.json", 5, 0, 0);
		hr.export("HR-case3-alt.json");
	}

}
