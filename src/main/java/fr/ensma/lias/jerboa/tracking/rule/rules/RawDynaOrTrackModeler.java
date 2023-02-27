package fr.ensma.lias.jerboa.tracking.rule.rules;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.tracking.rule.rules.Merge.MergeEdge;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.InsertVertex;
import fr.ensma.lias.jerboa.tracking.rule.rules.Sew.SewA2;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.CreateCorner2D;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.CreateDart;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.CreateEdge;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Split.ChamferCorner;
import fr.ensma.lias.jerboa.tracking.rule.rules.NeedFix.InsertEdge;
import fr.ensma.lias.jerboa.tracking.rule.rules.NeedFix.EdgeCollapse;
import fr.ensma.lias.jerboa.tracking.rule.rules.Suppression.DeleteIsolatedFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeEdge;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeFaceEdgeZAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeIndependentFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeVolumeFaceXAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Merge.FaceCollapse;
import fr.ensma.lias.jerboa.tracking.rule.rules.Split.SplitFaceDiag;
import fr.ensma.lias.jerboa.tracking.rule.rules.Merge.MergeFacesAroundVertex;
import fr.ensma.lias.jerboa.tracking.rule.rules.Suppression.DeleteVertex;
import fr.ensma.lias.jerboa.tracking.rule.rules.Suppression.RemoveVertex;
import fr.ensma.lias.jerboa.tracking.rule.rules.Suppression.DeleteEdge;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.SplitSquareFacePartial;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.InsertEdgeReduced;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.TriangulateSquareFaceFull;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.CollapseTriangularFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.CollapseTriangularFace2;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples.RemoveEdgeReduced;
import fr.ensma.lias.jerboa.tracking.rule.rules.Modif.EdgeFlip;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeFaceEdgeXAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeFaceEdgeYAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Suppression.DeleteFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.InsertVertexFolded;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeVolumeFaceZAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Extrusion.ExtrudeVolumeFaceYAxis;
import fr.ensma.lias.jerboa.tracking.rule.rules.Sew.SewA3;
import fr.ensma.lias.jerboa.tracking.rule.rules.UnSew.UnSewA3;
import fr.ensma.lias.jerboa.tracking.rule.rules.Merge.MergeHFaces;
import fr.ensma.lias.jerboa.tracking.rule.rules.Experiments.TriangulateVolume;
import fr.ensma.lias.jerboa.tracking.rule.rules.Experiments.MergeVolumesAroundVertex;
import fr.ensma.lias.jerboa.tracking.rule.rules.Experiments.PierceFaceAndCover;
import fr.ensma.lias.jerboa.tracking.rule.rules.Experiments.PierceFace;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.CreatePentagon;
import fr.ensma.lias.jerboa.tracking.rule.rules.Creation.InsertVertexUnfoldedV2;
import fr.ensma.lias.jerboa.tracking.rule.rules.MakeBlue;
import fr.ensma.lias.jerboa.tracking.rule.rules.Merge.MergeEdge2;
import fr.ensma.lias.jerboa.tracking.rule.rules.FaceToPyramid;
import fr.ensma.lias.jerboa.tracking.rule.rules.ExpandFaceToCube;



/**
 * 
 */

public class RawDynaOrTrackModeler extends JerboaModelerGeneric {

    // BEGIN LIST OF EMBEDDINGS
    protected JerboaEmbeddingInfo pos;
    protected JerboaEmbeddingInfo color;
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public RawDynaOrTrackModeler() throws JerboaException {

        super(3);

    // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

    // END USER HEAD CONSTRUCTOR TRANSLATION
        pos = new JerboaEmbeddingInfo("pos", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.Vec3.class);
        color = new JerboaEmbeddingInfo("color", JerboaOrbit.orbit(0,1), java.awt.Color.class);

        this.registerEbdsAndResetGMAP(pos,color);

        this.registerRule(new MergeEdge(this));
        this.registerRule(new InsertVertex(this));
        this.registerRule(new SewA2(this));
        this.registerRule(new CreateCorner2D(this));
        this.registerRule(new CreateDart(this));
        this.registerRule(new CreateEdge(this));
        this.registerRule(new CreateSquareFace(this));
        this.registerRule(new ChamferCorner(this));
        this.registerRule(new InsertEdge(this));
        this.registerRule(new EdgeCollapse(this));
        this.registerRule(new DeleteIsolatedFace(this));
        this.registerRule(new ExtrudeEdge(this));
        this.registerRule(new ExtrudeFaceEdgeZAxis(this));
        this.registerRule(new ExtrudeIndependentFace(this));
        this.registerRule(new ExtrudeVolumeFaceXAxis(this));
        this.registerRule(new TriangulateFace(this));
        this.registerRule(new FaceCollapse(this));
        this.registerRule(new SplitFaceDiag(this));
        this.registerRule(new MergeFacesAroundVertex(this));
        this.registerRule(new DeleteVertex(this));
        this.registerRule(new RemoveVertex(this));
        this.registerRule(new DeleteEdge(this));
        this.registerRule(new SplitSquareFacePartial(this));
        this.registerRule(new InsertEdgeReduced(this));
        this.registerRule(new TriangulateSquareFaceFull(this));
        this.registerRule(new CollapseTriangularFace(this));
        this.registerRule(new CollapseTriangularFace2(this));
        this.registerRule(new RemoveEdgeReduced(this));
        this.registerRule(new EdgeFlip(this));
        this.registerRule(new ExtrudeFaceEdgeXAxis(this));
        this.registerRule(new ExtrudeFaceEdgeYAxis(this));
        this.registerRule(new DeleteFace(this));
        this.registerRule(new InsertVertexFolded(this));
        this.registerRule(new ExtrudeVolumeFaceZAxis(this));
        this.registerRule(new ExtrudeVolumeFaceYAxis(this));
        this.registerRule(new SewA3(this));
        this.registerRule(new UnSewA3(this));
        this.registerRule(new MergeHFaces(this));
        this.registerRule(new TriangulateVolume(this));
        this.registerRule(new MergeVolumesAroundVertex(this));
        this.registerRule(new PierceFaceAndCover(this));
        this.registerRule(new PierceFace(this));
        this.registerRule(new CreatePentagon(this));
        this.registerRule(new InsertVertexUnfoldedV2(this));
        this.registerRule(new MakeBlue(this));
        this.registerRule(new MergeEdge2(this));
        this.registerRule(new FaceToPyramid(this));
        this.registerRule(new ExpandFaceToCube(this));
    }

    public final JerboaEmbeddingInfo getPos() {
        return pos;
    }

    public final JerboaEmbeddingInfo getColor() {
        return color;
    }

}
