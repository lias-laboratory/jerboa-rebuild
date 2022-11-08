package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

/**
 * LevelOrbitMT a structure that registers orbits of interests when reconstructing a model in
 * matching trees.
 */
public class LevelOrbitMT {

    private int dartID;
    private List<NodeOrbitHR> orbitList;
    private List<LevelEventMT> nextLevelEvents;

    public LevelOrbitMT(int dartID, List<NodeOrbitHR> orbitList,
            List<LevelEventMT> nextLevelEventList) {
        this.dartID = dartID;
        this.orbitList = orbitList;
        this.nextLevelEvents = nextLevelEventList;
    }

    /*
     * Constructor
     *
     * @param nodeName a name identifying a node from which to find an adequate dartID
     *
     * @param orbitList a list of node orbits
     */
    public LevelOrbitMT(int dartID, List<NodeOrbitHR> orbitList) {
        this.dartID = dartID;
        this.orbitList = orbitList;
    }

    protected void setNextLevelEventMTList(List<LevelEventMT> nexLevelEventMTList) {
        this.nextLevelEvents = nexLevelEventMTList;
    }

    public List<LevelEventMT> getNextLevelEventMTList() {
        return nextLevelEvents;
    }

    public int getDartID() {
        return dartID;
    }

    public List<NodeOrbitHR> getOrbitList() {
        return orbitList;
    }

    @Override
    public String toString() {
        return "dartID " + dartID + " | " + orbitList;
    }

}
