package fr.ensma.lias.jerboa.core.engine;

import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.LevelEventHR;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.ReevaluationTree2;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

public class RebuildEngine {

  private ParametricSpecification initialSpecification;
  private ParametricSpecification editedSpecification;
  private ArrayList<HistoryRecord> evaluationTrees;
  private ArrayList<ReevaluationTree2> reevaluationTrees;

  public RebuildEngine(
      ModelerGenerated modeler,
      String pathToDirectory,
      String pathToSpecification,
      String pathToEditedSpecification)
      throws IOException, JerboaException {

    this.evaluationTrees = new ArrayList<HistoryRecord>();
    this.reevaluationTrees = new ArrayList<ReevaluationTree2>();

    this.initialSpecification =
        JSONPrinter.importParametricSpecification(pathToDirectory, pathToSpecification, modeler);

    this.editedSpecification =
        JSONPrinter.importParametricSpecification(
            pathToDirectory, pathToEditedSpecification, modeler);
  }

  /**
   * Initialize the reevaluation process by computing the evaluation trees and creating empty
   * reevaluation trees
   */
  public void initializeReevaluation() {

    List<Application> initialApplications = this.initialSpecification.getApplications();

    for (Application application : initialApplications) {
      List<PersistentName> persistentNames = application.getPersistentNames();

      for (PersistentName persistentName : persistentNames) {
        JerboaOrbit orbitType = persistentName.getOrbitType();
        List<PersistentID> persistentIDs = persistentName.getPIs();

        for (PersistentID persistentID : persistentIDs) {
          // compute an evaluation tree
          HistoryRecord evaluationTree =
              new HistoryRecord(
                  persistentID,
                  orbitType,
                  initialSpecification,
                  initialApplications.indexOf(application));
          // register it within a list
          this.evaluationTrees.add(evaluationTree);
          // create and register the associated reevaluation tree
          this.reevaluationTrees.add(new ReevaluationTree2());
        }
      }
    }
  }

  /*
   * Overload method `reevaluate` to have a default behavior
   * @param gmap
   * @param gmapviewer
   */
  public void reevaluate(JerboaGMap gmap, GMapViewer gmapviewer) {
    reevaluate(gmap, gmapviewer, 0);
  }

