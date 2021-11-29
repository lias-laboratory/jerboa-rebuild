package fr.ensma.lias.jerboa;

import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

// FIXME: Il me semble judicieux de travailler sur le modeleur via une classe
// qui l'étend mais pour le moment ça ne fonctionne pas plus que ça.

public class JerboaRebuiltModeler extends JerboaRebuilt {

    protected JerboaEmbeddingInfo vertexTracker;
    // protected JerboaEmbeddingInfo halfFaceTracker;

    JerboaRebuiltModeler() throws JerboaException {

        super();

        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
                OrbitLabel.class);
        this.addEmbedding(vertexTracker);
        /* «Le plongement semble être partagé entre plusieurs modeleurs» */
        this.registerEbdsAndResetGMAP(vertexTracker);

        // halfFaceTracker = this.getEmbedding("halfFaceTracker");

    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

    // public final JerboaEmbeddingInfo getHalfFaceTracker() {
    // return halfFaceTracker;
    // }


}
