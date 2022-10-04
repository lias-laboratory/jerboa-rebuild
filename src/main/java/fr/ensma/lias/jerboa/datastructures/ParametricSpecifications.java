package fr.ensma.lias.jerboa.datastructures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import up.jerboa.core.JerboaRuleOperation;

public class ParametricSpecifications {

	private List<SpecificationEntry> spec;
	private int nextEntryNumber;

	public ParametricSpecifications() {
		spec = new ArrayList<>();
		nextEntryNumber = 1;
	}

	public ParametricSpecifications(List<SpecificationEntry> entries) {
		spec = new ArrayList<>(entries);
	}

	/*
	 * Adds an operation `Operation` in the field spec.
	 *
	 * @param name a string. It is the name of the operation being pushed.
	 *
	 * @param hooks a collection of JerboaDarts. Those are the operation's topological parameters.
	 */
	public void addEntry(JerboaRuleOperation rule, List<PersistentName> PNs,
			ApplicationType appType) {
		SpecificationEntry specEntry =
				new SpecificationEntry(this.nextEntryNumber, rule, PNs, appType);
		spec.add(specEntry);
		nextEntryNumber++;
	}

	/*
	 * Returns the nextEntryNumber field counting the number of elements in the parametric
	 * specification.
	 *
	 * @return the nextEntryNumber field counting the number of elements in the parametric
	 * specification.
	 */
	public int getNextEntryNumber() {
		return nextEntryNumber;
	}

	/*
	 * Returns full parametric specification which holds rule applications and persistent names for
	 * each application.
	 *
	 * @return full parametric specification.
	 */
	public List<SpecificationEntry> getSpec() {
		return spec;
	}

	/*
	 * Returns a specified specification entry which holds persistent names and a rule.
	 *
	 * @param applicationNumber a given number used to identify a rule application
	 *
	 * @return specification entry which holds persistent names and a rule.
	 */
	public SpecificationEntry getSpecEntry(int appID) {
		for (SpecificationEntry specEntry : spec) {
			if (specEntry.getAppID() == appID)
				return specEntry;
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SpecificationEntry e : spec) {
			sb.append(e.toString() + "\n");
		}
		return sb.toString();
	}

	public void export(String filename) {
		try {
			JSONPrinter.exportParametricSpecification(spec, filename);
		} catch (IOException exception) {
			System.out.println("Could not write to file");
		}

	}

}
