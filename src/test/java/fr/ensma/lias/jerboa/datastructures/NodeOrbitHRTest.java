package fr.ensma.lias.jerboa.datastructures;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

/**
 * NodeOrbitHRTest
 */
public class NodeOrbitHRTest {

	private ModelerGenerated modeler = new ModelerGenerated();

	public NodeOrbitHRTest() throws JerboaException {

	}

	@Test
	/**
	 * Test if a BB build entry is valid.
	 *
	 * Settings are: nodeName=n1; currentNodeOrbit=(0,2); rule=TriangulateFace;
	 * levelEvent=LevelEvent's instance
	 */
	public void test_BBBuildEntry_TriangulateFace_creation() throws JerboaException {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("TriangulateFace");
		NodeOrbit nodeOrbit = new NodeOrbit(JerboaOrbit.orbit(0, 2));
		String nodeName = "n1";
		LevelEventHR levelEvent = new LevelEventHR(3, null);
		List<NodeOrbit> result = nodeOrbit.BBBuildEntry(nodeName, rule, levelEvent,
				new ArrayList<NodeOrbit>(), false);
		assertEquals(1, result.size());
		assertEquals(JerboaOrbit.orbit(1), result.get(0).getOrbit());
		assertEquals(1, levelEvent.getEventList().size());
		assertEquals(Event.CREATION, levelEvent.getEventList().get(0).getEvent());
	}

	@Test
	/*
	 * Test if the method addNodes performs the merge correctly
	 */
	public void test_addNodes_addNodeWithSameOrbit_but_withDifferentChildren() {
		NodeEvent nodeEventA = new NodeEvent(Event.NOEFFECT);
		NodeEvent nodeEventB = new NodeEvent(Event.SPLIT);
		// LevelEventHR levelEventC = new LevelEventHR(1, null);
		// levelEventC.addEvent(new NodeEventHR(Event.CREATION));
		NodeOrbit nodeOrbitA = new NodeOrbit(JerboaOrbit.orbit(0, 1));
		NodeOrbit nodeOrbitB = new NodeOrbit(JerboaOrbit.orbit(0, 1));
		nodeOrbitA.addChild(new Link(LinkType.TRACE, nodeEventA));
		nodeOrbitB.addChild(new Link(LinkType.TRACE, nodeEventB));

		int expected_size = 1;
		NodeOrbit expected_node = new NodeOrbit(JerboaOrbit.orbit(0, 1));
		expected_node.addChild(new Link(LinkType.TRACE, nodeEventA));
		expected_node.addChild(new Link(LinkType.TRACE, nodeEventB));
		List<NodeOrbit> expected_list = new ArrayList<>();
		expected_list.add(expected_node);

		List<NodeOrbit> list = new ArrayList<>();

		NodeOrbit.addNodes(nodeOrbitA, list);
		assertEquals(expected_size, list.size());
		NodeOrbit.addNodes(nodeOrbitB, list);

		assertEquals(expected_size, list.size());
		assertEquals(expected_list, list);

		// TODO: show equality through children
	}

}
