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


def importRT(pathToRT):
    return json.load(open(pathToRT, "r"))


def computeEvent(branchIndex, appNumber, event, eventsChild):
    """Create a name and id for an event node."""
    return [
        str(appNumber)
        + "|"
        + str(branchIndex)
        + event["event"]
        + str(eventsChild["dim"]),
        event["event"],
    ]


def computeOrbit(branchIndex, appNumber, dartID, path, orbit):
    """Create a name and id for an orbit node."""
    pathStr = "".join("@" + str(l) + "." for l in path)
    # pathStr = str(path)
    orbitStr = str(orbit["dim"]).replace("[", "⟨").replace("]", "⟩")
    if not path:
        pathStr = ""
    return [
        str(appNumber) + "|" + str(branchIndex) + str(dartID) + str(orbit["dim"]),
        pathStr + orbitStr,
    ]


# def computeOrbit(appNumber, dartID, path, orbit):
#     """Create a name and id for an orbit node."""
#     return [
#         str(appNumber) + str(dartID) + str(orbit["dim"]),
#         str(path) + "·" + str(orbit["dim"]),
#     ]


def createEventNode(graph, branchIndex, appNumber, event, child):
    """Wrapping method for `graph.node()` to create an event node."""
    gEventName, eventStr = computeEvent(branchIndex, appNumber, event, child)
    graph.node(gEventName, eventStr, style="rounded")
    return gEventName


def createOrbitNode(graph, branchIndex, appNumber, dartID, path, keyValue):
    """Wrapping method for `graph.node()` to create an orbit node."""
    gOrbitName, orbitStr = computeOrbit(branchIndex, appNumber, dartID, path, keyValue)
    graph.node(gOrbitName, orbitStr)
    return gOrbitName


def drawEdge(graph, nameA, nameB, linkType):
    """Wrapping method for `graph.edge()` to decide edge's color."""
    if linkType == "ORIGIN":  # or linkType == "CREATION":
        link = "red"
    else:
        link = "black"
    graph.edge(nameA, nameB, color=link)


def drawEventLevel(graph, branchIndex, currentLevel, appNumber, dartID):
    """Gather data to create an event level and its nodes"""
    with graph.subgraph(
        name="cluster_" + str(appNumber) + ":" + str(branchIndex),
        node_attr={"style": "rounded, filled"},
    ) as eventCluster:
        eventCluster.attr(rank="same", style="rounded")
        eventCluster.node(str(appNumber) + ":" + str(branchIndex), color="lightgrey")

        for event in currentLevel["eventList"]:
            gEventName = createEventNode(
                graph,
                event["branchIndex"] + 1,
                appNumber,
                event,
                event["child"]["orbit"],
            )
            gOrbitName = createOrbitNode(
                graph,
                event["branchIndex"] + 1,
                appNumber,
                dartID,
                event["child"]["alphaPath"],
                event["child"]["orbit"],
            )
            eventCluster.node(gEventName)
            drawEdge(graph, gEventName, gOrbitName, event["event"])


def drawOrbitLevel(graph, branchIndex, currentLevel, appNumber, dartID):
    """Gather data to create an orbit level and its nodes and create the links to the next level's events"""
    with graph.subgraph(
        name="cluster_" + str(appNumber) + str(dartID),
    ) as orbitCluster:
        orbitCluster.attr(rank="same")
        orbitCluster.node(
            str(appNumber) + str(dartID), str(dartID), style="filled", color="lightblue"
        )

        for orbit in currentLevel["nextLevelOrbit"]["orbitList"]:
            gOrbitName = createOrbitNode(
                graph,
                orbit["branchIndex"] + 1,
                appNumber,
                dartID,
                orbit["alphaPath"],
                orbit["orbit"],
            )
            orbitCluster.node(gOrbitName)

            for link in orbit["children"]:
                gEventName = createEventNode(
                    graph,
                    link["child"]["branchIndex"] + 1,
                    ## if we have an actual list of levelEventRTs then how to decide which appNumber should pass ?
                    ## next line provides a list of appNumber collected from next LevelEventRTs which gives its first
                    ## index --> all these lists must share the same appNumber
                    [
                        levelEvent["appNumber"]
                        for levelEvent in currentLevel["nextLevelOrbit"][
                            "nextLevelEvents"
                        ]
                        if levelEvent is not None
                    ][0],
                    link["child"],
                    link["child"]["child"]["orbit"],
                )
                print(gEventName)
                drawEdge(graph, gOrbitName, gEventName, link["type"])


def drawLevel(level, branchIndex, graph):
    currentLevel = level
    appNumber = currentLevel["appNumber"]
    dartID = currentLevel["nextLevelOrbit"]["dartID"]

    # create a cluster for eventLevel
    drawEventLevel(graph, branchIndex, currentLevel, appNumber, dartID)

    # create a cluster for orbitLevel
    drawOrbitLevel(graph, branchIndex, currentLevel, appNumber, dartID)


def drawGraph(RT, exportPath):
    g = graphviz.Digraph("ReevaluationTree", node_attr={"shape": "record"})
    branchIndex = 0
    for branch in RT:
        # print(
        #     "number of levels for branch {} is {}".format(
        #         branchIndex, len(RT[branchIndex])
        #     )
        # )
        branchIndex += 1
        print("Level's appNumber is {}".format(branch[0]["appNumber"]))
        for level in branch:
            drawLevel(level, branchIndex, g)
    g.render(exportPath)


if __name__ == "__main__":
    usage(sys.argv)
    pathToRT = sys.argv[1]
    RT = importRT(pathToRT)
    exportPath = (
        "../exports/" + os.path.splitext(pathToRT)[0].split("/")[-1] + "-export"
    )
    drawGraph(RT, exportPath)
