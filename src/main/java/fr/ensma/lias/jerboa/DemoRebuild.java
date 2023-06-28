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
import fr.ensma.lias.jerboa.datastructures.MatchingTree;
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
	// private GMapViewer gmapviewer;
	private ParametricSpecification parametricSpecification;
	private List<Application> applications;
	private List<HistoryRecord> historyRecords;
	private List<MatchingTree> matchingTrees;
	private List<Application> editedApplications;
	// private JerboaRuleResult appResult;


	public DemoRebuild(JerboaRebuiltBridge bridge, String referenceDir, String referenceSpec,
			String editedDir, String editedSpec, JFrame frame, GMapViewer gmapviewer)
			throws IOException, JerboaException {

		this.gmap = bridge.getGMap(); // gmap in which we rebuild the model
		// this.gmapviewer = gmapviewer;
		ModelerGenerated modeler = (ModelerGenerated) bridge.getModeler();

		parametricSpecification =
				JSONPrinter.importParametricSpecification(referenceDir, referenceSpec, modeler);
		applications = parametricSpecification.getParametricSpecification();

		historyRecords = new ArrayList<>();
		matchingTrees = new ArrayList<>();

		initializeReevaluation();

		editedApplications =
				JSONPrinter.importParametricSpecification(editedDir, editedSpec, modeler)
						.getParametricSpecification();

		// reevaluateModel(editedApplications, historyRecords, matchingTrees, frame);
		// exportMatchingTrees(matchingTrees);
	}

	/**
	 * Organize the demo step by step.
	 *
	 * @param frame
	 * @param gmapviewer
	 * @throws InterruptedException
	 */
	// private synchronized void stepByStep(JFrame frame, GMapViewer gmapviewer) {}

	/**
	 *
	 * Export the matching trees created during the model's reevaluation
	 *
	 * @param matchingTrees
	 */
	// private void exportMatchingTrees(List<MatchingTree> matchingTrees) {
	private void exportMatchingTrees() {

		int counter = 0;
		for (MatchingTree mt : this.matchingTrees) {
			mt.export("exports", "matchingtree-" + counter + ".json");
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
	 * @param matchingTrees
	 */
	// private void initializeReevaluation(ParametricSpecification parametricSpecification,
	// List<Application> applications, List<HistoryRecord> historyRecords,
	// List<MatchingTree> matchingTrees) {
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
					MatchingTree mt = new MatchingTree();
					matchingTrees.add(mt);

				}
			}
		}

	}

	/**
	 * Reevaluate a model based on a parametric specification. This parametric specification may be
	 * edited or not but must concern the same object.
	 *
	 * @param swingWorker
	 *
	 * @param applications
	 * @param historyRecords
	 * @param matchingTrees
	 * @throws IOException
	 * @throws JerboaException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	// private synchronized void reevaluateModel(List<Application> applications,
	// List<HistoryRecord> historyRecords, List<MatchingTree> matchingTrees, JFrame frame)
	// throws IOException, JerboaException {
	private void reevaluateModel(GMapViewer gmapviewer) {
		JerboaLongTaskWait longtask = new JerboaLongTaskWait();
		boolean cont = true;

		Integer counter = 0;
		JerboaRuleResult appResult = null;
		List<List<JerboaDart>> topoParameters = new ArrayList<>();

		for (int applicationIndex = 0; applicationIndex < editedApplications
				.size(); applicationIndex++) {

			cont = longtask.waitUI();
			if (!cont) {
				break;
			}
			gmapviewer.clearDartSelection();

			// Application application = editedApplications.get(applicationIndex);
			// int nbPNs = application.getPersistentNames().size();

			// topoParameters = new ArrayList<>();

			// if (application.getApplicationType() != ApplicationType.ADD) {
			// counter =
			// collectTopologicalParameters(topoParameters, matchingTrees, nbPNs, counter);
			// } else {
			// topoParameters = dartIDsToJerboaDarts(application.getDartIDs(), topoParameters);
			// }

			// for (List<JerboaDart> parameter : topoParameters) {
			// gmapviewer.switchDartSelection(parameter);
			// }

			// try {
			// appResult = apply(application.getRule(), topoParameters);
			// gmapviewer.updateIHM();
			// } catch (JerboaException e) {
			// e.printStackTrace();
			// }
			Application application = editedApplications.get(applicationIndex);
			int nbPNs = application.getPersistentNames().size();
			JerboaDart controlDart = null;

			topoParameters = new ArrayList<>();

			if (application.getApplicationType() != ApplicationType.ADD) {
				counter =
						collectTopologicalParameters(topoParameters, matchingTrees, nbPNs, counter);
			} else {
				topoParameters = dartIDsToJerboaDarts(application.getDartIDs(), topoParameters);

				// REVIEW: this is a workaround to get an effective distinction
				// between NOEFFECT and MERGE with added rules
				JerboaRebuiltRule rule = (JerboaRebuiltRule) application.getRule();
				JerboaRuleNode hook = rule.getHooks().get(0);
				int controlNodeIndex = rule.findClosestPreservedNode(hook);
				JerboaRuleNode controlNode = rule.getLeftRuleNode(controlNodeIndex);

				List<Integer> pathFromRootToControl = rule.collectLabelsFromSourceToClosestTarget(
						hook, Arrays.asList(controlNode), null);

				for (Integer label : pathFromRootToControl) {
					controlDart = topoParameters.get(0).get(0).alpha(label);
				}
			}

			try {
				if (application.getApplicationType() != ApplicationType.DELETE) {
					appResult = apply(application.getRule(), topoParameters);
					gmapviewer.updateIHM();
				}
			} catch (JerboaException e) {
				e.printStackTrace();
			}

			computeMatchingTreeLevel(application, appResult, historyRecords, matchingTrees);

		}

		exportMatchingTrees();

	}


	/**
	 *
	 * @param application
	 * @param appResult
	 * @param historyRecords
	 * @param matchingTrees
	 */
	private void computeMatchingTreeLevel(Application application, JerboaRuleResult appResult,
			List<HistoryRecord> historyRecords, List<MatchingTree> matchingTrees) {

		for (int index = 0; index < historyRecords.size(); index++) {

			if (historyRecords.get(index).getLeaves().get(application.getApplicationID()) != null
					|| application.getApplicationType() == ApplicationType.ADD) {

				LevelEventHR levelEventHR = null;

				List<LevelEventHR> levelEventHRs = historyRecords.get(index).getLeaves()
						.getOrDefault(application.getApplicationID(), new ArrayList<>());

				if (!levelEventHRs.isEmpty()) {
					levelEventHR = levelEventHRs.get(0);
				}

				MatchingTree mt = matchingTrees.get(index);

				mt.addLevel(historyRecords.get(index), levelEventHR, application, appResult, null);
			}
		}
	}

	/**
	 *
	 * @param topoParameters
	 * @param matchingTrees
	 * @param nbPNs
	 * @param counter
	 * @return
	 */
	private int collectTopologicalParameters(List<List<JerboaDart>> topoParameters,
			List<MatchingTree> matchingTrees, int nbPNs, int counter) {
		// for each pn add a topological parameters
		for (int i = 0; i < nbPNs; i++) {
			topoParameters.add(Arrays.asList(matchingTrees.get(counter).getTopoParameter()));
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
	private List<List<JerboaDart>> dartIDsToJerboaDarts(List<Integer> dartIDs,
			List<List<JerboaDart>> topoParameters) {
		for (Integer dartID : dartIDs) {
			topoParameters.add(Arrays.asList(gmap.getNode(dartID)));
		}
		return topoParameters;
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
				// TODO Auto-generated method stub
				demo.reevaluateModel(gmapviewer);
			}

		});

		worker.start();

	}
}
