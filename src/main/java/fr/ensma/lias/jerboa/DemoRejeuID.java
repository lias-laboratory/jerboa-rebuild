package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.HistoryRecord;
import fr.ensma.lias.jerboa.datastructures.LevelEventHR;
import fr.ensma.lias.jerboa.datastructures.MatchingTree;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecifications;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.SpecificationEntry;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.exception.JerboaException;

/**
 * This class' purpose is to demonstrate the reevaluation without change of a model built with
 * parametric operations
 */
public class DemoRejeuID {

    public static void main(String[] args) throws JerboaException, IOException {

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

        JerboaGMap gmap = bridge.getGMap(); // gmap in which we rebuild the model

        // NOTE: rules with suppression and merge are not yet supported!
        ParametricSpecifications spec = JSONPrinter.importParametricSpecification("./exports",
                "rebuild-add-vertex.json", modeler);
        List<SpecificationEntry> specEntries = spec.getSpec();

        List<HistoryRecord> historyRecords = new ArrayList<>();
        List<MatchingTree> matchingTrees = new ArrayList<>();

        // compute and store all history records
        for (var specEntry : specEntries) {
            var PNs = specEntry.getPNs();

            for (PersistentName PN : PNs) {
                JerboaOrbit orbitType = PN.getOrbitType();
                var PIs = PN.getPIs();

                for (PersistentID PI : PIs) {
                    // Compute and export HRs from current spec
                    HistoryRecord hr = new HistoryRecord(PI, orbitType, spec);
                    historyRecords.add(hr);
                    MatchingTree mt = new MatchingTree();
                    matchingTrees.add(mt);

                }
            }
        }

        int counter = 0;
        JerboaRuleResult appResult = null;
        int previousAppNumber = -1;
        for (var specEntry : specEntries) {
            List<List<JerboaDart>> topoParameters = new ArrayList<>();
            int appNumber = specEntry.getAppID();
            System.out.println("current specEntry: " + specEntry);

            // compute if current entry has at list one topological parameter
            if (!specEntry.getPNs().isEmpty()) {

                // for each history record compute a level for each matching tree
                for (int index = 0; index < historyRecords.size(); index++) {

                    // compute if current history record has a key for this entry
                    if (historyRecords.get(index).getLeaves().get(previousAppNumber) != null) {

                        List<LevelEventHR> levelEvent =
                                historyRecords.get(index).getLeaves().get(previousAppNumber);

                        System.out.println("current levelEvent: " + levelEvent);

                        MatchingTree currentMT = matchingTrees.get(index);

                        // add a level to the current matching tree
                        currentMT.addLevel(levelEvent.get(0), spec.getSpecEntry(previousAppNumber),
                                appResult);
                    }
                }

                // for each pn add a topological parameters
                for (int i = 0; i < specEntry.getPNs().size(); i++) {
                    System.out.println("auto" + counter + ".json");
                    historyRecords.get(counter).export("./exports", "auto" + counter + ".json");
                    topoParameters
                            .add(Arrays.asList(matchingTrees.get(counter++).getTopoParameter()));
                }
            }

            // apply rule and save its result until next application
            appResult = specEntry.getRule().applyRule(gmap,
                    new JerboaInputHooksGeneric(topoParameters));
            previousAppNumber = appNumber;
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame.invalidate();
                frame.repaint(1000);
                gmapviewer.updateIHM();
            }
        });

    }


}
