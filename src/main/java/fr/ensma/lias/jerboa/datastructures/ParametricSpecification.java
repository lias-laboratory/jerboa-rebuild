package fr.ensma.lias.jerboa.datastructures;

import fr.ensma.lias.jerboa.SpecEditor.Listeners.ParametricSpecificationListener;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

/**
 * A parametric specification holds the rules applied during the construction process of a model. It
 * also allows the model to be reevaluated.
 */
public class ParametricSpecification {

  /** The default name of an {@link ParametricSpecification} */
  public static final String DEFAULT_DISPLAY_NAME = "Initial Specification";

  /** Value used by the listener caller to indicates that the position isn't changed. */
  public static final int NO_POS_CHANGES = -1;

  private List<Application> spec;
  private int nextApplicationNumber;

  private String displayName;
  private boolean initialSpec;

  private ParametricSpecificationListener listener;

  public ParametricSpecification(final String displayName, boolean initialSpec) {
    this.displayName = displayName;
    spec = new ArrayList<>();
    nextApplicationNumber = 1;
    this.initialSpec = initialSpec;
  }

  public ParametricSpecification() {
    this(DEFAULT_DISPLAY_NAME, true);
  }

  public ParametricSpecification(
      final String displayName,
      List<Application> applications,
      int nextApplicationNumber,
      boolean initialSpec) {
    ArrayList<Application> apps = new ArrayList<Application>();
    for (Application app : applications) apps.add(new Application(app));

    this.displayName = displayName;
    this.spec = apps;
    this.nextApplicationNumber = nextApplicationNumber;
    this.initialSpec = initialSpec;
  }

  public ParametricSpecification(List<Application> applications, boolean initialSpec) {
    ArrayList<Application> apps = new ArrayList<Application>();
    for (Application app : applications) apps.add(new Application(app));

    this.displayName = DEFAULT_DISPLAY_NAME;
    this.spec = apps;
    this.nextApplicationNumber = 1;
    this.initialSpec = initialSpec;
  }

  public ParametricSpecification(List<Application> applications) {
    this(DEFAULT_DISPLAY_NAME, applications, 1, false);
  }

  /**
   * Copy constructor
   *
   * @param other ParametricSpecification must be copied
   */
  public ParametricSpecification(ParametricSpecification other) {
    // ArrayList<Application> apps = new ArrayList<Application>();
    // for (Application app : other.spec) apps.add(new Application(app));

    this.displayName = other.displayName;
    this.spec =
        other.spec.stream().map(Application::new).collect(Collectors.toCollection(ArrayList::new));
    // this.spec = other.spec;
    this.nextApplicationNumber = other.nextApplicationNumber;
    this.initialSpec = other.initialSpec;
  }

  /*
   * Adds an operation `Operation` in the field spec.
   *
   * @param name a string. It is the name of the operation being pushed.
   *
   * @param hooks a collection of JerboaDarts. Those are the operation's topological parameters.
   */
  public void addApplication(
      JerboaRuleOperation rule,
      List<PersistentName> PNs,
      List<Integer> dartIDs,
      ApplicationType appType)
      throws JerboaException {

    if (appType == ApplicationType.INIT && !this.initialSpec) {
      appType = ApplicationType.ADD;
    }

    if (this.listener != null) {

      int newpos = this.initialSpec ? spec.size() : this.listener.getCurrentIndex() + 1;

      Application specEntry =
          new Application(this.nextApplicationNumber, rule, PNs, dartIDs, appType, newpos);
      nextApplicationNumber++;

      if (newpos >= 0 && newpos < spec.size()) {
        spec.add(newpos, specEntry);
      } else {
        spec.add(specEntry);
      }

      switch (appType) {
        case INIT:
          this.listener.onInitApplication(specEntry, newpos);
          break;
        case ADD:
          this.listener.onAddApplication(specEntry, newpos);
          break;
        case DELETE:
          this.listener.onDeleteApplication(specEntry, newpos);
          break;
        case MOVE:
          this.listener.onMoveApplication(specEntry, this.listener.getCurrentIndex(), newpos);
          break;
      }
    } else {
      Application specEntry =
          new Application(this.nextApplicationNumber, rule, PNs, dartIDs, appType, spec.size());
      nextApplicationNumber++;
      spec.add(specEntry);
    }
  }

  /**
   * Update the type of an {@link Application} and trigger event if it's necessary
   *
   * @param app {@link Application} that must be modified
   * @param type New type
   * @throws JerboaException
   */
  public void updateApplicationType(Application app, ApplicationType type) throws JerboaException {
    if (this.spec.contains(app)) {

      if (this.listener != null && app.getApplicationType() != type) {
        app.setApplicationType(type);
        switch (type) {
          case INIT:
            break;
          case ADD:
            break;
          case DELETE:
            this.listener.onDeleteApplication(app, NO_POS_CHANGES);
            break;
          case MOVE:
            break;
        }
        this.listener.update();
      } else {
        app.setApplicationType(type);
      }

    } else {
      // TODO : Lever une vraie exception
      System.err.println(
          "ParametricSpecification.updateApplicationType > Application is not in the"
              + " specification");
      System.exit(-1);
    }
  }

