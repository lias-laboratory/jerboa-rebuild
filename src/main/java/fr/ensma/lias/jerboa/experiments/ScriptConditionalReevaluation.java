package fr.ensma.lias.jerboa.experiments;

import java.util.List;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.util.JerboaRuleGenerated;

class ScriptConditionalReevaluation {
  /**
   * @param scriptEntry The dart used as parameter for the script
   * @param ruleAEntryPath The path to the dart used as entry for ruleA
   * @param ruleBEntryPath The path to the dart used as entry for ruleB
   * @param ruleA A rule called during the initial Evaluation
   * @param ruleB A rule called during Reevaluation instead of ruleA
   * @return //TODO initializeMatching specify return
   */
  void initializeMatching(
      JerboaDart scriptEntry,
      List<Integer> ruleAEntryPath,
      List<Integer> ruleBEntryPath,
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB) {
    // TODO: match ruleA leftPattern against ruleB leftpattern and deduce matching instanciation in
    // ruleB
  }

  /**
   * @param matchInstance An index of leftRowPattern from ruleB
   * @param ruleA A rule called during the initial Evaluation
   * @param ruleB A rule called during Reevaluation instead of ruleA
   * @return //TODO matchOrbits specify return
   */
  void matchOrbits(int matchInstance, JerboaRuleGenerated ruleA, JerboaRuleGenerated ruleB) {
    // TODO: match orbits from ruleA onto ruleB
    // TODO: orbits must be matched by grouping: creation => Generative; split, merge, modification
    // => Modification; deletion => Deletion
  }
}
