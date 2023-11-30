package fr.ensma.lias.jerboa.core.rule.rules.CarvingFake;

import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.util.ArrayList;
import java.util.List;
import up.jerboa.core.*;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.exception.JerboaException;

/** */
public class PierceFaceCoverDiamondFake extends JerboaRebuiltRule {

  private transient JerboaRowPattern curleftPattern;

  // BEGIN PARAMETERS Transformed

  protected Object fakeLeft;

  // END PARAMETERS

  public PierceFaceCoverDiamondFake(ModelerGenerated modeler) throws JerboaException {

    super(modeler, "PierceFaceCoverDiamondFake", "CarvingFake");

    // -------- LEFT GRAPH
    JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0, 1, 3), 3);
    left.add(ln0);
    hooks.add(ln0);
    ln0.setAlpha(2, ln0);

    // -------- RIGHT GRAPH
    JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(-1, 1, 3), 3);
    JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(-1, -1, 3), 3);
    JerboaRuleNode rn2 =
        new JerboaRuleNode(
            "n2", 2, JerboaOrbit.orbit(-1, 0, 3), 3, new PierceFaceCoverDiamondFakeExprRn2pos());
    JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(1, 0, 3), 3);
    right.add(rn0);
    right.add(rn1);
    right.add(rn2);
    right.add(rn3);
    rn0.setAlpha(0, rn1);
    rn1.setAlpha(1, rn2);
    rn2.setAlpha(2, rn3);
    rn0.setAlpha(2, rn0);
    rn1.setAlpha(2, rn1);
    ;
    // ------- SPECIFIED FEATURE
    computeEfficientTopoStructure();
    computeSpreadOperation();
    // ------- USER DECLARATION
    fakeLeft = null;
  }

  public int reverseAssoc(int i) {
    switch (i) {
      case 0:
        return 0;
      case 1:
        return -1;
      case 2:
        return -1;
      case 3:
        return -1;
    }
    return -1;
  }

  public int attachedNode(int i) {
    switch (i) {
      case 0:
        return 0;
      case 1:
        return 0;
      case 2:
        return 0;
      case 3:
        return 0;
    }
    return -1;
  }

  public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, Object fakeLeft)
      throws JerboaException {
    JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
    ____jme_hooks.addCol(n0);
    setFakeLeft(fakeLeft);
    return applyRule(gmap, ____jme_hooks);
  }

  private class PierceFaceCoverDiamondFakeExprRn2pos implements JerboaRuleExpression {

    @Override
    public Object compute(
        JerboaGMap gmap,
        JerboaRuleOperation rule,
        JerboaRowPattern leftPattern,
        JerboaRuleNode rulenode)
        throws JerboaException {
      curleftPattern = leftPattern;
      // ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
      // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
      Vec3 center = new Vec3();
      center.barycenter(
          gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(
              curleftPattern.getNode(0), JerboaOrbit.orbit(0, 1, 3), 0));
      return new Vec3(
          Vec3.segmentABByWeight(
              curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),
              center,
              0.4f));
      // ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
    }

    @Override
    public String getName() {
      return "pos";
    }

    @Override
    public int getEmbedding() {
      return ((ModelerGenerated) modeler).getPos().getID();
    }
  }

  @Override
  public boolean hasPrecondition() {
    return true;
  }

  public boolean evalPrecondition(final JerboaGMap gmap, final List<JerboaRowPattern> leftPattern)
      throws JerboaException {

    // BEGIN PRECONDITION CODE
    fakeLeft = new ArrayList<JerboaRowPattern>(leftPattern);
    return false;
    // END PRECONDITION CODE
  }

  // Facility for accessing to the dart
  private JerboaDart n0() {
    return curleftPattern.getNode(0);
  }

  public Object getFakeLeft() {
    return fakeLeft;
  }

  public void setFakeLeft(Object _fakeLeft) {
    this.fakeLeft = _fakeLeft;
  }
} // end rule Class
