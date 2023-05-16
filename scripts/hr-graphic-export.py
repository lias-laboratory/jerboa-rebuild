#!/usr/bin/env python3

import graphviz
import json
import sys
import os


def usage(nbArgs):
    if len(nbArgs) != 2:
        raise ValueError(
            "Argument must be a path to a .json export of an history record!"
        )


def importHR(pathToHR):
    return json.load(open(pathToHR, "r"))


def computeEvent(appNumber, event, eventsChild):
    """Create a name and id for an event node."""
    return [str(appNumber) + event["event"] + str(eventsChild["dim"]), event["event"]]


def computeOrbit(appNumber, nodeName, path, orbit):
    """Create a name and id for an orbit node."""
    pathStr = "".join("@" + str(l) + "." for l in path)
    # pathStr = str(path)
    orbitStr = str(orbit["dim"]).replace("[", "⟨").replace("]", "⟩")
    if not path:
        pathStr = ""
    return [str(appNumber) + nodeName + str(orbit["dim"]), pathStr + orbitStr]


def createEventNode(graph, appNumber, event, child):
    """Wrapping method for `graph.node()` to create an event node."""
    gEventName, eventStr = computeEvent(appNumber, event, child)
    graph.node(gEventName, eventStr, style="rounded")
    return gEventName


def createOrbitNode(graph, appNumber, nodeName, path, keyValue):
    """Wrapping method for `graph.node()` to create an orbit node."""
    gOrbitName, orbitStr = computeOrbit(appNumber, nodeName, path, keyValue)
    graph.node(gOrbitName, orbitStr)
    return gOrbitName


def drawEdge(graph, nameA, nameB, linkType):
    """Wrapping method for `graph.edge()` to decide edge's color."""
    if linkType == "ORIGIN" or linkType == "CREATION":
        link = "red"
    else:
        link = "black"
    graph.edge(nameA, nameB, color=link)


def drawLevel(level, graph):
    currentLevel = level[1][0]
    appNumber = currentLevel["appNumber"]
    nodeName = currentLevel["nextLevelOrbit"]["nodeName"]
    # create a cluster for eventLevel
    with graph.subgraph(
        name="cluster_" + str(appNumber), node_attr={"style": "rounded, filled"}
    ) as eventCluster:
        eventCluster.attr(rank="same", style="rounded")
        eventCluster.node(str(appNumber), color="lightgrey")

        for event in currentLevel["eventList"]:
            gEventName = createEventNode(
                graph, appNumber, event, event["child"]["orbit"]
            )
            gOrbitName = createOrbitNode(
                graph,
                appNumber,
                nodeName,
                event["child"]["alphaPath"],
                event["child"]["orbit"],
            )
            eventCluster.node(gEventName)
            drawEdge(graph, gEventName, gOrbitName, event["event"])

    # create a cluster for orbitLevel
    with graph.subgraph(
        name="cluster_" + str(appNumber) + nodeName,
    ) as orbitCluster:
        orbitCluster.attr(rank="same")
        orbitCluster.node(
            str(appNumber) + nodeName, nodeName, style="filled", color="lightblue"
        )
        for orbit in currentLevel["nextLevelOrbit"]["orbitList"]:
            gOrbitName = createOrbitNode(
                graph, appNumber, nodeName, orbit["alphaPath"], orbit["orbit"]
            )
            orbitCluster.node(gOrbitName)
            for event in orbit["children"]:
                gEventName = createEventNode(
                    graph,
                    # if we have an actual list of levelEventHRs then how to decide which appNumber should pass ?
                    # next line provides a list of appNumber collected from next LevelEventHRs which gives its first
                    # index
                    [
                        levelEvent["appNumber"]
                        for levelEvent in currentLevel["nextLevelOrbit"][
                            "nextLevelEvents"
                        ]
                        if levelEvent is not None
                    ][0],
                    event["child"],
                    event["child"]["child"]["orbit"],
                )
                drawEdge(graph, gOrbitName, gEventName, event["type"])


def drawGraph(HR, exportPath):
    g = graphviz.Digraph("HistoryRecord", node_attr={"shape": "record"})
    for level in reversed(HR.items()):
        drawLevel(level, g)
    g.render(exportPath)


if __name__ == "__main__":
    usage(sys.argv)
    pathToHR = sys.argv[1]
    HR = importHR(pathToHR)
    exportPath = (
        "../exports/" + os.path.splitext(pathToHR)[0].split("/")[-1] + "-export"
    )
    drawGraph(HR, exportPath)