  /**
   * Move an application from the oldindex to the newindex
   *
   * @param oldindex Index of the application to move
   * @param newindex New index of the application
   * @throws JerboaException
   */
  public void moveApplication(int oldindex, int newindex) throws JerboaException {

    if (this.initialSpec
        || oldindex == newindex
        || oldindex < 0
        || newindex < 0
        || oldindex >= this.spec.size()
        || newindex >= this.spec.size()) return;

    Application app = this.spec.get(oldindex);

    if (app.getApplicationType() == ApplicationType.ADD) {
      // Ne rien faire
    } else if (newindex == app.getInitialIndex()) {
      updateApplicationType(app, ApplicationType.INIT);
    } else {
      updateApplicationType(app, ApplicationType.MOVE);
    }

    this.spec.remove(app);
    this.spec.add(newindex, app);

    if (this.listener != null) {
      this.listener.onMoveApplication(app, oldindex, newindex);
    }
  }

  /*
   * Returns the nextEntryNumber field counting the number of elements in the parametric
   * specification.
   *
   * @return the nextEntryNumber field counting the number of elements in the parametric
   * specification.
   */
  public int getNextApplicationNumber() {
    return nextApplicationNumber;
  }

  /*
   * Returns full parametric specification which holds rule applications and persistent names for
   * each application.
   *
   * @return full parametric specification.
   */
  public List<Application> getApplications() {
    return spec;
  }

  /*
   * Returns a specified specification entry which holds persistent names and a rule.
   *
   * @param applicationNumber a given number used to identify a rule application
   *
   * @return specification entry which holds persistent names and a rule.
   */
  public Application getApplicationByID(int applicationID) {
    for (Application application : spec) {
      if (application.getApplicationID() == applicationID) return application;
    }
    return null;
  }

  /**
   * @param applicationID {@link Application} ID before the returned {@link Application}
   * @return Application after the {@link Application} represented by the id in parameter
   */
  public Application getApplicationAfter(int applicationID) {
    int index = 0;
    for (Application application : spec) {
      index++;
      if (application.getApplicationID() == applicationID) break;
    }

    return index < this.spec.size() ? this.spec.get(index) : null;
  }

  // public List<Application> getApplications() {
  // return this.spec;
  // }

  /**
   * @return The number of {@link Application} in the {@link ParametricSpecification}
   */
  public int size() {
    return this.spec.size();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Application e : spec) {
      sb.append(e.toString() + "\n");
    }
    return sb.toString();
  }

  private ArrayList<PersistentID> collectPersistentIDs() {
    // TODO impl: collect persistent IDs to store in a PN

    // for all darts filtered by a hook node, collect a PI if it contains an
    // appID not yet registered within the list to return;
    return new ArrayList<>();
  }

  /**
   * Compute topological parameters (persistent names) for the current rule being applied. This
   * method is very likely to change in order to aggregate PersistentIDs (at the moment only one is
   * collected to at least match uml pattern)
   *
   * @param hooks JerboaInputHooks. Collection of JerboaDart used to retrieve PersistentID
   * @return PNs. A LinkedList of PersistentName.
   */
  public LinkedList<PersistentName> computePersistentNames(
      JerboaRuleOperation rule, JerboaInputHooks inputHooks) {

    LinkedList<PersistentName> PNs = new LinkedList<>();
    Iterator<JerboaRuleNode> nodeIterator = rule.getHooks().iterator();
    for (Iterator<JerboaDart> dartIterator = inputHooks.iterator(); dartIterator.hasNext(); ) {
      JerboaDart dart = dartIterator.next();
      JerboaRuleNode node = nodeIterator.next();
      PersistentName PN = new PersistentName();
      PN.add(new PersistentID(dart.<PersistentID>ebd("PersistentID")));
      PN.setOrbitType(node.getOrbit());
      PNs.add(PN);
    }

    return PNs;
  }

  /*
   * Exports this parametric specification to a json file
   *
   * @param filename the name of the export file
   */
  public void export(String filename) {
    try {
      JSONPrinter.exportParametricSpecification(spec, filename);
    } catch (IOException exception) {
      System.out.println("Could not write to file");
    }
  }

  /**
   * @return The name to display
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Set the display name
   *
   * @param name The new name
   */
  public void setDisplayName(String name) {
    this.displayName = name;
  }

  /**
   * Define a listener for the {@link ParametricSpecification}
   *
   * @param listener {@link ParametricSpecificationListener} to set
   */
  public void setListener(ParametricSpecificationListener listener) {
    this.listener = listener;
  }

  /**
   * Remove definitively an {@link Application}
   *
   * @param app Application must be removed
   * @throws JerboaException
   */
  public void definitiveRemove(Application app) throws JerboaException {
    if (app.getApplicationType() == ApplicationType.ADD) {
      spec.remove(app);
      if (this.listener != null) {
        this.listener.onDefinitiveDeleteApplication(app);
        this.listener.update();
      }
    }
  }

  /**
   * @return If the {@link ParametricSpecification} is initial
   */
  public boolean isInitialSpec() {
    return initialSpec;
  }

  /**
   * Set if the {@link ParametricSpecification} is initial
   *
   * @param initialSpec value to sets
   */
  public void setInitialSpec(boolean initialSpec) {
    this.initialSpec = initialSpec;
  }
}
