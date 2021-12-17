package fr.ensma.lias.jerboa;

import java.util.ArrayList;
import java.util.List;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltModeler extends JerboaModelerGeneric {

    protected List<JerboaEmbeddingInfo> trackers;

    public JerboaRebuiltModeler(int dim) throws JerboaException {

        super(dim);

        trackers = new ArrayList<JerboaEmbeddingInfo>();
        trackers.add(new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
                fr.ensma.lias.jerboa.embeddings.OrbitLabel.class));

        // vertexTracker = trackers.add(vertexTracker);
        // registerEbdsAndResetGMAP(vertexTracker);

    }

    @Override
    public void registerEbdsAndResetGMAP(JerboaEmbeddingInfo... ebd) throws JerboaException {
        JerboaEmbeddingInfo[] ebds = new JerboaEmbeddingInfo[ebd.length + trackers.size()];

        for (int index = 0; index < ebd.length; index++) {
            ebds[index] = ebd[index];
        }
        for (int index = 0; index < trackers.size(); index++) {
            ebds[ebd.length + index] = trackers.get(index);
        }
        super.registerEbdsAndResetGMAP(ebds);
    }

    public final List<JerboaEmbeddingInfo> getTrackers() {
        return trackers;
    }
}
