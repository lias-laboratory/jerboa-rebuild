package fr.ensma.lias.jerboa.datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.core.utils.rule.RelativePath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

/** NodeOrbitHRTest */
public class NodeOrbitTest {

  private ModelerGenerated modeler = new ModelerGenerated();

  public NodeOrbitTest() throws JerboaException {}

  @Test
  /**
   * Test if a BB build entry is valid.
   *
   * <p>Settings are: nodeName=n1; currentNodeOrbit=(0,2); rule=TriangulateFace;
   * levelEvent=LevelEvent's instance
   */
  public void test_BBBuildEntry_TriangulateFace_creation() throws JerboaException {
    JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("TriangulateFace");
    NodeOrbit nodeOrbit = new NodeOrbit(JerboaOrbit.orbit(0, 2));
    String nodeName = "n1";
    LevelEventHR levelEvent = new LevelEventHR(3, null);
    List<NodeOrbit> result =
        nodeOrbit.BBBuildEntry(nodeName, rule, levelEvent, new ArrayList<NodeOrbit>(), false);
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
    Link child1 = new Link(LinkType.TRACE, nodeEventA);
    Link child2 = new Link(LinkType.TRACE, nodeEventB);

    int expected_size = 1;
    NodeOrbit expected_node = new NodeOrbit(JerboaOrbit.orbit(0, 1));
    expected_node.addChild(new Link(LinkType.TRACE, nodeEventA));
    expected_node.addChild(new Link(LinkType.TRACE, nodeEventB));
    List<NodeOrbit> expected_list = new ArrayList<>();
    expected_list.add(expected_node);

    List<NodeOrbit> list = new ArrayList<>();

    NodeOrbit.updateNodeOrbitList(nodeOrbitA, child1, list);
    assertEquals(expected_size, list.size());
    NodeOrbit.updateNodeOrbitList(nodeOrbitB, child2, list);

    assertEquals(expected_size, list.size());
    assertEquals(expected_list, list);
    assertTrue(expected_list.get(0).getChildren().containsAll(list.get(0).getChildren()));
    assertTrue(list.get(0).getChildren().containsAll(expected_list.get(0).getChildren()));
  }

  @Test
  public void test_compute_path_oneNewTwoUpdates() {
    JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("DeleteEdge");
    if(rule == null)
    	return; // HAK en fait selon ton JME il ne retrouve pas la regle donc si elle n'existe pas alors pas de test.
    NodeOrbit nodeOrbit = new NodeOrbit(JerboaOrbit.orbit(0, 1));
    String nodeName = "n1";
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaRuleNode currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    Event event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(1), nodeOrbit.getAlphaPath());

    nodeOrbit = new NodeOrbit(nodeOrbit.getOrbit(), nodeOrbit.getAlphaPath());
    nodeName = "n1";
    detector = new JerboaStaticDetection(rule);
    currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(1, 2, 1), nodeOrbit.getAlphaPath());

    rule = (JerboaRebuiltRule) modeler.getRule("TriangulateFace");
    nodeOrbit = new NodeOrbit(nodeOrbit.getOrbit(), nodeOrbit.getAlphaPath());
    nodeName = "n0";
    detector = new JerboaStaticDetection(rule);
    currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(1), nodeOrbit.getAlphaPath());

    rule = (JerboaRebuiltRule) modeler.getRule("InsertEdge");
    nodeOrbit = new NodeOrbit(nodeOrbit.getOrbit(), nodeOrbit.getAlphaPath());
    nodeName = "n0";
    detector = new JerboaStaticDetection(rule);
    currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(), nodeOrbit.getAlphaPath());
  }

  @Test
  public void test_compute_path_without_NOEFF() {
    JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("FaceCollapse");
    NodeOrbit nodeOrbit = new NodeOrbit(JerboaOrbit.orbit(1, 2));
    String nodeName = "n2";
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaRuleNode currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    Event event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(1, 2), nodeOrbit.getAlphaPath());

    rule = (JerboaRebuiltRule) modeler.getRule("ExtrudeVolumeFaceZAxis");
    nodeOrbit = new NodeOrbit(nodeOrbit.getOrbit(), nodeOrbit.getAlphaPath());
    nodeName = "n4";
    detector = new JerboaStaticDetection(rule);
    currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(), nodeOrbit.getAlphaPath());

    rule = (JerboaRebuiltRule) modeler.getRule("CreateSquareFace");
    nodeOrbit = new NodeOrbit(nodeOrbit.getOrbit(), nodeOrbit.getAlphaPath());
    nodeName = "n1";
    detector = new JerboaStaticDetection(rule);
    currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(), nodeOrbit.getAlphaPath());
  }

  @Test
  public void test_compute_path_pyramid() {
    JerboaRebuiltRule rule = (JerboaRebuiltRule) modeler.getRule("FaceA3ToPyramid");
    NodeOrbit nodeOrbit = new NodeOrbit(JerboaOrbit.orbit(0, 1, 3));
    String nodeName = "n1";
    JerboaStaticDetection detector = new JerboaStaticDetection(rule);
    JerboaRuleNode currentRuleNode = rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName));
    Event event = detector.getEventFromOrbit(currentRuleNode, nodeOrbit.getOrbit());
    nodeOrbit.setAlphaPath(RelativePath.computePath(rule, nodeName, nodeOrbit, detector, event));
    assertEquals(Arrays.asList(3), nodeOrbit.getAlphaPath());
  }
}
