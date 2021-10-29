package fr.ensma.lias.jerboa;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.CreateDart;
import fr.ensma.lias.jerboa.ExtrudeIndependantFace;
import fr.ensma.lias.jerboa.ExtrudeVolumeFace;
import fr.ensma.lias.jerboa.CreateSquareFace;
import fr.ensma.lias.jerboa.CreateEdge;
import fr.ensma.lias.jerboa.InsertVertex;

/**
 * Trace
 *
 * entrée: liste de référence
 *
 * traitement: pour chaque orbite d'une gmap //
 */

public class JerboaRebuilt extends JerboaModelerGeneric {

    // BEGIN LIST OF EMBEDDINGS
    protected JerboaEmbeddingInfo pos;
    protected JerboaEmbeddingInfo vertexTracker;
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public JerboaRebuilt() throws JerboaException {

        super(3);

    // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

    // END USER HEAD CONSTRUCTOR TRANSLATION 
        pos = new JerboaEmbeddingInfo("pos", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.Vec3.class);
        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

        this.registerEbdsAndResetGMAP(pos,vertexTracker);

        this.registerRule(new CreateDart(this));
        this.registerRule(new ExtrudeIndependantFace(this));
        this.registerRule(new ExtrudeVolumeFace(this));
        this.registerRule(new CreateSquareFace(this));
        this.registerRule(new CreateEdge(this));
        this.registerRule(new InsertVertex(this));

        this.applyRule(this.rules.get(3), new JerboaInputHooksAtomic());

        traceOrbits(this.gmap);
    }

    public final JerboaEmbeddingInfo getPos() {
        return pos;
    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

}
