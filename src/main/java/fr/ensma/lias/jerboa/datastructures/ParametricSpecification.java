package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaRuleOperation;

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
	// TODO: remplacer les appels par GetApplications
	@Deprecated
	public List<Application> getParametricSpecification() {
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

	public List<Application> getApplications() {
		return this.spec;
	}

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
