package fr.ensma.lias.jerboa.core.utils.rule;

import static org.junit.Assert.assertEquals;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

/** PathToolKitTest */
public class PathToolKitTest {

  @Test
  public void test_split_2_TriangulateFace() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("TriangulateFace");
    JerboaRuleNode node =
        rule.getRightRuleNode(rule.getRightIndexRuleNode(rule.getHooks().get(0).getName()));
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1);
    int splitLink = 2;

    List<Integer> list = PathToolKit.getNextInstancePath(rule, node, splitLink, orbitType);
    System.out.println(list);
    assertEquals(Arrays.asList(0, 1, 2, 1), list);
  }

  @Test
  public void test_split_1_InsertEdge() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("InsertEdge");
    JerboaRuleNode node =
        rule.getRightRuleNode(rule.getRightIndexRuleNode(rule.getHooks().get(0).getName()));
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1);
    int splitLink = 2;

    List<Integer> list = PathToolKit.getNextInstancePath(rule, node, splitLink, orbitType);
    System.out.println(list);
    assertEquals(Arrays.asList(1, 2, 1), list);
  }

  @Test
  public void test_split_2_InsertVertexFolded() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("InsertVertexFolded");
    JerboaRuleNode node =
        rule.getRightRuleNode(rule.getRightIndexRuleNode(rule.getHooks().get(0).getName()));
    JerboaOrbit orbitType = JerboaOrbit.orbit(0);
    int splitLink = 1;

    List<Integer> list = PathToolKit.getNextInstancePath(rule, node, splitLink, orbitType);
    System.out.println(list);
    assertEquals(Arrays.asList(0, 1, 0), list);
  }
}
