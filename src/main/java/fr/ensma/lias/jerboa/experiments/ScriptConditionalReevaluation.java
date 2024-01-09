package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.List;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;

class ScriptConditionalReevaluation {

  /**
   * @param leftRowPattern
   * @return a Jerboadart if leftRowPattern not empty else null
   */
  public static JerboaDart getEntryDart(List<JerboaRowPattern> leftRowPattern) {
    return !leftRowPattern.isEmpty() ? leftRowPattern.get(0).get(0) : null;
  }

  public static int getDartInstance(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
    for (int i = 0; i < leftRowPattern.size(); i++) {
      for (int j = 0; j < leftRowPattern.get(i).size(); j++) {
        if (leftRowPattern.get(i).get(j).equals(dart)) {
          return j;
        }
      }
    }

    return -1;
  }

  /**
   * Look for ruleA topological parameter within ruleB leftRowPattern
   *
   * <p>dartA is dartB this can be trivially re-evaluated.
   *
   * <p>If it cannot be found -> no re-evaluation is possible
   *
   * <p>If dartA instance is different from dartB -> re-evaluate on this instance
   *
   * <p>If dartA has same instance -> check it must be preserved throughout ruleB application
   */
  private void LHSMatching(
      ModelerGenerated modeler,
      JerboaDart dartA,
      JerboaDart dartB,
      // JerboaRuleGenerated ruleA,
      // JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> leftPatternB) {

    int instanceA = getDartInstance(dartA, leftPatternB);
    int instanceB = getDartInstance(dartB, leftPatternB);

    if (dartA.equals(dartB)) {
      System.out.println("The same dart is re-evaluated");
    } else {
      if (instanceA == -1) {
        return;
      }
      // if()
    }
  }

  // NOTE: A little more theory + testings are necessary before putting if/else script matching
  // altogether
  public static Pair<JerboaRuleNode, JerboaOrbit> findRHSOrbit(
      JerboaRuleGenerated rule,
      String nodeName,
      JerboaOrbit orbitType,
      JerboaOrbit origin,
      Event event) {

    JerboaStaticDetection detector = new JerboaStaticDetection(rule);

    for (JerboaRuleNode rightNode : rule.getRight()) {
      Event computedEvent = detector.getEventFromOrbit(rightNode, orbitType);
      JerboaOrbit computedOrigin = detector.computeOrigin(rightNode, orbitType);
      if (computedEvent.equals(event) && computedOrigin.equals(origin)) {
        return new Pair<JerboaRuleNode, JerboaOrbit>(rightNode, orbitType);
      }
    }
    System.out.println("findRHSOrbit: no match found");
    return null;
  }

  /**
   * @param matchInstance An index of leftRowPattern from ruleB
   * @param ruleA A rule called during the initial Evaluation
   * @param ruleB A rule called during Reevaluation instead of ruleA
   * @return //TODO matchOrbits specify return
   */
  private void matchOrbits(
      int matchInstance, JerboaRuleGenerated ruleA, JerboaRuleGenerated ruleB) {
    // TODO: match orbits from ruleA onto ruleB
    // TODO: orbits must be matched by grouping: creation => Generative; split,
    // merge, modification
    // => Modification; deletion => Deletion
  }

  public JerboaDart conditionalReevaluation() {
    return null;
  }
}
