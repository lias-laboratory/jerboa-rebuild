package fr.ensma.lias.jerboa.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PersistentID
 */
public class PersistentID {

	List<PersistentIdElement> pIdElements;

	public PersistentID() {
		this.pIdElements = new ArrayList<PersistentIdElement>();
	}

	public PersistentID(PersistentID oldPI) {
		this.pIdElements = new LinkedList<>(oldPI.getPIdElements());
	}

	public void add(PersistentIdElement PIElement) {
		this.pIdElements.add(PIElement);
	}

	/*
	 * Duplicate dart's PI to current PI
	 *
	 * @param dart JerboaDart from which to get old PersistentID
	 *
	 * @param embeddingID index of PersistentID in current modeler
	 */
	// public void duplicatePI(JerboaDart dart, int embeddingID) {
	// if (dart != null) {
	// // PersistentID oldPI = dart.<PersistentID>ebd(embeddingID);
	// // for (PersistentIdElement PIElement : oldPI.getPIdElements()) {
	// // this.pIdElements.add(PIElement);
	// // }
	// }
	// }

	public List<PersistentIdElement> getPIdElements() {
		return pIdElements;
	}

	@Override
	public String toString() {
		String output = pIdElements.stream().map(n -> n.toString())
				.collect(Collectors.joining("; ", "{", "}"));
		return output;
	}

}
