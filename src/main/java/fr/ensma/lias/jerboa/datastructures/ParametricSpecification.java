package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRuleNode;

/**
 * A parametric specification holds the rules applied during the construction process of a model. It
 * also allows the model to be reevaluated.
 */
public class ParametricSpecification {

	private List<Application> spec;
	private int nextApplicationNumber;

	public ParametricSpecification() {
		spec = new ArrayList<>();
		nextApplicationNumber = 1;
	}

	public ParametricSpecification(List<Application> applications) {
		spec = new ArrayList<>(applications);
	}

	/*
	 * Adds an operation `Operation` in the field spec.
	 *
	 * @param name a string. It is the name of the operation being pushed.
	 *
	 * @param hooks a collection of JerboaDarts. Those are the operation's topological parameters.
	 */
	public void addApplication(JerboaRuleOperation rule, List<PersistentName> PNs,
			ApplicationType appType) {
		Application specEntry = new Application(this.nextApplicationNumber, rule, PNs, appType);
		spec.add(specEntry);
		nextApplicationNumber++;
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
			if (application.getApplicationID() == applicationID)
				return application;
		}
		return null;
	}

	// public List<Application> getApplications() {
	// return this.spec;
	// }

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
	 * method is very likely to change in order to aggregate PersistentIDs (at the moment only one
	 * is collected to at least match uml pattern)
	 *
	 * @param hooks JerboaInputHooks. Collection of JerboaDart used to retrieve PersistentID
	 *
	 * @return PNs. A LinkedList of PersistentName.
	 */
	public LinkedList<PersistentName> computePersistentNames(JerboaRuleOperation rule,
			JerboaInputHooks inputHooks) {

		LinkedList<PersistentName> PNs = new LinkedList<>();
		Iterator<JerboaRuleNode> nodeIterator = rule.getHooks().iterator();
		for (Iterator<JerboaDart> dartIterator = inputHooks.iterator(); dartIterator.hasNext();) {
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

}
