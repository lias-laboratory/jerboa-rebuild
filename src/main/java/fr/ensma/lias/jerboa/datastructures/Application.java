package fr.ensma.lias.jerboa.datastructures;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;

/**
 * An application is an applied rule. It holds its ID type rule and persistent names. During the
 * construction of a model such application is stored within a {@link ParametricSpecification} for
 * future reevaluation
 */
public class Application {
  private int ID;
  private int initialIndex;
  private ApplicationType type;
  private JerboaRuleOperation rule;
  private List<PersistentName> PNs;
  private List<Integer> dartIDs; // These are the current application's topological parameters

  /**
   * Application stores information about the application of a rule. It stores diverses information
   * such as an id, a name, its topological parameters and its geometric parameters.
   *
   * <p>An entry should look similar to this : 2 — Triangulation([[1—n0]]·<0,1>)
   *
   * @param ID
   * @param rule
   * @param PNs
   * @param dartIDs
   * @param type
   * @param initialIndex
   */
  public Application(
      int ID,
      JerboaRuleOperation rule,
      List<PersistentName> PNs,
      List<Integer> dartIDs,
      ApplicationType type,
      int initialIndex) {
    this.ID = ID;
    this.initialIndex = initialIndex;
    this.type = type;
    this.rule = rule;
    this.PNs = PNs;
    this.dartIDs = dartIDs;
  }

  /*
   * Application stores information about the application of a rule. It stores diverses
   * information such as an id, a name, its topological parameters and its geometric parameters.
   *
   * An entry should look similar to this : 2 — Triangulation([[1—n0]]·<0,1>)
   *
   * @param id int. Application index for the applied rule
   *
   * @param name string. Name of the rule applied
   *
   * @param PIs
   */
  public Application(
      int ID,
      JerboaRuleOperation rule,
      List<PersistentName> PNs,
      ApplicationType type,
      int initialIndex) {
    this(ID, rule, PNs, new ArrayList<>(), type, initialIndex);
  }

  /**
   * Copy-constructor of an application.
   *
   * @param application
   */
  public Application(Application other) {
    // FIXME: temporary revert deep copy as it breaks reevaluation in the spec editor
    // this.ID = other.ID;
    // this.rule = other.rule;
    // this.PNs =
    //     other.getPersistentNames().stream()
    //         .map(PersistentName::new)
    //         .collect(Collectors.toCollection(ArrayList::new));
    this(other.ID, other.rule, other.PNs, other.dartIDs, other.type, other.initialIndex);
  }

  /**
   * Type of the {@link Application}
   *
   * @return the type of this {@link Application}
   */
  public ApplicationType getApplicationType() {
    return type;
  }

  /**
   * Set type of an {@link Application}
   *
   * @param type New {@link ApplicationType} used by the {@link Application}
   */
  public void setApplicationType(ApplicationType type) {
    this.type = type;
  }

  /**
   * Name of the rule used for this application
   *
   * @return the name of the rule stored in this application
   */
  public String getName() {
    return rule.getName();
  }

  /**
   * Rule used for this application
   *
   * @return the rule stored in this application
   */
  public JerboaRuleOperation getRule() {
    return rule;
  }

  /**
   * ID of this application
   *
   * @return the ID associated to this application
   */
  public int getApplicationID() {
    return ID;
  }

  /**
   * Initial Index in the parametric specification Use for the MOVE type
   *
   * @return the initial index in the parametric specification
   */
  public int getInitialIndex() {
    return this.initialIndex;
  }

  /**
   * Set the new value of initialIndex
   *
   * @param index New value of initialIndex
   */
  public void setInitialIndex(int index) {
    this.initialIndex = index;
  }

  /**
   * Persistent names to identify topological parameters of this application
   *
   * @return the persistent names stored in this application
   */
  public List<PersistentName> getPersistentNames() {
    return PNs;
  }

  /**
   * @return The {@link List} of dartID
   */
  public List<Integer> getDartIDs() {
    return dartIDs;
  }

  // /**
  // * Set the rule for this application
  // *
  // * @param rule a given JerboaRuleOperation
  // */
  // public void setRule(JerboaRuleOperation rule) {
  // this.rule = rule;
  // }

  // /*
  // * 0
  // *
  // * @param appID
  // */
  // public void setAppID(int appID) {
  // this.appID = appID;
  // }

  // public void setPNs(LinkedList<PersistentName> pNs) {
  // PNs = pNs;
  // }

  public List<String> getGeoParams() {
    Class<? extends JerboaRuleOperation> ct = rule.getClass();

    Method[] methods = ct.getMethods();

    List<String> getters =
        Arrays.stream(methods)
            .map(m -> m.getName())
            .filter(m -> m.startsWith("get"))
            .map(m -> m.substring(3))
            .collect(Collectors.toList());

    List<String> setters =
        Arrays.stream(methods)
            .map(m -> m.getName())
            .filter(m -> m.startsWith("set"))
            .map(m -> m.substring(3))
            .collect(Collectors.toList());

    List<String> props =
        getters.stream().filter(s -> setters.contains(s)).collect(Collectors.toList());
    return props;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Type: ").append(type).append(" ");
    sb.append("ID: ").append(ID).append(" ");
    sb.append("InitialIndex: ").append(initialIndex).append(" ");
    sb.append("Name: ").append(rule.getName()).append("(");
    for (int index = 0; index < PNs.size(); index++) {
      var PN = PNs.get(index);
      JerboaOrbit orbit = rule.getHooks().get(index).getOrbit();
      sb.append(PN.toString() + "·" + orbit + "; ");
    }
    sb.append(")");
    return sb.toString();
  }
}
