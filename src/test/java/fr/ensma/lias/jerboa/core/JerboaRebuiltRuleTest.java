package fr.ensma.lias.jerboa.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

/**
 * JerboaRebuiltRuleTest
 */
public class JerboaRebuiltRuleTest {

	ModelerGenerated modeler = new ModelerGenerated();

	public JerboaRebuiltRuleTest() throws JerboaException {}

	// @Test
	/*
	 * Test isRuleNodesOrbitCreated method. True is expected.
	 *
	 * @throws JerboaException
	 */
	// public void test_creation_case_InsertVertex_alternative_unfolded_true()
	// throws JerboaException {
	// JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");

	// int ruleNodeIndex = rule.getRightIndexRuleNode("n2");
	// List<JerboaRuleNode> ruleNodesOrbit =
	// JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex),
	// JerboaOrbit.orbit(1, 2, 3));

	// assertTrue(rule.isRuleNodesOrbitCreated(ruleNodesOrbit));
	// }

	// @Test
	/*
	 * Test isRuleNodesOrbitCreated method. False is expected.
	 *
	 * @throws JerboaException
	 */
	// public void test_creation_case_InsertVertex_alternative_unfolded_false()
	// throws JerboaException {
	// JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");

	// int ruleNodeIndex = rule.getRightIndexRuleNode("n0");
	// List<JerboaRuleNode> ruleNodesOrbit =
	// JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex),
	// JerboaOrbit.orbit(1, 2, 3));

	// assertFalse(rule.isRuleNodesOrbitCreated(ruleNodesOrbit));
	// }

	@Test
	/*
	 * Test isRuleNodesOrbitCreated method. True is expected.
	 *
	 * @throws JerboaException
	 */
	public void test_creation_case_InsertVertex_true() throws JerboaException {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertexFolded");

		int ruleNodeIndex = rule.getRightIndexRuleNode("n1");
		List<JerboaRuleNode> ruleNodesOrbit =
				JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex), JerboaOrbit.orbit(1));

		assertTrue(rule.isRuleNodesOrbitCreated(ruleNodesOrbit));
	}

	@Test
	/*
	 * Test isRuleNodesOrbitCreated method. False is expected.
	 *
	 * @throws JerboaException
	 */
	public void test_creation_case_InsertVertex_false() throws JerboaException {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertexFolded");

		int ruleNodeIndex = rule.getRightIndexRuleNode("n0");
		List<JerboaRuleNode> ruleNodesOrbit =
				JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex), JerboaOrbit.orbit(1));

		assertFalse(rule.isRuleNodesOrbitCreated(ruleNodesOrbit));
	}

	@Test
	/*
	 * Test isRuleNodesOrbitUnchanged method. True is expected.
	 *
	 * @throws JerboaException
	 */
	public void test_unchanged_test_case_InsertVertex_true() throws JerboaException {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");
		JerboaOrbit orbitType = JerboaOrbit.orbit(1, 2, 3);

		int ruleNodeIndex = rule.getRightIndexRuleNode("n0");
		List<JerboaRuleNode> ruleNodesOrbit =
				JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex), orbitType);

		assertTrue(rule.isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType));
	}

	@Test
	/*
	 * Test isRuleNodesOrbitUnchanged method. False is expected.
	 *
	 * @throws JerboaException
	 */
	public void test_unchanged_test_case_InsertVertex_false() throws JerboaException {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1, 3);

		int ruleNodeIndex = rule.getRightIndexRuleNode("n0");
		List<JerboaRuleNode> ruleNodesOrbit =
				JerboaRuleNode.orbit(rule.getRightRuleNode(ruleNodeIndex), orbitType);

		assertFalse(rule.isRuleNodesOrbitUnchanged(ruleNodesOrbit, orbitType));
	}

	@Test
	/*
	 * Test computeBBOrigin method. Rule is TriangulateFace, node for which to find origin is n1 and
	 * entry orbit is <0,2>. <1> is expected.
	 */
	public void test_computeBBOrigin_triangulation_edge_creation() {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("TriangulateFace");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0, 2);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertEquals(JerboaOrbit.orbit(1), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
	}

	@Test
	/*
	 * Test computeBBOrigin method. Rule is TriangulateFace, node for which to find origin is n2 and
	 * entry orbit is <1,2>. <0,1> is expected.
	 */
	public void test_computeBBOrigin_triangulation_vertex_creation() {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("TriangulateFace");
		JerboaOrbit orbitType = JerboaOrbit.orbit(1, 2);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n2"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertEquals(JerboaOrbit.orbit(0, 1), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
	}

	@Test
	/*
	 * Test computeBBOrigin method. Rule is CreateSquareFace, node for which to find origin is n6
	 * and entry orbit is <0>. <> is expected.
	 */
	public void test_computeBBOrigin_CreateSquareFace_edge_creation() {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("CreateSquareFace");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n6"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertEquals(JerboaOrbit.orbit(), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
	}

	@Test
	/*
	 * Test computeBBOrigin method. Rule is InsertVertexFolded, node for which to find origin is n1
	 * and entry orbit is <1>. <0> is expected.
	 */
	public void test_computeBBOrigin_InsertVertex_vertex_creation() {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertexFolded");
		JerboaOrbit orbitType = JerboaOrbit.orbit(1);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertEquals(JerboaOrbit.orbit(0), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
	}

	/*
	 * Test computeBBOrigin method. Rule is InsertVertex, node for which to find origin is n2 and
	 * entry orbit is <1>. <0> is expected.
	 */
	// @Test
	// public void
	// test_computeBBOrigin_InsertVertex_unfolded_alternative_vertex_creation() {
	// JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");
	// JerboaOrbit orbitType = JerboaOrbit.orbit(1);
	// JerboaRuleNode ruleNode =
	// rule.getRightRuleNode(rule.getRightIndexRuleNode("n2"));
	// List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode,
	// orbitType);
	// assertEquals(JerboaOrbit.orbit(0), rule.computeBBOrigin(ruleNodesOrbit,
	// orbitType));
	// }

	/*
	 * Test computeBBOrigin method. Rule is InsertVertex, node for which to find origin is n0 and
	 * entry orbit is <02>. <2> is expected.
	 */
	// @Test
	// public void
	// test_computeBBOrigin_InsertVertex_unfolded_alternative_edge_split() {
	// JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertVertex");
	// JerboaOrbit orbitType = JerboaOrbit.orbit(0, 2);
	// JerboaRuleNode ruleNode =
	// rule.getRightRuleNode(rule.getRightIndexRuleNode("n0"));
	// List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode,
	// orbitType);
	// assertEquals(JerboaOrbit.orbit(0, 2), rule.computeBBOrigin(ruleNodesOrbit,
	// orbitType));
	// }

	@Test
	public void test_computeBBOrigin_InserteEdge_face_split() {
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("InsertEdge");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1, 3);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n0"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertEquals(JerboaOrbit.orbit(3), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
		// assertEquals(JerboaOrbit.orbit(0, 3), rule.computeBBOrigin(ruleNodesOrbit, orbitType));
	}

	@Test
	public void test_detectMergeEvent_DeleteEdge() {
		//
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("DeleteEdge");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1, 3);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertTrue(rule.isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType));
	}

	@Test
	public void test_detectMergeEvent_SewA3() {
		//
		JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("SewA3");
		JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1, 3);
		JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
		List<JerboaRuleNode> ruleNodesOrbit = JerboaRuleNode.orbit(ruleNode, orbitType);
		assertTrue(rule.isRuleNodesOrbitMerged(ruleNodesOrbit, orbitType));
	}

}
