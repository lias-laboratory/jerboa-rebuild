package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.core.util.Pair;
import up.jerboa.exception.JerboaException;

class ScriptConditionalReevaluation {

  /**
   * Iterate a JerboaRowPattern to find the row index (or instance) of a dart
   *
   * @param dart a {@link JerboaDart}
   * @param leftRowPattern a List of {@link JerboaRowPattern} (Arrays of {@link JerboaDart})
   * @return an row Index (Integer) else null
   */
  static Integer getDartsInstance(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
    // That's a row pattern :
    // i is row index
    for (int i = 0; i < leftRowPattern.size(); i++) {
      // j is a col index
      for (int j = 0; j < leftRowPattern.get(i).size(); j++) {
        if (leftRowPattern.get(i).get(j).equals(dart)) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * When matching sub-/super-orbit, we must recompute as many orbits as necessary from the first
   * computed origin orbit
   *
   * @param baesOrigin a List of {@link JerboaDart} being the base origin orbit
   * @return a List of orbits (or a List of List of {@link JerboaDart})
   */
  static List<List<JerboaDart>> findSOrbitsInOrbit(
      List<JerboaDart> baseOrbit, JerboaOrbit orbitType) {

    List<List<JerboaDart>> dartsLists = new ArrayList<>();
    for (var d : baseOrbit) {
      try {
        final List<JerboaDart> darts = d.getOwner().orbit(d, orbitType);
        if (dartsLists.stream().noneMatch(l -> l.containsAll(darts))) {
          dartsLists.add(darts);
        }
      } catch (JerboaException e) {
      }
    }
    return dartsLists;
  }

  /**
   * Collect darts {@link JerboaDart} of an orbit of given {@link JerboaOrbit} type using a depth
   * first search
   *
   * @param originType a {@link JerboaOrbit}
   * @param leftPattern a List of {@link JerboaRowPattern} with the darts matched by a rule's left
   *     side
   * @return a List of JerboaDart
   */
  static List<JerboaDart> getLHSDarts(JerboaOrbit originType, List<JerboaRowPattern> leftPattern) {

    List<JerboaDart> darts = new ArrayList<>();
    JerboaDart root = leftPattern.get(0).get(0);

    try {
      darts = root.getOwner().orbit(root, originType);
    } catch (JerboaException e) {
    }

    //
    return darts;
  }

  private static void markOrbit(List<JerboaRuleNode> orbit, boolean flag) {
    orbit.forEach(node -> node.setMark(flag));
  }

  /**
   * Iterate through a rule's right side nodes and compute orbits. For each visited orbit, nodes are
   * marked to true (visited). If
   *
   * @param rule
   * @param detector
   * @param matchType
   * @param orbitType
   * @param event
   * @param strategyLevel
   * @return
   */
  static List<JerboaRuleNode> collectOrbitsRoots(
      JerboaRuleGenerated rule,
      JerboaStaticDetection detector,
      JerboaOrbit matchType,
      JerboaOrbit orbitType,
      Event event,
      int strategyLevel) {

    ScriptReevaluationStrategy strategy = FactoryScriptReevaluationStrategy.create(strategyLevel);
    List<List<JerboaRuleNode>> orbits = new ArrayList<>();

    for (JerboaRuleNode rightNode : rule.getRight()) {

      if (rightNode.isNotMarked()) {

        // REVIEW: computing matchingOrbit only once (check needed)
        List<JerboaRuleNode> matchingOrbit = JerboaRuleNode.orbit(rightNode, orbitType);
        Event computedEvent = detector.getEventFromOrbit(rightNode, orbitType, matchingOrbit);
        JerboaOrbit RHSBOriginType = detector.computeOrigin(rightNode, orbitType);

        // mark orbits to avoid compute orbits more than once
        markOrbit(matchingOrbit, true);

        if (computedEvent.equalsCategory(event)
            && (matchType == null || strategy.test(matchType, RHSBOriginType))) {
          orbits.add(matchingOrbit);
        }
      }
    }
    // process done -> unmark right nodes
    markOrbit(rule.getRight(), false);
    return orbits.stream().map(o -> o.get(0)).collect(Collectors.toList());
  }

  /* Compute the trace and origin dart lists depending on the originType and event values.
   * OriginType must *be* set when event belongs to GENERATION or is either of SPLIT or MERGE.
   *
   * @param originTypeA
   * @param orbitType
   * @param ruleALHSPattern
   * @param detectorRuleA
   * @param node
   * @param event
   * @return
   */
  static Pair<List<JerboaDart>, List<JerboaDart>> computeTraceAndOrTrace(
      JerboaOrbit originType,
      JerboaOrbit orbitType,
      List<JerboaRowPattern> ruleALHSPattern,
      JerboaStaticDetection detectorRuleA,
      JerboaRuleNode node,
      Event event) {
    //
    List<JerboaDart> originDarts = new ArrayList<>();
    List<JerboaDart> traceDarts = new ArrayList<>();

    switch (event.getCategory()) {
      case GENERATION:
        originDarts = getLHSDarts(originType, ruleALHSPattern);
        break;
      case MODIFICATION:
        traceDarts = getLHSDarts(orbitType, ruleALHSPattern);
        if (event.equals(Event.SPLIT) || event.equals(Event.MERGE)) {
          originDarts = getLHSDarts(originType, ruleALHSPattern);
        }
        break;
      case DESTRUCTION:
        break;
      default:
        break;
    }
    return new Pair<>(traceDarts, originDarts);
  }

  /**
   * Compute origin/trace intersection with LHSB for each root's (from RHSBOrbitRoots) originType
   *
   * @param detectorRuleB
   * @param RHSBOrbitRoots
   * @param orbitType
   * @param pairTraceOrigin
   * @param ruleBLHSPattern
   * @return
   */
  static List<Pair<Integer, JerboaRuleNode>> computeMatchDartCoordinates(
      JerboaStaticDetection detectorRuleB,
      List<JerboaRuleNode> RHSBOrbitRoots,
      JerboaOrbit orbitType,
      Pair<List<JerboaDart>, List<JerboaDart>> pairTraceOrigin,
      List<JerboaRowPattern> ruleBLHSPattern) {

    List<Pair<Integer, JerboaRuleNode>> coordinates =
        new ArrayList<Pair<Integer, JerboaRuleNode>>();

    // If no origin is expected then origin is empty
    if (pairTraceOrigin.r().isEmpty()) {
      // NOTE: Expect no more than one intersection for each orbit when there is only a trace
      // matching
      for (JerboaRuleNode root : RHSBOrbitRoots) {
        int instance = getDartsInstance(pairTraceOrigin.l().get(0), ruleBLHSPattern);
        if (instance >= 0) {
          coordinates.add(new Pair<>(instance, root));
        }
      }
    } else {
      // TODO: couple origins with roots to NOT RECOMPUTE EVERYTHING EVERY HECKIN' TIME
      for (JerboaRuleNode root : RHSBOrbitRoots) {
        JerboaOrbit origin = detectorRuleB.computeOrigin(root, orbitType);
        List<List<JerboaDart>> sDartOrbits = findSOrbitsInOrbit(pairTraceOrigin.r(), origin);
        for (List<JerboaDart> sDartOrbit : sDartOrbits) {
          int instance = getDartsInstance(sDartOrbit.get(0), ruleBLHSPattern);
          if (instance >= 0) {
            coordinates.add(new Pair<>(instance, root));
          }
        }
      }
    }
    return coordinates;
  }

  /**
   * Compare two rules found within a script if/else block.
   *
   * <p>Given a JerboaRuleNode, a {@link Event} and possibly an instance, compare their leftpatterns
   * and find the elligible topological parameters for the reevaluation process
   *
   * @param ruleA A {@link JerboaRuleGenerated} applied during the initial evaluation
   * @param ruleB A {@link JerboaRuleGenerated} to be applied at reevaluation
   * @param ruleALHSPattern The left {@link JerboaRowPattern} of ruleA
   * @param ruleBLHSPattern The left {@link JerboaRowPattern} of ruleB
   * @param node A reference {@link JerboaRuleNode} from the evaluation DAG (thus from ruleA)
   * @param orbitType A reference {@link JerboaOrbit} type from the evaluation DAG (thus from ruleA)
   * @param event A reference Event from the evaluation DAG (thus from ruleA)
   * @return A {@link Pair} containing two Lists : A Integer List for the matching instances and
   *     List for the roots of matching orbits
   */
  public static List<Pair<Integer, JerboaRuleNode>> conditionalReevaluation(
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> ruleALHSPattern,
      List<JerboaRowPattern> ruleBLHSPattern,
      JerboaRuleNode node,
      JerboaOrbit orbitType,
      JerboaOrbit originType,
      Event event) {

    Logger logger = Logger.getLogger(ScriptConditionalReevaluation.class.getName());
    logger.setLevel(Level.WARNING);
    logger.warning("This method may yield unexpected results with parallelization!");

    JerboaStaticDetection detectorRuleA = new JerboaStaticDetection(ruleA);

    // left: trace / right: origin
    Pair<List<JerboaDart>, List<JerboaDart>> pairTraceOrigin =
        computeTraceAndOrTrace(orbitType, orbitType, ruleALHSPattern, detectorRuleA, node, event);

    // match all eligible orbits in RHSB
    JerboaStaticDetection detectorRuleB = new JerboaStaticDetection(ruleB);
    List<JerboaRuleNode> RHSBOrbitRoots =
        collectOrbitsRoots(ruleB, detectorRuleB, originType, orbitType, event, 1);

    return computeMatchDartCoordinates(
        detectorRuleB, RHSBOrbitRoots, originType, pairTraceOrigin, ruleBLHSPattern);
  }
}
