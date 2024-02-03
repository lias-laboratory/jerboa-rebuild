package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  public static Integer getDartsInstance(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
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
    return null;
  }

  /**
   * When matching sub-/super-orbit, we must recompute as many orbits as necessary from the first
   * computed origin orbit
   *
   * @param baesOrigin a List of {@link JerboaDart} being the base origin orbit
   * @return a List of orbits (or a List of List of {@link JerboaDart})
   */
  public static List<List<JerboaDart>> findSOrbitsInOrbit(
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
  public static List<JerboaDart> getLHSDarts(
      JerboaOrbit originType, List<JerboaRowPattern> leftPattern) {

    List<JerboaDart> darts = new ArrayList<>();
    JerboaDart root = leftPattern.get(0).get(0);

    try {
      darts = root.getOwner().orbit(root, originType);
    } catch (JerboaException e) {
    }

    //
    return darts;
  }

  /**
   * Iterate the orbits (given an orbit type) within a rule's right side and keep those matching the
   * event's category
   *
   * @param rule A {@link JerboaRuleGenerated}
   * @param detector An instance of JerboaStaticDetection, here to compute an orbit's event
   * @param nodeName An Optional node to match a specific orbit (iterate all if nodeName is null)
   * @param orbitType a {@link JerboaOrbit}
   * @param event An {@link Event} to match
   * @return A List of orbits (List of JerboaRuleNodes), possibly empty if no match was found
   */
  public static List<List<JerboaRuleNode>> findRHSOrbits(
      JerboaRuleGenerated rule,
      JerboaStaticDetection detector,
      String nodeName,
      JerboaOrbit matchType,
      JerboaOrbit orbitType,
      Event event) {

    List<List<JerboaRuleNode>> orbits = new ArrayList<>();

    if (nodeName == null) {
      for (JerboaRuleNode rightNode : rule.getRight()) {
        if (rightNode.isNotMarked()) {

          Event computedEvent = detector.getEventFromOrbit(rightNode, orbitType);

          // TODO: Make this readable
          if (computedEvent.getCategory().equals(event.getCategory())
              && (matchType == null
                  || matchType.equals(detector.computeOrigin(rightNode, orbitType)))) {
            List<JerboaRuleNode> matchingOrbit = JerboaRuleNode.orbit(rightNode, orbitType);
            for (JerboaRuleNode n : matchingOrbit) {
              n.setMark(true);
            }
            orbits.add(matchingOrbit);
          }
        }
      }
      for (List<JerboaRuleNode> o : orbits) {
        for (JerboaRuleNode n : o) {
          n.setMark(false);
        }
      }
    } else {

      return Arrays.asList(
          JerboaRuleNode.orbit(
              rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName)), orbitType));
    }

    return orbits;
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
  public static Pair<List<Integer>, List<JerboaRuleNode>> conditionalReevaluation(
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> ruleALHSPattern,
      List<JerboaRowPattern> ruleBLHSPattern,
      JerboaRuleNode node,
      JerboaOrbit orbitType,
      Event event) {

    JerboaStaticDetection detectorRuleA = new JerboaStaticDetection(ruleA);
    List<JerboaDart> traceDarts = new ArrayList<>();
    List<JerboaDart> originDarts = new ArrayList<>();
    JerboaOrbit originTypeA = null;

    switch (event.getCategory()) {
      case GENERATION:
        originTypeA = detectorRuleA.computeOrigin(node, orbitType);
        System.out.println("origin type A: " + originTypeA);
        // System.out.println("origin in A");
        // originTypeA.forEach(v -> System.out.println("'" + v + "'"));
        originDarts = getLHSDarts(originTypeA, ruleALHSPattern);
        // System.out.println("origin darts");
        // originDarts.forEach(v -> System.out.println(v));
        break;
      case MODIFICATION:
        traceDarts = getLHSDarts(orbitType, ruleALHSPattern);
        if (event.equals(Event.SPLIT) || event.equals(Event.MERGE)) {
          originTypeA = detectorRuleA.computeOrigin(node, orbitType);
          System.out.println("origin type A: " + originTypeA);
          // System.out.println("origin in A");
          // originTypeA.forEach(v -> System.out.println(v));
          originDarts = getLHSDarts(originTypeA, ruleALHSPattern);
          // System.out.println("origin darts");
          // originDarts.forEach(v -> System.out.println(v));
        }
        break;
      case DESTRUCTION:
        break;
      default:
        break;
    }

    // 3/ match all eligible orbits in RHSB
    JerboaStaticDetection detectorRuleB = new JerboaStaticDetection(ruleB);
    List<List<JerboaRuleNode>> RHSBOrbits =
        findRHSOrbits(ruleB, detectorRuleB, null, originTypeA, orbitType, event);

    // 4/ select LHSB instances onto which reevaluation may operate
    List<Integer> intersections = new ArrayList<>();
    // originDarts.forEach(
    //     d -> {
    //       Integer i = getDartsInstance(d, ruleBLHSPattern);
    //       if (!intersections.contains(i)) intersections.add(i);
    //     });
    intersections.add(getDartsInstance(originDarts.get(0), ruleBLHSPattern));

    System.out.println("ruleB LHS: " + ruleBLHSPattern);
    System.out.println("origin darts: " + originDarts);
    System.out.println("trace darts: " + traceDarts);
    if (ruleA.getName().equals("TriangulateFaceFake")) {
      System.out.println(findSOrbitsInOrbit(originDarts, JerboaOrbit.orbit(1)));
    }
    if (intersections.isEmpty()) {
      // traceDarts.forEach(
      //     d -> {
      //       Integer i = getDartsInstance(d, ruleBLHSPattern);
      //       if (!intersections.contains(i)) intersections.add(i);
      //     });
      intersections.add(getDartsInstance(traceDarts.get(0), ruleBLHSPattern));
    }

    // get only first nodes in each orbit
    List<JerboaRuleNode> roots =
        RHSBOrbits.stream().map(l -> l.get(0)).collect(Collectors.toList());

    return new Pair<>(intersections, roots);
  }
}
