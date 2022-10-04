package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import up.jerboa.core.JerboaOrbit;

public class PersistentName {

	private List<PersistentID> PIs;
	private JerboaOrbit orbitType;


	public PersistentName() {
		PIs = new ArrayList<>();
	}

	public PersistentName(PersistentName oldPIs, JerboaOrbit orbitType) {
		for (PersistentID PI : oldPIs.getPIs()) {
			this.PIs.add(PI);
		}
		this.PIs = oldPIs.getPIs();
		this.orbitType = orbitType;
	}

	public List<PersistentID> getPIs() {
		return PIs;
	}

	public void add(PersistentID PI) {
		this.PIs.add(PI);
	}

	public JerboaOrbit getOrbitType() {
		return orbitType;
	}

	public void setOrbitType(JerboaOrbit orbitType) {
		this.orbitType = orbitType;
	}

	@Override
	public String toString() {
		// StringBuilder sb = new StringBuilder();
		// sb.append(PN.toString());
		// return sb.toString();
		String output =
				PIs.stream().map(n -> n.toString()).collect(Collectors.joining(", ", "[", "]"));
		return output;
	}

}

