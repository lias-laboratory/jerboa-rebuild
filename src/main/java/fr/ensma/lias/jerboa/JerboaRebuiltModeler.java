package fr.ensma.lias.jerboa;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.JerboaRebuilt;

// FIXME Out Of Bound while applying a rule

public class JerboaRebuiltModeler extends JerboaModelerGeneric {

    private JerboaRebuilt modeler;
    protected JerboaEmbeddingInfo vertexTracker;
    protected JerboaEmbeddingInfo halfFaceTracker;

    JerboaRebuiltModeler() throws JerboaException {

        super(3);

        modeler = new JerboaRebuilt();

        /* get modeler's embeddings */
        this.ebds.addAll(modeler.getAllEmbedding());
        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1, 2, 3),
                fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);
        halfFaceTracker = new JerboaEmbeddingInfo("halfFaceTracker", JerboaOrbit.orbit(0, 1),
                fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

        /* get modeler's rules */
        this.rules.addAll(modeler.getRules());

        this.ebds.clear();
        this.registerEbdsAndResetGMAP(modeler.getPos(), vertexTracker, halfFaceTracker);

    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

    public final JerboaEmbeddingInfo getHalfFaceTracker() {
        return halfFaceTracker;
    }

}
