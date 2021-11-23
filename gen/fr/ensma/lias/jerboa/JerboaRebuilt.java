package fr.ensma.lias.jerboa;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.Creation.CreateDart;
import fr.ensma.lias.jerboa.ExtrudeIndependantFace;
import fr.ensma.lias.jerboa.ExtrudeVolumeFace;
import fr.ensma.lias.jerboa.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.Creation.CreateEdge;
import fr.ensma.lias.jerboa.InsertVertex;
import fr.ensma.lias.jerboa.NeedFix.ChanfreinCoin2D;
import fr.ensma.lias.jerboa.MergeEdge;
import fr.ensma.lias.jerboa.NeedFix.VertexCollapse;
import fr.ensma.lias.jerboa.ExtrudeEdge;
import fr.ensma.lias.jerboa.ExtrudeFaceEdge;
import fr.ensma.lias.jerboa.UnSewA2;
import fr.ensma.lias.jerboa.SewA2;
import fr.ensma.lias.jerboa.Creation.CreateCorner2D;
import fr.ensma.lias.jerboa.NeedFix.RemoveEdge;
import fr.ensma.lias.jerboa.DeleteFace;



/**
 * 
 */

public class JerboaRebuilt extends JerboaModelerGeneric {

    // BEGIN LIST OF EMBEDDINGS
    protected JerboaEmbeddingInfo pos;
    protected JerboaEmbeddingInfo vertexTracker;
    protected JerboaEmbeddingInfo halfFaceTracker;
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public JerboaRebuilt() throws JerboaException {

        super(3);

    // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

    // END USER HEAD CONSTRUCTOR TRANSLATION
        pos = new JerboaEmbeddingInfo("pos", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.Vec3.class);
        vertexTracker = new JerboaEmbeddingInfo("vertexTracker", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);
        halfFaceTracker = new JerboaEmbeddingInfo("halfFaceTracker", JerboaOrbit.orbit(0,1), fr.ensma.lias.jerboa.embeddings.OrbitLabel.class);

        this.registerEbdsAndResetGMAP(pos,vertexTracker,halfFaceTracker);

        this.registerRule(new CreateDart(this));
        this.registerRule(new ExtrudeIndependantFace(this));
        this.registerRule(new ExtrudeVolumeFace(this));
        this.registerRule(new CreateSquareFace(this));
        this.registerRule(new CreateEdge(this));
        this.registerRule(new InsertVertex(this));
        this.registerRule(new ChanfreinCoin2D(this));
        this.registerRule(new MergeEdge(this));
        this.registerRule(new VertexCollapse(this));
        this.registerRule(new ExtrudeEdge(this));
        this.registerRule(new ExtrudeFaceEdge(this));
        this.registerRule(new UnSewA2(this));
        this.registerRule(new SewA2(this));
        this.registerRule(new CreateCorner2D(this));
        this.registerRule(new RemoveEdge(this));
        this.registerRule(new DeleteFace(this));
    }

    public final JerboaEmbeddingInfo getPos() {
        return pos;
    }

    public final JerboaEmbeddingInfo getVertexTracker() {
        return vertexTracker;
    }

    public final JerboaEmbeddingInfo getHalfFaceTracker() {
        return halfFaceTracker;
    }

}
