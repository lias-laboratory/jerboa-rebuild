package fr.ensma.lias.jerboa.experiments;

import java.util.List;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.util.JerboaRuleGenerated;

class ScriptConditionalReevaluation {

  private JerboaDart getDartFromEntry(
      List<JerboaRowPattern> leftRowPattern, int nodeIndex, int instance, List<Integer> path) {
    JerboaDart dart = leftRowPattern.get(nodeIndex).get(instance);
    for (int link : path) {
      dart = dart.alpha(link);
    }
    return dart;
  }

  private int getDartInstance(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
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
   * @param scriptEntry The dart used as parameter for the script
   * @param ruleAEntryPath The path to the dart used as entry for ruleA
   * @param ruleBEntryPath The path to the dart used as entry for ruleB
   * @param ruleA A rule called during the initial Evaluation
   * @param ruleB A rule called during Reevaluation instead of ruleA
   * @return //TODO initializeMatching specify return
   */
  private void initializeMatching(
      JerboaDart scriptEntry,
      List<Integer> ruleAEntryPath,
      List<Integer> ruleBEntryPath,
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB) {

    JerboaDart dartA = getDartFromEntry(null, ruleA.getHooks().get(0).getID(), 0, ruleBEntryPath);
    int instance = getDartInstance(dartA, null);
    if (instance != -1) {
      System.out.println("Found instance: " + instance);
    }
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
    initializeMatching(null, null, null, null, null);
    matchOrbits((Integer) null, null, null);
    return null;
  }
}
