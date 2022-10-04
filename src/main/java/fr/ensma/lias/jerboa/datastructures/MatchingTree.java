package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaRuleResult;

public class MatchingTree {

    // List<LevelEventMT> leaves = new ArrayList<>(); // as for the history record, leaves are top
    // level levelEvents
    // LevelEventHR leaf;
    LevelOrbitMT root;
    JerboaDart topoParameter; // the last dartID computed at the end of the matching process

    // public MatchingTree(Map<Integer, JerboaRuleResult> appResults, ParametricSpecifications spec,
    // HistoryRecord HR) {

    /**
     * Data structure to match a history record against a GMap and build a model from a history
     * record.
     */
    public MatchingTree() {}

    public MatchingTree(JerboaRuleResult appResult, SpecificationEntry specEntry,
            HistoryRecord HR) {

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

    // public void addLevel(LevelEventHR level, ParametricSpecifications spec,
    // Map<Integer, JerboaRuleResult> appResults) {
    public void addLevel(LevelEventHR level, SpecificationEntry specEntry,
            JerboaRuleResult appResult) {

        int appNumber = level.getAppNumber();
        String nodeName = level.getNextLevelOrbitHR().getNodeName();

        // nodeNameToDartID(appNumber, nodeName, appResult.get(appNumber), spec);
        nodeNameToDartID(appNumber, nodeName, appResult, specEntry);

        // in this case appType is *always* INIT
        // ApplicationType appType = spec.getSpecEntry(appNumber).getAppType();
        ApplicationType appType = specEntry.getAppType();

        LevelEventMT levelEvent =
                new LevelEventMT(level.getAppNumber(), level.getEventList(), appType);
        if (root != null) {
            root.setNextLevelEventMTList(Arrays.asList(levelEvent));
        }
        LevelOrbitMT levelOrbit = new LevelOrbitMT(topoParameter.getID(),
                level.getNextLevelOrbitHR().getOrbitList(), null);
        levelEvent.nextLevelOrbit = levelOrbit;
        root = levelOrbit;

        System.out.println(level + "\n" + levelEvent + "\n" + levelOrbit);

    }

    /**
     * Method to convert a nodeName from HR to an actual dart id (NID) the gmap can recognize when
     * applying a rule
     */
    // private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
    // ParametricSpecifications spec) {
    private void nodeNameToDartID(int AppNumber, String nodeName, JerboaRuleResult appResult,
            SpecificationEntry specEntry) {

        // JerboaDart dartID = null;

        // JerboaRuleResult appResult = ruleAppResults.get(currentAppNumber);

        int rowIndex = 0;
        for (int i = 0; i < appResult.sizeCol(); i++) {
            for (int j = 0; j < appResult.sizeRow(i); j++) {
                if (appResult.get(i, j) == topoParameter) {
                    rowIndex = j;
                }
            }
        }


        // int colIndex = spec.getSpecEntry(AppNumber).getRule().getRightIndexRuleNode(nodeName);
        int colIndex = specEntry.getRule().getRightIndexRuleNode(nodeName);
        System.out.println("colIndex " + colIndex);

        System.out.println(
                appResult.sizeCol() + " - " + colIndex + " - " + rowIndex + " - " + nodeName);
        JerboaDart dart = appResult.get(colIndex, rowIndex);
        this.topoParameter = dart;
    }

}
