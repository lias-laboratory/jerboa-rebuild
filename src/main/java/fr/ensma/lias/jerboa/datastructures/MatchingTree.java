package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaRuleResult;

public class MatchingTree {

    // LevelOrbitMT root;
    JerboaDart topoParameter; // the last dartID computed at the end of the matching process

    List<List<LevelEventMT>> leaves;

    /**
     * Data structure to match a history record against a GMap and build a model from a history
     * record.
     */
    public MatchingTree() {
        leaves = new ArrayList<>();
    }

    public MatchingTree(JerboaRuleResult appResult, SpecificationEntry specEntry,
            HistoryRecord HR) {

        leaves = new ArrayList<>();

        // if spec is unedited

        // for (LevelEventHR level : HR.getLeaf(0)) {
        // // addLevel(level, spec, appResults);
        // addLevel(level, specEntry, appResult);
        // }

        // else (spec is edited)
        // -- op added
        // -- op deleted
        // -- op moved
        // -- geometry modified (see if we should move it to `unedited` phase)
    }


    public JerboaDart getTopoParameter() {
        return topoParameter;
    }

    public void addInitLevel(LevelEventHR level, SpecificationEntry prevSpecEntry,
            SpecificationEntry curSpecEntry, JerboaRuleResult appResult) {

        // System.out.println("specentry:" + prevSpecEntry);

        ApplicationType appType = prevSpecEntry.getAppType();
        int appNumber = level.getAppNumber();
        String nodeName = level.getNextLevelOrbitHR().getNodeName();

        // nodeNameToDartID(appNumber, nodeName, appResult.get(appNumber), spec);
        nodeNameToDartID(appNumber, nodeName, appResult, prevSpecEntry);

        // in this case appType is *always* INIT
        // ApplicationType appType = spec.getSpecEntry(appNumber).getAppType();

        LevelEventMT levelEvent =
                new LevelEventMT(level.getAppNumber(), level.getEventList(), appType);
        List<LevelEventMT> nextLeveleventMTs = Arrays.asList(levelEvent);

        if (!leaves.isEmpty()) {
            getLastLevel().get(0).getNextLevelOrbit().setNextLevelEventMTList(nextLeveleventMTs);
        }

        LevelOrbitMT levelOrbit = new LevelOrbitMT(topoParameter.getID(),
                level.getNextLevelOrbitHR().getOrbitList(), null);
        levelEvent.nextLevelOrbit = levelOrbit;
        // root = levelOrbit;
        leaves.add(nextLeveleventMTs);

        // System.out.println(level + "\n" + levelEvent + "\n" + levelOrbit);

    }

    /**
     * Method to convert a nodeName from HR to an actual dart id (NID) the gmap can recognize when
     * applying a rule
     */
    private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
            SpecificationEntry specEntry) {

        int rowIndex = 0;
        for (int i = 0; i < appResult.sizeCol(); i++) {
            for (int j = 0; j < appResult.sizeRow(i); j++) {
                if (appResult.get(i, j) == topoParameter) {
                    rowIndex = j;
                }
            }
        }

        int colIndex = specEntry.getRule().getRightIndexRuleNode(nodeName);
        // System.out.println("colIndex " + colIndex);

        // System.out.println(
        // appResult.sizeCol() + " - " + colIndex + " - " + rowIndex + " - " + nodeName);
        JerboaDart dart = appResult.get(colIndex, rowIndex);
        this.topoParameter = dart;
    }

    private List<LevelEventMT> getLastLevel() {
        return leaves.get(leaves.size() - 1);
    }

}
