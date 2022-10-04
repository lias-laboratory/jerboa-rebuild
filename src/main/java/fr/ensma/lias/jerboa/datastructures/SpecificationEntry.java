package fr.ensma.lias.jerboa.datastructures;

import java.util.LinkedList;
import java.util.List;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;

public class SpecificationEntry {
	// private Stack s;
	private int appID;
	private ApplicationType appType;
	private JerboaRuleOperation rule;
	private List<PersistentName> PNs;

	/*
	 * SpecificationEntry stores information about the application of a rule. It stores diverses
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
	public SpecificationEntry(int id, JerboaRuleOperation rule, List<PersistentName> PNs,
			ApplicationType appType) {
		this.appID = id;
		this.appType = appType;
		this.rule = rule;
		this.PNs = PNs;
	}

	public ApplicationType getAppType() {
		return appType;
	}

	public int getOpID() {
		return appID;
	}

	public String getOpName() {
		return rule.getName();
	}

	public JerboaRuleOperation getRule() {
		return rule;
	}

	public void setRule(JerboaRuleOperation rule) {
		this.rule = rule;
	}

	public int getAppID() {
		return appID;
	}

	public void setAppID(int appID) {
		this.appID = appID;
	}

	public List<PersistentName> getPNs() {
		return PNs;
	}

	public void setPNs(LinkedList<PersistentName> pNs) {
		PNs = pNs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: ").append(appType).append(" ");
		sb.append("Application: ").append(appID).append(" ");
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
