package fr.ensma.lias.jerboa.datastructures;

import java.util.List;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;

/**
 * An application is an applied rule. It holds its ID type rule and persistent names. During the
 * construction of a model such application is stored within a {@link ParametricSpecification} for
 * future reevaluation
 */

public class Application {
	private int ID;
	private ApplicationType type;
	private JerboaRuleOperation rule;
	private List<PersistentName> PNs;
	// HACK: this is a hack to allow the use of dartIDs as topological parameters in added
	// applications
	private List<Integer> dartIDs;

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
	public Application(int ID, JerboaRuleOperation rule, List<PersistentName> PNs,
			ApplicationType type) {
		this.ID = ID;
		this.type = type;
		this.rule = rule;
		this.PNs = PNs;
		this.dartIDs = null;
	}

	/**
	 * Type of the application
	 *
	 * @return the type of this application
	 */
	public ApplicationType getApplicationType() {
		return type;
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
	 * Persistent names to identify topological parameters of this application
	 *
	 * @return the persistent names stored in this application
	 */
	public List<PersistentName> getPersistentNames() {
		return PNs;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: ").append(type).append(" ");
		sb.append("ID: ").append(ID).append(" ");
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
