package fr.ensma.lias.jerboa;

import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

// FIXME: Les plongements "semblent partagés"

public class JerboaRebuiltModeler extends JerboaRebuilt {

    protected JerboaEmbeddingInfo vertexTracker;
    protected JerboaEmbeddingInfo halfFaceTracker;

    JerboaRebuiltModeler() throws JerboaException {

        super();

        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
                OrbitLabel.class);
        this.addEmbedding(vertexTracker);
        halfFaceTracker = new JerboaEmbeddingInfo("halfFaceTracker", JerboaOrbit.orbit(0, 1),
                OrbitLabel.class);
        this.addEmbedding(halfFaceTracker);
        /* «Le plongement semble être partagé entre plusieurs modeleurs» */
        this.registerEbdsAndResetGMAP(getPos(), vertexTracker, halfFaceTracker);


    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

    public final JerboaEmbeddingInfo getHalfFaceTracker() {
        return halfFaceTracker;
    }


}
