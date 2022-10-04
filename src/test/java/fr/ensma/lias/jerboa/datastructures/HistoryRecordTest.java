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

	public HistoryRecordTest() throws JerboaException {
		System.out.println("HistoryRecordTest:");
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

	// @Test
	// public void test_HistoryRecord_construction_full_creation()
	// throws IOException, JerboaException {

	// HistoryRecord HR = setupHistoryRecord(
	// "ParametricSpecification_insert-vertex-created-edge.json", 4, 0, 0);

	// assertNotNull(HR.getLeaves().keySet());
	// assertEquals(1, HR.getLeaves().size());
	// LevelEventHR leaf = HR.getLeaf(0);
	// System.out.println("leaf: " + leaf);
	// for (LevelEventHR level : leaf) {
	// assertEquals(Event.CREATION, level.getEventList().get(0).getEvent());
	// }
	// }

	// @Test
	// public void test_HistoryRecord_construction_with_split() throws IOException, JerboaException
	// {

	// HistoryRecord HR =
	// setupHistoryRecord("ParametricSpecification_triangulate-triangulated-face.json", 3, 0, 0);

	// assertNotNull(HR);
	// LevelEventHR leaf = HR.getLeaf(0);

	// assertEquals(Event.CREATION, leaf.getEventList().get(0).getEvent());
	// assertEquals(Event.CREATION, leaf.getEventList().get(1).getEvent());

	// leaf = leaf.getNextLevelOrbitHR().getNextLevelEventHRAtIndex(0);
	// assertEquals(Event.SPLIT, leaf.getEventList().get(0).getEvent());

	// }

	// @Test
	// public void test_Case3() throws IOException, JerboaException {
	// HistoryRecord HR = setupHistoryRecord("case3-but-triangulated.json", 5, 0, 0);
	// assertNotNull(HR);
	// LevelEventHR leaf = HR.getLeaf(0);
	// // for (LevelEventHR level : leaf) {
	// // System.out.println(level);
	// // }
	// assertEquals(3, leaf.getEventList().size());

	// }

}
