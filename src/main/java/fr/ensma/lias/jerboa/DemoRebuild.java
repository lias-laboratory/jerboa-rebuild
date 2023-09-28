package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.LevelEventHR;
import fr.ensma.lias.jerboa.datastructures.ReevaluationTree;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;


/**
 * This class' purpose is to demonstrate the reevaluation without change of a model built with
 * parametric operations
 */
public class DemoRebuild {

  private JerboaGMap gmap;
  private ParametricSpecification parametricSpecification;
  private List<Application> applications;
  private List<HistoryRecord> historyRecords;
  private List<ReevaluationTree> reevaluationTrees;
  private List<Application> editedApplications;

  public DemoRebuild(JerboaRebuiltBridge bridge, String referenceDir, String referenceSpec,
      String editedDir, String editedSpec, JFrame frame, GMapViewer gmapviewer)
      throws IOException, JerboaException {

    this.gmap = bridge.getGMap(); // gmap in which we rebuild the model
    ModelerGenerated modeler = (ModelerGenerated) bridge.getModeler();

    parametricSpecification =
        JSONPrinter.importParametricSpecification(referenceDir, referenceSpec, modeler);
    applications = parametricSpecification.getApplications();

    historyRecords = new ArrayList<>();
    reevaluationTrees = new ArrayList<>();

    initializeReevaluation();

    editedApplications =
        JSONPrinter.importParametricSpecification(editedDir, editedSpec, modeler).getApplications();
  }

  /**
   *
   * Export the matching trees created during the model's reevaluation
   *
   * @param reevaluationTrees
   */
  // private void exportReevaluationTrees(List<ReevaluationTree> reevaluationTrees) {
  private void exportReevaluationTrees() {

    int counter = 0;
    for (ReevaluationTree rt : this.reevaluationTrees) {
      rt.export("exports", "matchingtree-" + counter + ".json");
      counter++;
    }
  }

  /**
   * Initialize the reevaluation process by computing history records and creating empty matching
   * trees
   *
   * @param parametricSpecification
   * @param applications
   * @param historyRecords
   * @param reevaluationTrees
   */
  private void initializeReevaluation() {

    int counter = 0;
    // compute and store all history records
    for (var application : applications) {
      var PNs = application.getPersistentNames();

      for (PersistentName PN : PNs) {
        JerboaOrbit orbitType = PN.getOrbitType();
        var PIs = PN.getPIs();

        for (PersistentID PI : PIs) {
          // Compute and export HRs from current spec
          HistoryRecord hr = new HistoryRecord(PI, orbitType, parametricSpecification,
              applications.indexOf(application));
          hr.export("./exports", "hr-rejeu-ajout-" + counter++ + ".json");
          historyRecords.add(hr);
          ReevaluationTree rt = new ReevaluationTree();
          reevaluationTrees.add(rt);
        }
      }
    }
  }

  /**
   * Reevaluate a model based on a parametric specification. This parametric specification may be
   * edited or not but must concern the same object.
   *
   * @param applications
   * @param historyRecords
   * @param reevaluationTrees
   * @throws IOException
   * @throws JerboaException
   * @throws InterruptedException
   * @throws InvocationTargetException
   */
  private void reevaluateModel(GMapViewer gmapviewer) {

    JerboaLongTaskWait longtask = new JerboaLongTaskWait();
    boolean cont = true;

    Integer counter = 0;
    List<JerboaRuleResult> appResults = new ArrayList<>();
    List<List<List<JerboaDart>>> topoParameters = new ArrayList<>();

    // For each application in edited parametric specification
    for (int applicationIndex = 0; applicationIndex < editedApplications
        .size(); applicationIndex++) {

      cont = longtask.waitUI();
      if (!cont) {
        break;
      }

      Application application = editedApplications.get(applicationIndex);
      List<JerboaDart> controlDarts = new ArrayList<>();
      int nbParameters = reevaluationTrees.get(counter).getTopoParameters().size();

      // compute topological parameters
      if (application.getApplicationType() != ApplicationType.ADD) {
        counter = collectTopologicalParameters(topoParameters, reevaluationTrees,
            application.getPersistentNames().size(), counter, nbParameters);
      } else {
        dartIDsToJerboaDarts(application.getDartIDs(), topoParameters, nbParameters);
        computeControlDart(controlDarts, application, topoParameters);
      }

      // apply application's rule once for each computed topological parameter
      try {
        if (application.getApplicationType() != ApplicationType.DELETE) {
          if (topoParameters.isEmpty()) {
            appResults.add(apply(application.getRule(), Arrays.asList()));
          } else {
            for (int paramIndex = 0; paramIndex < topoParameters.size(); paramIndex++) {
              appResults.add(apply(application.getRule(), topoParameters.get(paramIndex)));
            }
          }
          computeReevaluationTreeLevel(application, appResults, historyRecords, reevaluationTrees,
              controlDarts);
          gmapviewer.updateIHM();
        }
      } catch (JerboaException e) {
        e.printStackTrace();
      }

      // renew lists for next application
      topoParameters = new ArrayList<>();
      appResults = new ArrayList<>();

    }

    exportReevaluationTrees();
  }

