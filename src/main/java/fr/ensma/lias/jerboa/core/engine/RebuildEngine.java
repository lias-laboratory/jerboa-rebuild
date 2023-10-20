package fr.ensma.lias.jerboa.core.engine;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.ReevaluationTree2;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

public class RebuildEngine {

  private ParametricSpecification initialSpecification;
  private ParametricSpecification editedSpecification;
  private ArrayList<HistoryRecord> evaluationTrees;
  private ArrayList<ReevaluationTree2> reevaluationTrees;

  public RebuildEngine(
      ModelerGenerated modeler, String pathToSpecification, String pathToEditedSpecification)
      throws IOException, JerboaException {

    this.evaluationTrees = new ArrayList<HistoryRecord>();
    this.reevaluationTrees = new ArrayList<ReevaluationTree2>();

    this.initialSpecification =
        JSONPrinter.importParametricSpecification(pathToSpecification, modeler);

    this.editedSpecification =
        JSONPrinter.importParametricSpecification(pathToEditedSpecification, modeler);
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
    reevaluate(gmap, gmapviewer, this.editedSpecification.getApplications().size());
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

    // TODO: setup a list for topological parameters
    // TODO: setup a list for application results

    // Go through the applications registered within the edited specification
    for (Application application : editedSpecification.getApplications()) {

      if (application.getApplicationID() == stopApplicationID) {
        // Stop reevaluation here.
        break;
      }

      // if the current application is added
      // TODO: convert dartIDs to JerboaDarts
      // TODO: compute control darts (or better: orbits) to check for merge, delete and noeffect
      // else
      // TODO: Collect topological parameters

      // TODO: Apply current application as many time as needed
      try {
        application.getRule().apply(gmap, null);
        gmapviewer.updateIHM();
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    }
  }

  /* export both evaluation and reevaluation trees */
  public void exportTrees() {

    for (int index = 0; index < evaluationTrees.size(); index++) {
      this.evaluationTrees.get(index).export("./export", "evaluationTree-" + index + ".json");
      this.reevaluationTrees.get(index).export("./export", "reevaluationTree-" + index + ".json");
    }
  }
}
