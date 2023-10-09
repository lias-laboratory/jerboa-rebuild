package fr.ensma.lias.jerboa.datastructures;

import java.util.List;

/** LevelEventMT */
public class LevelEventMT extends LevelEvent {

  // private int appNumber;
  // private ApplicationType appType;
  // private List<NodeEvent> eventList;
  private LevelOrbitMT nextLevelOrbit;

  public LevelEventMT(List<NodeEvent> eventList, ApplicationType appType) {
    this.appType = appType;
    this.eventList = eventList;
  }

  public LevelEventMT(int appNumber, List<NodeEvent> eventList, ApplicationType appType) {
    this.appNumber = appNumber;
    this.appType = appType;
    this.eventList = eventList;
  }

  public LevelEventMT() {}

  public List<NodeEvent> getEventList() {
    return eventList;
  }

  public void setEventList(List<NodeEvent> eventList) {
    for (NodeEvent newEventNode : eventList) {
      this.eventList.add(new NodeEvent(newEventNode.getEvent()));
    }
  }

  public LevelOrbitMT getNextLevelOrbit() {
    return nextLevelOrbit;
  }

  public void setNextLevelOrbit(LevelOrbitMT nextLevelOrbit) {
    this.nextLevelOrbit = nextLevelOrbit;
  }

  @Override
  public String toString() {
    return "appNumber " + appNumber + " | " + eventList + " --> " + nextLevelOrbit;
  }
}
