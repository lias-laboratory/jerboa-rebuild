package fr.ensma.lias.jerboa.core.rule.rules.Sew;

import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import up.jerboa.core.*;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.exception.JerboaException;

/** */
public class SewA3 extends JerboaRebuiltRule {

  private transient JerboaRowPattern curleftPattern;

  // BEGIN PARAMETERS Transformed

  // END PARAMETERS

  public SewA3(ModelerGenerated modeler) throws JerboaException {

    super(modeler, "SewA3", "Sew");

    // -------- LEFT GRAPH
    JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0, 1), 3);
    JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(0, 1), 3);
    left.add(ln0);
    left.add(ln1);
    hooks.add(ln0);
    hooks.add(ln1);
    ln0.setAlpha(3, ln0);
    ln1.setAlpha(3, ln1);

    // -------- RIGHT GRAPH
    JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0, 1), 3);
    JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(0, 1), 3);
    right.add(rn0);
    right.add(rn1);
    rn0.setAlpha(3, rn1);
    ;
    // ------- SPECIFIED FEATURE
    computeEfficientTopoStructure();
    computeSpreadOperation();
    // ------- USER DECLARATION
  }

  public int reverseAssoc(int i) {
    switch (i) {
      case 0:
        return 0;
      case 1:
        return 1;
    }
    return -1;
  }

  public int attachedNode(int i) {
    switch (i) {
      case 0:
        return 1;
      case 1:
        return 1;
    }
    return -1;
  }

  public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, JerboaDart n1)
      throws JerboaException {
    JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
    ____jme_hooks.addCol(n0);
    ____jme_hooks.addCol(n1);
    return applyRule(gmap, ____jme_hooks);
  }

  // Facility for accessing to the dart
  private JerboaDart n0() {
    return curleftPattern.getNode(0);
  }

  private JerboaDart n1() {
    return curleftPattern.getNode(1);
  }
} // end rule Class
