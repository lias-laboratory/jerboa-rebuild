package fr.ensma.lias.jerboa.core;

import fr.ensma.lias.jerboa.datastructures.ParametricSpecifications;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltModeler extends JerboaModelerGeneric {

	// protected List<JerboaEmbeddingInfo> trackers;
	protected JerboaEmbeddingInfo persistentID;
	public ParametricSpecifications spec;

	public JerboaRebuiltModeler(int dim) throws JerboaException {

		super(dim);

		// trackers = new ArrayList<JerboaEmbeddingInfo>();
		// trackers.add(new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
		// fr.ensma.lias.jerboa.embeddings.OrbitLabel.class));
		// trackers.add(new JerboaEmbeddingInfo("halfFaceTracker", JerboaOrbit.orbit(0, 1),
		// fr.ensma.lias.jerboa.embeddings.OrbitLabel.class));
		// trackers.add(new JerboaEmbeddingInfo("halfEdgeTracker", JerboaOrbit.orbit(0),
		// fr.ensma.lias.jerboa.embeddings.OrbitLabel.class));
		// trackers.add(new JerboaEmbeddingInfo("edgeTracker", JerboaOrbit.orbit(0, 2, 3),
		// fr.ensma.lias.jerboa.embeddings.OrbitLabel.class));
		persistentID =
				new JerboaEmbeddingInfo("PersistentID", JerboaOrbit.orbit(), PersistentID.class);
		spec = new ParametricSpecifications();

	}

	@Override
	public void registerEbdsAndResetGMAP(JerboaEmbeddingInfo... ebd) throws JerboaException {
		int nbEbds = ebd.length + 1;
		JerboaEmbeddingInfo[] ebds = new JerboaEmbeddingInfo[ebd.length + 1];
		for (int index = 0; index < ebd.length; index++) {
			ebds[index] = ebd[index];
		}
		ebds[nbEbds - 1] = getPersistentID();
		super.registerEbdsAndResetGMAP(ebds);

		// JerboaEmbeddingInfo[] ebds = new JerboaEmbeddingInfo[ebd.length + trackers.size()];

		// for (int index = 0; index < ebd.length; index++) {
		// ebds[index] = ebd[index];
		// }
		// for (int index = 0; index < trackers.size(); index++) {
		// ebds[ebd.length + index] = trackers.get(index);
		// }
		// super.registerEbdsAndResetGMAP(ebd);
	}

	public final JerboaEmbeddingInfo getPersistentID() {
		return persistentID;
	}

	// public final List<JerboaEmbeddingInfo> getTrackers() {
	// return trackers;
	// }
}
