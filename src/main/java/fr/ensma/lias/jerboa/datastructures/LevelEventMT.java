package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * LevelEventMT
 */
public class LevelEventMT {

    public int appNumber;
    public ApplicationType appType;
    public List<NodeEventHR> eventList;
    public LevelOrbitMT nextLevelOrbit;

    public LevelEventMT(List<NodeEventHR> eventList, ApplicationType appType) {
        this.eventList = eventList;
        this.appType = appType;
    }

    public LevelEventMT(int appNumber, List<NodeEventHR> eventList, ApplicationType appType) {
        this.appNumber = appNumber;
        this.appType = appType;
        this.eventList = eventList;
    }

    public LevelEventMT() {}

    public LevelOrbitMT getNextLevelOrbit() {
        return nextLevelOrbit;
    }

    @Override
    public String toString() {
        return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
    }

}
