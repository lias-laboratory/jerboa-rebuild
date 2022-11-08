package fr.ensma.lias.jerboa.core;

import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltModeler extends JerboaModelerGeneric {

	// protected List<JerboaEmbeddingInfo> trackers;
	protected JerboaEmbeddingInfo persistentID;
	public ParametricSpecification spec;

	public JerboaRebuiltModeler(int dim) throws JerboaException {

		super(dim);

		persistentID =
				new JerboaEmbeddingInfo("PersistentID", JerboaOrbit.orbit(), PersistentID.class);
		spec = new ParametricSpecification();
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
	}

	public final JerboaEmbeddingInfo getPersistentID() {
		return persistentID;
	}

}
