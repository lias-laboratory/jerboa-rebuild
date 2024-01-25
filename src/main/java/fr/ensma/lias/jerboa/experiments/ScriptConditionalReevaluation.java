package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
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
  // public static JerboaDart getEntryDart(List<JerboaRowPattern> leftRowPattern) {
  //   return !leftRowPattern.isEmpty() ? leftRowPattern.get(0).get(0) : null;
  // }

  // public static int getDartInstance(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
  //   for (int i = 0; i < leftRowPattern.size(); i++) {
  //     for (int j = 0; j < leftRowPattern.get(i).size(); j++) {
  //       if (leftRowPattern.get(i).get(j).equals(dart)) {
  //         return j;
  //       }
  //     }
  //   }
  //   return -1;
  // }

  public static Integer getDartsCoord(JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
    for (int i = 0; i < leftRowPattern.size(); i++) {
      for (int j = 0; j < leftRowPattern.get(i).size(); j++) {
        if (leftRowPattern.get(i).get(j).equals(dart)) {
          return j;
        }
      }
    }
    return null;
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
  // private void LHSMatching(
  //     ModelerGenerated modeler,
  //     JerboaDart dartA,
  //     JerboaDart dartB,
  //     // JerboaRuleGenerated ruleA,
  //     // JerboaRuleGenerated ruleB,
  //     List<JerboaRowPattern> leftPatternB) {

  //   int instanceA = getDartInstance(dartA, leftPatternB);
  //   int instanceB = getDartInstance(dartB, leftPatternB);

  //   if (dartA.equals(dartB)) {
  //     System.out.println("The same dart is re-evaluated");
  //   } else {
  //     if (instanceA == -1) {
  //       return;
  //     }
  //     // if()
  //   }
  // }

  /**
   * 0
   *
   * @param matchingDarts
   * @param orbitType
   * @param instancePath
   */
  // TODO: Documentation
  // TODO: populate a hashmap with origin darts from the first rule
  public static List<JerboaDart> getLHSDarts(
      JerboaOrbit originType, List<JerboaRowPattern> leftPattern, List<Integer> instancePath) {

    List<JerboaDart> darts = new ArrayList<>();

    JerboaDart root = leftPattern.get(0).get(0);

    // shift origin's root /!\ no safeguard when instance path is out the LHSPattern
    for (Integer a : instancePath) {
      root = root.alpha(a);
    }

    // gather darts within rule's LHS
    Stack<JerboaDart> queue = new Stack<>();
    List<JerboaDart> visited = new ArrayList<>();
    queue.push(root);
    while (!queue.isEmpty()) {
      JerboaDart d = queue.pop();
      if (!visited.contains(d)) {
        darts.add(d);
        visited.add(d);
        for (Integer a : originType) {
          JerboaDart next = d.alpha(a);
          if (next != null) queue.push(next);
        }
      }
    }

    //
    return darts;
  }

  /**
   * 0
   *
   * @param rule
   * @param detector
   * @param nodeName
   * @param orbitType
   * @param event
   * @return
   */
  // TODO: Documentation
  public static List<List<JerboaRuleNode>> findRHSOrbits(
      JerboaRuleGenerated rule,
      JerboaStaticDetection detector,
      String nodeName,
      JerboaOrbit orbitType,
      Event event) {

    List<List<JerboaRuleNode>> foundOrbits = new ArrayList<>();

    if (nodeName != null) {
      return Arrays.asList(
          JerboaRuleNode.orbit(
              rule.getRightRuleNode(rule.getRightIndexRuleNode(nodeName)), orbitType));
    }

    for (JerboaRuleNode rightNode : rule.getRight()) {
      if (rightNode.isNotMarked()) {
        Event computedEvent = detector.getEventFromOrbit(rightNode, orbitType);
        if (computedEvent.getCategory().equals(event.getCategory())) {
          List<JerboaRuleNode> matchingOrbit = JerboaRuleNode.orbit(rightNode, orbitType);
          for (JerboaRuleNode n : matchingOrbit) {
            n.setMark(true);
          }
          foundOrbits.add(matchingOrbit);
        }
      }
    }

    for (List<JerboaRuleNode> o : foundOrbits) {
      for (JerboaRuleNode n : o) {
        n.setMark(false);
      }
    }

    return foundOrbits;
  }

  public static Pair<List<Integer>, List<JerboaRuleNode>> conditionalReevaluation(
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> ruleALHSPattern,
      List<JerboaRowPattern> ruleBLHSPattern,
      List<Integer> instancePath,
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
        // System.out.println("origin in A");
        // originTypeA.forEach(v -> System.out.println("'" + v + "'"));
        originDarts = getLHSDarts(originTypeA, ruleALHSPattern, instancePath);
        // System.out.println("origin darts");
        // originDarts.forEach(v -> System.out.println(v));
        break;
      case MODIFICATION:
        traceDarts = getLHSDarts(orbitType, ruleALHSPattern, instancePath);
        if (event.equals(Event.SPLIT) || event.equals(Event.MERGE)) {
          System.out.println("foo" + traceDarts);
          originTypeA = detectorRuleA.computeOrigin(node, orbitType);
          // System.out.println("origin in A");
          // originTypeA.forEach(v -> System.out.println(v));
          originDarts = getLHSDarts(originTypeA, ruleALHSPattern, instancePath);
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
        findRHSOrbits(ruleB, detectorRuleB, null, orbitType, event);

    // 4/ select LHSB instances onto which reevaluation may operate
    List<Integer> intersections = new ArrayList<>();
    originDarts.forEach(
        d -> {
          Integer i = getDartsCoord(d, ruleBLHSPattern);
          if (!intersections.contains(i)) intersections.add(i);
        });

    System.out.println(ruleBLHSPattern);
    System.out.println(traceDarts);

    if (intersections.isEmpty()) {
      traceDarts.forEach(
          d -> {
            Integer i = getDartsCoord(d, ruleBLHSPattern);
            if (!intersections.contains(i)) intersections.add(i);
          });
    }

    List<JerboaRuleNode> roots =
        findRHSOrbits(ruleB, detectorRuleB, null, orbitType, event).stream()
            .map(o -> o.get(0))
            .collect(Collectors.toList());

    return new Pair<>(intersections, roots);
  }
}