  /**
   * Reevaluate an object by successively applying the rules of the editedSpecification and updating
   * a reevaluation tree.
   *
   * @param gmap
   * @param gmapviewer
   * @param stopApplicationID Index of an {@link Application} at which to stop reevaluating
   */
  public void reevaluate(JerboaGMap gmap, GMapViewer gmapviewer, int stopApplicationID) {

    /* There can be several topological parameters for a given rule application.
     * We use an index (a counter at heart) to reach topological parameters
     * within trees.*/
    int treeIndex = 0;

    // Go through the applications registered within the edited specification
    for (Application application : editedSpecification.getApplications()) {

      // Interrupt reevaluation when condition on stopApplicationID is met
      if (application.getApplicationID() == stopApplicationID) {
        break;
      }

      /* A rule application requires that a topological parameter must be a list
       * of JerboaDart, therefore a list of list is used to manage several
       * topological parameters; For the reevaluation, we encapsulate sets of
       * parameters, one for each branch of the current tree.*/
      List<List<List<JerboaDart>>> topologicalParameters = new ArrayList<>();
      List<JerboaRuleResult> applicationResults = new ArrayList<>();
      // List<List<JerboaDart>> controlOrbits = new ArrayList<>();
      List<JerboaDart> controlDarts = new ArrayList<>();

      // if the current application is added
      if (application.getApplicationType() == ApplicationType.ADD) {
        getDartsFromIDs(application, topologicalParameters, gmap);
        computeControlDarts(controlDarts, application, topologicalParameters);
      } else {
        treeIndex =
            getDartsFromTrees(application, reevaluationTrees, treeIndex, topologicalParameters);
      }

      // Do not apply a rule that is marked as DELETE
      if (application.getApplicationType() != ApplicationType.DELETE) {
        try {
          if (topologicalParameters.isEmpty()) {
            applicationResults.add(apply(gmap, application.getRule(), Arrays.asList()));
          } else {
            for (int index = 0; index < topologicalParameters.size(); index++) {
              applicationResults.add(
                  apply(gmap, application.getRule(), topologicalParameters.get(index)));
            }
          }
          updateReevaluationTrees(application, applicationResults, controlDarts);
          gmapviewer.updateIHM();
        } catch (JerboaException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Update all the {@link ReevaluationTree2} that need to be updated by adding a level to them. A
   * tree is updated when an {@link HistoryRecord} match the current application or when the
   * application is added.
   *
   * @param application
   * @param applicationResults
   * @param controlDarts
   */
  private void updateReevaluationTrees(
      Application application,
      List<JerboaRuleResult> applicationResults,
      List<JerboaDart> controlDarts) {

    // Update all reevaluation trees
    for (HistoryRecord evaluationTree : evaluationTrees) {

      // Unless the application is added, only trees whose leaves match current application's ID
      // must be updated
      if (evaluationTree.getLeaves().get(application.getApplicationID()) != null
          || application.getApplicationType() == ApplicationType.ADD) {

        int treeIndex = evaluationTrees.indexOf(evaluationTree);
        LevelEventHR levelEventEval = null;

        List<LevelEventHR> levelEventEvals =
            evaluationTree
                .getLeaves()
                .getOrDefault(application.getApplicationID(), new ArrayList<>());

        if (!levelEventEvals.isEmpty()) {
          // NOTE: get 0 because as of now, we consider evalation trees to have only one branch
          levelEventEval = levelEventEvals.get(0);
        }

        ReevaluationTree2 rt = reevaluationTrees.get(treeIndex);

        // For each application, update the reevaluation trees' branches
        for (int resultIndex = 0; resultIndex < applicationResults.size(); resultIndex++) {
          if (application.getApplicationType() == ApplicationType.ADD) {
            rt.addLevel(
                evaluationTree,
                levelEventEval,
                application,
                applicationResults.get(resultIndex),
                controlDarts.get(resultIndex));
          } else {
            rt.addLevel(
                evaluationTree,
                levelEventEval,
                application,
                applicationResults.get(resultIndex),
                null);
          }
        }
      }
    }
  }

  /**
   * Control darts help knowing whether or not that a branch is affected by the current application
   *
   * @param controlDarts
   * @param application
   * @param topologicalParameters
   */
  private void computeControlDarts(
      List<JerboaDart> controlDarts,
      Application application,
      List<List<List<JerboaDart>>> topologicalParameters) {

    //

    JerboaRebuiltRule rule = (JerboaRebuiltRule) application.getRule();
    JerboaRuleNode hook = rule.getHooks().get(0);
    int controlNodeIndex = rule.findClosestPreservedNode(hook);
    JerboaRuleNode controlNode = rule.getLeftRuleNode(controlNodeIndex);

    List<Integer> pathFromRootToControl =
        rule.collectLabelsFromSourceToClosestTarget(hook, Arrays.asList(controlNode), null);

    for (int paramIndex = 0; paramIndex < topologicalParameters.size(); paramIndex++) {
      JerboaDart controlDart = topologicalParameters.get(paramIndex).get(0).get(0);
      for (Integer label : pathFromRootToControl) {
        controlDart = controlDart.alpha(label);
      }
      controlDarts.add(controlDart);
    }
  }

  /**
   * Apply a rule with its topological parameters
   *
   * @param rule
   * @param topologicalParameters
   * @return
   * @throws JerboaException
   */
  private JerboaRuleResult apply(
      JerboaGMap gmap, JerboaRuleOperation rule, List<List<JerboaDart>> topologicalParameters)
      throws JerboaException {

    // apply rule and save its result until next application
    return rule.applyRule(gmap, new JerboaInputHooksGeneric(topologicalParameters));
  }

  /**
   * Get {@link JerboaDart} from reevaluation trees
   *
   * @param application
   * @param reevaluationTrees2
   * @param treeIndex
   * @param topologicalParameters
   */
  private int getDartsFromTrees(
      Application application,
      ArrayList<ReevaluationTree2> reevaluationTrees2,
      int treeIndex,
      List<List<List<JerboaDart>>> topologicalParameters) {

    int nbParameters = application.getPersistentNames().size();

    // Get as many parameters as required by the current application
    for (int i = 0; i < nbParameters; i++) {

      ReevaluationTree2 rt = reevaluationTrees.get(treeIndex + i);

      // Get parameters for each branch of the current reevaluation tree
      for (int j = 0; j < rt.getNbBranches(); j++) {

        if (topologicalParameters.size() <= j) {
          // Add a new parameters' set if there is none for index j
          topologicalParameters.add(
              new ArrayList<List<JerboaDart>>(
                  Arrays.asList(Arrays.asList(rt.getTopologicalParameter(j)))));
        } else {
          // Add parameter to index j
          topologicalParameters
              .get(j)
              .add(new ArrayList<JerboaDart>(Arrays.asList(rt.getTopologicalParameter(j))));
        }
      }
    }
    return treeIndex + nbParameters;
  }

  /**
   * Get {@link JerboaDart} from their identifiers stor the gmap
   *
   * @param application
   * @param topologicalParameters
   * @param gmap
   */
  private void getDartsFromIDs(
      Application application,
      List<List<List<JerboaDart>>> topologicalParameters,
      JerboaGMap gmap) {

    List<List<JerboaDart>> darts = new ArrayList<>();

    for (Integer dartID : application.getDartIDs()) {
      darts.add(new ArrayList<>(Arrays.asList(gmap.getNode(dartID))));
    }

    topologicalParameters.add(darts);
  }

  /* export both evaluation and reevaluation trees */
  public void exportTrees() {

    for (int index = 0; index < evaluationTrees.size(); index++) {
      this.evaluationTrees.get(index).export("./export", "evaluationTree-" + index + ".json");
      this.reevaluationTrees.get(index).export("./export", "reevaluationTree-" + index + ".json");
    }
  }
}
