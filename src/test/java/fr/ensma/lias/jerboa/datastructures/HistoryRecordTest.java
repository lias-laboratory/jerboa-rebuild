package fr.ensma.lias.jerboa.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

/**
 * HistoryRecordTest
 */
public class HistoryRecordTest {

	private ModelerGenerated modeler = new ModelerGenerated();

	public HistoryRecordTest() throws JerboaException {}


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
	public void test_HistoryRecord_construction_full_creation()
			throws IOException, JerboaException {

		HistoryRecord HR = setupHistoryRecord(
				"ParametricSpecification_createface-insertvertex-triangulate-insertvertex_Test.json",
				4, 0, 0);

		HashMap<Integer, List<LevelEventHR>> hrMap = HR.getLeaves();
		assertEquals(3, hrMap.size());

		for (Map.Entry<Integer, List<LevelEventHR>> entry : hrMap.entrySet()) {
			List<LevelEventHR> levels = entry.getValue();
			for (LevelEventHR level : levels) {
				assert (level.getEventList().stream()
						.allMatch(event -> event.event == Event.CREATION));
			}
		}
	}

	@Test
	public void test_HistoryRecord_construction_with_split() throws IOException, JerboaException {

		HistoryRecord HR = setupHistoryRecord(
				"ParametricSpecification_triangulate-triangulated-face.json", 3, 0, 0);

		HashMap<Integer, List<LevelEventHR>> hrMap = HR.getLeaves();
		assertEquals(2, hrMap.size());

		assertEquals(Event.CREATION, hrMap.get(1).get(0).getEventList().get(0).getEvent());
		assertEquals(Event.CREATION, hrMap.get(1).get(0).getEventList().get(1).getEvent());

		assertEquals(Event.SPLIT, hrMap.get(2).get(0).getEventList().get(0).getEvent());
	}

}