  // NOTE: this is a workaround to get an effective distinction
  // between NOEFFECT and MERGE with added rules
  private void computeControlDart(List<JerboaDart> controlDarts, Application application,
      List<List<List<JerboaDart>>> topoParameters) {

    JerboaRebuiltRule rule = (JerboaRebuiltRule) application.getRule();
    JerboaRuleNode hook = rule.getHooks().get(0);
    int controlNodeIndex = rule.findClosestPreservedNode(hook);
    JerboaRuleNode controlNode = rule.getLeftRuleNode(controlNodeIndex);

    List<Integer> pathFromRootToControl =
        rule.collectLabelsFromSourceToClosestTarget(hook, Arrays.asList(controlNode), null);

    for (int paramIndex = 0; paramIndex < topoParameters.size(); paramIndex++) {
      JerboaDart controlDart = topoParameters.get(paramIndex).get(0).get(0);
      for (Integer label : pathFromRootToControl) {
        controlDart = controlDart.alpha(label);
      }
      controlDarts.add(controlDart);
    }
    // for (Integer label : pathFromRootToControl) {
    // controlDart = topoParameters.get(paramIndex).get(0).get(0).alpha(label);
    // }
    // return controlDart;
  }

  /**
   *
   * @param application
   * @param appResult
   * @param historyRecords
   * @param reevaluationTrees
   * @param i
   */
  private void computeReevaluationTreeLevel(Application application, List<JerboaRuleResult> appResults,
      List<HistoryRecord> historyRecords, List<ReevaluationTree> reevaluationTrees,
      List<JerboaDart> controlDarts) {

    for (int index = 0; index < historyRecords.size(); index++) {

      if (historyRecords.get(index).getLeaves().get(application.getApplicationID()) != null
          || application.getApplicationType() == ApplicationType.ADD) {

        LevelEventHR levelEventHR = null;

        List<LevelEventHR> levelEventHRs = historyRecords.get(index).getLeaves()
            .getOrDefault(application.getApplicationID(), new ArrayList<>());

        if (!levelEventHRs.isEmpty()) {
          levelEventHR = levelEventHRs.get(0);
        }

        ReevaluationTree rt = reevaluationTrees.get(index);

        // For each application result
        for (int appIndex = 0; appIndex < appResults.size(); appIndex++) {
        // Update current reevaluation tree
          if (application.getApplicationType() == ApplicationType.ADD) {
            rt.addLevel(historyRecords.get(index), levelEventHR, application, appResults.get(appIndex),
                controlDarts.get(appIndex));
          } else {
            rt.addLevel(historyRecords.get(index), levelEventHR, application, appResults.get(appIndex), null);
          }
        }
      }
    }
  }

  /**
   *
   * @param topoParameters
   * @param reevaluationTrees
   * @param nbPNs
   * @param counter
   * @param nbParameters
   * @return
   */
  private int collectTopologicalParameters(List<List<List<JerboaDart>>> topoParameters,
      List<ReevaluationTree> reevaluationTrees, int nbPNs, int counter, int nbParameters) {

    // for each pn add a topological parameter
    for (int i = 0; i < nbPNs; i++) {
      for (int index = 0; index < nbParameters; index++) {
        if (topoParameters.size() <= index) {
          // NOTE: new ArrayList<…>(Arrays.asList) keeps the list resizable thus permitting the
          // addition of elements
          topoParameters.add(new ArrayList<List<JerboaDart>>(Arrays
              .asList(Arrays.asList(reevaluationTrees.get(counter).getTopoParameter(index)))));
        } else
          topoParameters.get(index).add(new ArrayList<JerboaDart>(
              Arrays.asList(reevaluationTrees.get(counter).getTopoParameter(index))));
      }
      counter += 1;
    }
    return counter;

  }

  /**
   *
   * @param dartIDs
   * @param topoParameters
   * @return
   */
  private void dartIDsToJerboaDarts(List<Integer> dartIDs,
      List<List<List<JerboaDart>>> topoParameters, int nbParameters) {
    List<List<JerboaDart>> params = new ArrayList<>();
    for (Integer dartID : dartIDs) {
      params.add(new ArrayList<JerboaDart>(Arrays.asList(gmap.getNode(dartID))));
    }
    topoParameters.add(params);
  }

  /**
   *
   * @param rule
   * @param topoParameters
   * @return
   * @throws JerboaException
   */
  private JerboaRuleResult apply(JerboaRuleOperation rule, List<List<JerboaDart>> topoParameters)
      throws JerboaException {

    // apply rule and save its result until next application
    return rule.applyRule(gmap, new JerboaInputHooksGeneric(topoParameters));
  }

  public static void main(String[] args)
      throws JerboaException, IOException, InterruptedException, InvocationTargetException {

    final JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1024, 768);

    ModelerGenerated modeler = new ModelerGenerated();

    JerboaRebuiltBridge bridge = new JerboaRebuiltBridge(modeler);
    GMapViewer gmapviewer = new GMapViewer(frame, modeler, bridge);
    frame.getContentPane().add(gmapviewer);
    frame.setSize(1024, 768);
    frame.pack();

    frame.setPreferredSize(new Dimension(1024, 768));

    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);

    DemoRebuild demo = new DemoRebuild(bridge, //
        "./examples", //
        "article-2-build.json", //
        "./examples", //
        "article-2-build-reevaluation.json", //
        frame, gmapviewer);

    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        frame.invalidate();
        frame.repaint(1000);
        gmapviewer.updateIHM();
      }

    });

    Thread worker = new Thread(new Runnable() {

      @Override
      public void run() {
        demo.reevaluateModel(gmapviewer);
      }

    });

    worker.start();

  }
}
