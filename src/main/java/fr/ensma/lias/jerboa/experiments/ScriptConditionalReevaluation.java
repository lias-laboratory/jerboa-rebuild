package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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

  public static Pair<Integer, Integer> getDartsCoord(
      JerboaDart dart, List<JerboaRowPattern> leftRowPattern) {
    for (int i = 0; i < leftRowPattern.size(); i++) {
      for (int j = 0; j < leftRowPattern.get(i).size(); j++) {
        if (leftRowPattern.get(i).get(j).equals(dart)) {
          return new Pair<>(i, j);
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
  public static Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> findOriginDarts(
      JerboaOrbit originType, List<JerboaRowPattern> ruleALHSPattern, List<Integer> instancePath) {

    Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> originDarts = new HashMap<>();

    JerboaDart root = ruleALHSPattern.get(0).get(0);

    // shift origin's root /!\ no safeguard when instance path is out the LHSPattern
    for (Integer a : instancePath) {
      root = root.alpha(a);
    }

    // gather darts within origin orbit
    Stack<JerboaDart> queue = new Stack<>();
    List<JerboaDart> visited = new ArrayList<>();
    queue.push(root);
    while (!queue.isEmpty()) {
      JerboaDart d = queue.pop();
      if (!visited.contains(d)) {
        originDarts.put(d, new ArrayList<Pair<JerboaRuleNode, Integer>>());
        visited.add(d);
        for (Integer a : originType) {
          JerboaDart next = d.alpha(a);
          if (next != null) queue.push(next);
        }
      }
    }

    //
    return originDarts;
  }

  /**
   * 0
   *
   * @param matchingDarts
   */
  // TODO: Documentation
  // TODO: populate a hashmap with the nodes and indexes darts from a second rule
  public static void matchLHSDarts(
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> ruleALHSPattern,
      List<JerboaRowPattern> ruleBLHSPattern,
      Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> matchingDarts) {

    // referencing for ruleA
    for (JerboaDart dart : matchingDarts.keySet()) {
      Pair<Integer, Integer> coord = getDartsCoord(dart, ruleALHSPattern);
      JerboaRuleNode n = ruleA.getLeftRuleNode(coord.l());
      List<Pair<JerboaRuleNode, Integer>> l = matchingDarts.get(dart);
      l.add(new Pair<>(n, coord.r()));
      matchingDarts.put(dart, l);
    }

    // referencing for ruleB
    // for (JerboaDart dart : matchingDarts.keySet()) {
    //   Pair<Integer, Integer> coord = getDartsCoord(dart, ruleBLHSPattern);
    //   if (coord != null) {
    //     JerboaRuleNode n = ruleB.getLeftRuleNode(coord.l());
    //     List<Pair<JerboaRuleNode, Integer>> l = matchingDarts.get(dart);
    //     l.add(new Pair<>(n, coord.r()));
    //     matchingDarts.put(dart, l);
    //   }
    // }
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

  public JerboaDart conditionalReevaluation(
      JerboaRuleGenerated ruleA,
      JerboaRuleGenerated ruleB,
      List<JerboaRowPattern> ruleALHSPattern,
      List<JerboaRowPattern> ruleBLHSPattern,
      List<Integer> instancePath,
      JerboaRuleNode node,
      JerboaOrbit orbitType,
      Event event) {

    JerboaStaticDetection detectorRuleA = new JerboaStaticDetection(ruleA);
    JerboaOrbit originA = detectorRuleA.computeOrigin(node, orbitType);

    // 1/ match an orbit of root n in RHSA
    List<List<JerboaRuleNode>> RHSAOrbits =
        findRHSOrbits(ruleA, detectorRuleA, node.getName(), orbitType, event);
    // List<JerboaRuleNode> RHSAOrbit = JerboaRuleNode.orbit(node, orbitType);

    // 1.5/ initialize memoization list for origin darts from LHSA
    Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> dartsMapping =
        findOriginDarts(orbitType, ruleALHSPattern, instancePath);

    // 2/ memoize darts -> dart : ("LHSA" (node, index), "LHSB" (node, index))

    // 3/ match all eligible orbits in RHSB

    // 4/ select default orbit from RHSB orbits

    return null;
  }
}
