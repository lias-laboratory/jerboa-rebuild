package fr.ensma.lias.jerboa.core.tracking;

import static org.junit.Assert.assertTrue;

import fr.ensma.lias.jerboa.tracking.rule.rules.RawDynaOrTrackModeler;
import java.util.List;
import org.junit.Test;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

/** JerboaStaticDetectionTest */
public class JerboaStaticDetectionTest {

  RawDynaOrTrackModeler modeler = new RawDynaOrTrackModeler();

  public JerboaStaticDetectionTest() throws JerboaException {}

  @Test
  public void test_event_TriangulateFace_edge_creation() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("TriangulateFace");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 2);
    JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
    assertTrue(detector.createdOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_InsertVertexFolded_vertex_creation() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("InsertVertexFolded");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(1, 2);
    JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
    assertTrue(detector.createdOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_TriangulateFace_vertex_modified() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("TriangulateFace");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(1, 2);
    JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n0"));
    assertTrue(detector.modifiedOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_DeleteEdge_edge_deletion() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("DeleteEdge");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 2);
    JerboaRuleNode ruleNode = rule.getLeftRuleNode(rule.getLeftIndexRuleNode("n0"));
    assertTrue(detector.deletedOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_MergeFacesAroundVertex_vertex_deletion() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("MergeFacesAroundVertex");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(1, 2);
    JerboaRuleNode ruleNode = rule.getLeftRuleNode(rule.getLeftIndexRuleNode("n0"));
    assertTrue(detector.deletedOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_TriangulateFace_face_split() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("TriangulateFace");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1);
    JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n1"));
    assertTrue(detector.splittedOrbit(ruleNode, orbitType));
  }

  @Test
  public void test_event_FaceCollapse_face_modification() {
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("FaceCollapse");
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1);
    JerboaRuleNode ruleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode("n2"));
    List<JerboaRuleNode> orbit = JerboaRuleNode.orbit(ruleNode, orbitType);
    assertTrue(detector.modifiedOrbit(ruleNode, orbitType));
  }
}
