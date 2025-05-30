package fr.ensma.lias.jerboa.core.utils.rule;

import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import fr.ensma.lias.jerboa.datastructures.NodeOrbit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;

/** ComputePath */
public class RelativePath {

  private JerboaRuleGenerated rule;
  private String nodeName;
  private List<Integer> alphaPath;
  private JerboaStaticDetection detector;

  // public RelativePath(JerboaRuleGenerated rule, String nodeName) {
  //   this.rule = rule;
  //   this.nodeName = nodeName;
  //   detector = new JerboaStaticDetection(rule);
  //   alphaPath = new ArrayList<Integer>();
  // }

  /**
   * Initilization procedure to update a path
   *
   * @param sourceNode A {@link JerboaRuleNode}
   * @param implicitLinksIndexes A list of Integer
   */
  private static void initializeUpdatePath(
      List<Integer> alphaPath, JerboaRuleNode sourceNode, List<Integer> implicitLinksIndexes) {
    JerboaRuleNode curNode = sourceNode;
    for (Integer label : alphaPath) {
      if (curNode.getOrbit().contains(label)) {
        implicitLinksIndexes.add(curNode.getOrbit().indexOf(label));
      } else if (curNode.alpha(label) != null) {
        curNode = curNode.alpha(label);
      }
    }
  }

  /**
   * Collect the matched implicit links within a {@link JerboaRuleNode} using a list of implicit
   * link indexes
   *
   * @param node A {@link JerboaRuleNode}
   * @param implicitIndexes A list of implicit link indexes
   */
  private static void collectImplicitLabels(
      List<Integer> alphaPath, JerboaRuleNode node, List<Integer> implicitIndexes) {
    for (Integer index : implicitIndexes) {
      alphaPath.add(node.getOrbit().get(index));
    }
  }

  /** Compute a relative path */
  public static List<Integer> computePath(
      JerboaRuleGenerated rule,
      String nodeName,
      NodeOrbit orbitNode,
      JerboaStaticDetection detector,
      Event event) {

    List<Integer> alphaPath = orbitNode.getAlphaPath();
    int nodeOfInterest = rule.getRightIndexRuleNode(nodeName);
    JerboaRuleNode rNode = rule.getRightRuleNode(nodeOfInterest);

    // if (rule.isNodeCreated(rNode)) {
    // return;
    // }

    if (alphaPath.isEmpty())
      // if (rule.isNodeCreated(rNode)) {
      // find hook in `rule.right`
      // }
      if ((event.equals(Event.NOMODIF) || event.equals(Event.MODIFICATION))
          || (detector.isNodeHook(rNode) || detector.isNodeCreated(rNode))) {
        alphaPath = new ArrayList<>();
        // return alphaPath;
      } else {
        int leftNodeOfInterest = rule.reverseAssoc(nodeOfInterest);
        JerboaRuleNode lNode = rule.getLeftRuleNode(leftNodeOfInterest);
        alphaPath =
            ToolKit.collectLabelsFromSourceToClosestTarget(rule, lNode, rule.getHooks(), null);
      }
    else {

      if (detector.isNodeCreated(rNode)) {
        return new ArrayList<>();
      }
      int leftNodeOfInterest = rule.reverseAssoc(nodeOfInterest);
      JerboaRuleNode lNode = rule.getLeftRuleNode(leftNodeOfInterest);
      JerboaRuleNode hook = ToolKit.findClosestHook(rule, lNode, rule.getHooks());
      if (hook == null) {
        hook = rule.getHooks().get(0);
      }

      List<Integer> implicitPath = new ArrayList<>();
      List<Integer> path = new ArrayList<>();

      // - rejouer le chemin à partir de nodeName dans right
      // et enregistrer les index d'arcs implicites traversés
      initializeUpdatePath(alphaPath, rNode, implicitPath);
      alphaPath = new ArrayList<>();
      // - aller de nodename vers hook (enregistrer les arcs explicites) dans left
      path = ToolKit.collectLabelsFromSourceToClosestTarget(rule, lNode, rule.getHooks(), hook);
      alphaPath.addAll(path);
      // - enregistrer les arcs implicites dans l'ordre des index enregistrés avant et
      // dans
      // hook
      collectImplicitLabels(alphaPath, hook, implicitPath);
      // - aller de hook vers nodename en enregistrant les arcs explicites traversés dans
      // left
      path = ToolKit.collectLabelsFromSourceToClosestTarget(rule, hook, Arrays.asList(lNode), null);
      alphaPath.addAll(path);
    }
    return alphaPath;
  }
}
