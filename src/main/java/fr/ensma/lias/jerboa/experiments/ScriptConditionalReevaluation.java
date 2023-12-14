package fr.ensma.lias.jerboa.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;

class ScriptConditionalReevaluation {

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

    // get ruleA left row pattern from ruleA
    List<JerboaRowPattern> leftRowPatternA = new ArrayList<>();
    // get ruleB left row pattern from ruleB
    List<JerboaRowPattern> leftRowPatternB = new ArrayList<>();

    // TODO get Dart from ruleAEntryPath
    JerboaDart dartA = null;
    // coords for dart A in row pattern A
    Pair<Integer, Integer> coordA1 = new Pair<Integer, Integer>(-1, -1);
    // coords for dart A in row pattern B
    Pair<Integer, Integer> coordA2 = new Pair<Integer, Integer>(-1, -1);
    // TODO get Dart from ruleBEntryPath
    // coords for dart B in row pattern A
    Pair<Integer, Integer> coordB1 = new Pair<Integer, Integer>(-1, -1);
    // coords for dart B in row pattern B
    Pair<Integer, Integer> coordB2 = new Pair<Integer, Integer>(-1, -1);
    JerboaDart dartB = null;

    for (int i = 0; i < leftRowPatternA.size(); i++) {
      for (int j = 0; j < leftRowPatternA.get(i).size(); j++) {
        if (leftRowPatternA.get(i).get(j).equals(dartA)) {}
        // TODO assign coords to coordA…
        break;
      }
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
