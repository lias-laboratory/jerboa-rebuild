package fr.ensma.lias.jerboa.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import up.jerboa.exception.JerboaException;

/**
 * NodeEventHRTest
 */
public class NodeEventHRTest {

	public NodeEventHRTest() throws JerboaException {}

	@Test
	public void test_NodeEvent_equality_true() {

		NodeEventHR nodeA = new NodeEventHR(Event.CREATION);
		NodeEventHR nodeB = new NodeEventHR(Event.CREATION);
		assertEquals(nodeA, nodeB);

	}

	@Test
	public void test_NodeEvent_equality_false() {

		NodeEventHR nodeA = new NodeEventHR(Event.CREATION);
		NodeEventHR nodeB = new NodeEventHR(Event.SPLIT);
		assertNotEquals(nodeA, nodeB);

	}


}
