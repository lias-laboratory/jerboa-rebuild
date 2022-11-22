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


	private HistoryRecord setupHistoryRecord(String path, int applicationID,
			int persistentNameIndex, int persistentIDIndex) throws IOException, JerboaException {

		try {
			ParametricSpecification parametricSpecification =
					JSONPrinter.importParametricSpecification(path, modeler);
			Application application = parametricSpecification.getApplication(applicationID);

			PersistentName PN = application.getPersistentNames().get(persistentNameIndex);
			PersistentID PI = PN.getPIs().get(persistentIDIndex);
			JerboaOrbit orbitType = PN.getOrbitType();
			return new HistoryRecord(PI, orbitType, parametricSpecification);
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

		Map<Integer, List<LevelEventHR>> hrMap = HR.getLeaves();
		assertEquals(3, hrMap.size());

		for (Map.Entry<Integer, List<LevelEventHR>> entry : hrMap.entrySet()) {
			List<LevelEventHR> levels = entry.getValue();
			for (LevelEventHR level : levels) {
				assert (level.getEventList().stream()
						.allMatch(event -> event.getEvent() == Event.CREATION));
			}
		}
	}

	@Test
	public void test_HistoryRecord_construction_with_split() throws IOException, JerboaException {

		HistoryRecord HR = setupHistoryRecord(
				"ParametricSpecification_triangulate-triangulated-face.json", 3, 0, 0);

		Map<Integer, List<LevelEventHR>> hrMap = HR.getLeaves();
		assertEquals(2, hrMap.size());

		assertEquals(Event.CREATION, hrMap.get(1).get(0).getEventList().get(0).getEvent());
		assertEquals(Event.CREATION, hrMap.get(1).get(0).getEventList().get(1).getEvent());

		assertEquals(Event.SPLIT, hrMap.get(2).get(0).getEventList().get(0).getEvent());
	}

}
