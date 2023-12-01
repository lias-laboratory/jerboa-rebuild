package fr.ensma.lias.jerboa.core.utils.rules;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.rule.PathToolKit;
import java.util.List;
import org.junit.Test;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

/** PathToolKitTest */
public class PathToolKitTest {

  @Test
  public void test_Case() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    JerboaRuleGenerated rule = (JerboaRuleGenerated) modeler.getRule("TriangulateFace");
    JerboaRuleNode node =
        rule.getRightRuleNode(rule.getRightIndexRuleNode(rule.getHooks().get(0).getName()));
    JerboaOrbit orbitType = JerboaOrbit.orbit(0, 1, 3);
    int splitLink = 2;

    List<Integer> list = PathToolKit.getNextInstancePath(rule, node, splitLink, orbitType);
    System.out.println(list);
  }
}
