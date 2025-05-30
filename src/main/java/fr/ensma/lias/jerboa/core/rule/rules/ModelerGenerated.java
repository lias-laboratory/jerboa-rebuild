package fr.ensma.lias.jerboa.core.rule.rules;
import fr.ensma.lias.jerboa.core.JerboaRebuiltModeler;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertVertex;
import fr.ensma.lias.jerboa.core.rule.rules.Sew.SewA2;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateCorner2D;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateDart;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateEdge;
import fr.ensma.lias.jerboa.core.rule.rules.Split.ChamferCorner;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertEdge;
import fr.ensma.lias.jerboa.core.rule.rules.Merge.EdgeCollapse;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.DeleteIsolatedFace;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeEdge;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeFaceEdgeZAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFace;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVolumeFaceXAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.Merge.FaceCollapse;
import fr.ensma.lias.jerboa.core.rule.rules.Split.SplitFaceDiag;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.DeleteVertex;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.RemoveVertex;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.DeleteEdgeBorder;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.SplitSquareFacePartial;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.InsertEdgeReduced;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.TriangulateSquareFaceFull;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.CollapseTriangularFace;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.CollapseTriangularFace2;
import fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples.RemoveEdgeReduced;
import fr.ensma.lias.jerboa.core.rule.rules.Modif.EdgeFlip;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeFaceEdgeXAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeFaceEdgeYAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.DeleteFace;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertVertexFolded;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVolumeFaceZAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVolumeFaceYAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Sew.SewA3;
import fr.ensma.lias.jerboa.core.rule.rules.UnSew.UnSewA3;
import fr.ensma.lias.jerboa.core.rule.rules.Merge.MergeHFaces;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateVolume;
import fr.ensma.lias.jerboa.core.rule.rules.Merge.MergeVolumesAroundVertex;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceAndCover;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFace;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreatePentagon;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertVertexUnfoldedV2;
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceBlue;
import fr.ensma.lias.jerboa.core.rule.rules.Testing.FaceA3ToPyramid;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.SubdivTri;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.SubdivQuad;
import fr.ensma.lias.jerboa.core.rule.rules.UnSew.UnSewA2;
import fr.ensma.lias.jerboa.core.rule.rules.Duplication.DuplicateAndTranslateCC;
import fr.ensma.lias.jerboa.core.rule.rules.UnSew.Unsew012;
import fr.ensma.lias.jerboa.core.rule.rules.FilRouge;
import fr.ensma.lias.jerboa.core.rule.rules.TriOrColor;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriPlusVerts;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.Rainure2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.RainureDouble2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.RainureDivise2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.Retrecissement2D;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivQuadFake;
import fr.ensma.lias.jerboa.core.rule.rules.Experiments.XpRejeuScript;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivTriFake;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.CatmullClark;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertBorderEdge;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceCoverDiamond;
import fr.ensma.lias.jerboa.core.rule.rules.Split.QuadrangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.TriPlusVertsFake;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.TriangulateFaceFake;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.QuadrangulateFaceFake;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.InsertBorderEdgeFake;
import fr.ensma.lias.jerboa.core.rule.rules.Experiments.XpRejeuScript2;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.PierceFaceAndCoverFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.PierceFaceCoverDiamondFake;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.DooSabin;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDivise2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.Retrecissement2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.Rainure2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDouble2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.Split.ChamferCorner2D;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.ChamferCorner2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.InsertVertexFoldedFake;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.CreateSquareFaceFake;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.CreatePentagonFake;
import fr.ensma.lias.jerboa.core.rule.rules.Experiments.XpRejeuScriptIfElse;
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceRed;
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceGreen;
import fr.ensma.lias.jerboa.core.rule.rules.S_extrudeStructure;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentVertex;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeEdgeVertexYAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.S_ExtrusionSquelette;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.S_VertexDualToVolume;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateTriangle;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFaceWithSide;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.S_DualToSkeleton;
import fr.ensma.lias.jerboa.core.rule.rules.ExtrudeFaceToPyramid;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVertexToBranch;
import fr.ensma.lias.jerboa.core.rule.rules.ExtrudeVertexToBranchFolded;
import fr.ensma.lias.jerboa.core.rule.rules.Suppression.DeleteCC;
import fr.ensma.lias.jerboa.core.rule.rules.test_boucle;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFaceSquare;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.S_VertexDualToFace;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFace;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFaceTri;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.S_DualToSkeleton2D;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFaceZAxis;
import fr.ensma.lias.jerboa.core.rule.rules.EdgeDim2;
import fr.ensma.lias.jerboa.core.rule.rules.squareOrTriangulate;
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceOrange;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;



/**
 * 
 */

public class ModelerGenerated extends JerboaRebuiltModeler {

    // BEGIN LIST OF EMBEDDINGS
    protected JerboaEmbeddingInfo pos;
    protected JerboaEmbeddingInfo color;
    protected JerboaEmbeddingInfo norm;
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public ModelerGenerated() throws JerboaException {

        super(3);

    // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

    // END USER HEAD CONSTRUCTOR TRANSLATION
        pos = new JerboaEmbeddingInfo("pos", JerboaOrbit.orbit(1,2,3), fr.ensma.lias.jerboa.embeddings.Vec3.class);
        color = new JerboaEmbeddingInfo("color", JerboaOrbit.orbit(0,1), java.awt.Color.class);
        norm = new JerboaEmbeddingInfo("norm", JerboaOrbit.orbit(), fr.ensma.lias.jerboa.embeddings.Vec3.class);

        this.registerEbdsAndResetGMAP(pos,color,norm);

        this.registerRule(new InsertVertex(this));
        this.registerRule(new SewA2(this));
        this.registerRule(new CreateCorner2D(this));
        this.registerRule(new CreateDart(this));
        this.registerRule(new CreateEdge(this));
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
        this.registerRule(new DeleteVertex(this));
        this.registerRule(new RemoveVertex(this));
        this.registerRule(new DeleteEdgeBorder(this));
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
        this.registerRule(new MakeFaceBlue(this));
        this.registerRule(new FaceA3ToPyramid(this));
        this.registerRule(new SubdivTri(this));
        this.registerRule(new SubdivQuad(this));
        this.registerRule(new UnSewA2(this));
        this.registerRule(new DuplicateAndTranslateCC(this));
        this.registerRule(new Unsew012(this));
        this.registerRule(new FilRouge(this));
        this.registerRule(new TriOrColor(this));
        this.registerRule(new TriPlusVerts(this));
        this.registerRule(new Rainure2D(this));
        this.registerRule(new RainureDouble2D(this));
        this.registerRule(new RainureDivise2D(this));
        this.registerRule(new Retrecissement2D(this));
        this.registerRule(new SubdivQuadFake(this));
        this.registerRule(new XpRejeuScript(this));
        this.registerRule(new SubdivTriFake(this));
        this.registerRule(new CatmullClark(this));
        this.registerRule(new InsertBorderEdge(this));
        this.registerRule(new PierceFaceCoverDiamond(this));
        this.registerRule(new QuadrangulateFace(this));
        this.registerRule(new TriPlusVertsFake(this));
        this.registerRule(new TriangulateFaceFake(this));
        this.registerRule(new QuadrangulateFaceFake(this));
        this.registerRule(new InsertBorderEdgeFake(this));
        this.registerRule(new XpRejeuScript2(this));
        this.registerRule(new PierceFaceAndCoverFake(this));
        this.registerRule(new PierceFaceCoverDiamondFake(this));
        this.registerRule(new DooSabin(this));
        this.registerRule(new RainureDivise2DFake(this));
        this.registerRule(new Retrecissement2DFake(this));
        this.registerRule(new Rainure2DFake(this));
        this.registerRule(new RainureDouble2DFake(this));
        this.registerRule(new ChamferCorner2D(this));
        this.registerRule(new ChamferCorner2DFake(this));
        this.registerRule(new InsertVertexFoldedFake(this));
        this.registerRule(new CreateSquareFaceFake(this));
        this.registerRule(new CreatePentagonFake(this));
        this.registerRule(new XpRejeuScriptIfElse(this));
        this.registerRule(new MakeFaceRed(this));
        this.registerRule(new MakeFaceGreen(this));
        this.registerRule(new S_extrudeStructure(this));
        this.registerRule(new ExtrudeIndependentVertex(this));
        this.registerRule(new ExtrudeEdgeVertexYAxis(this));
        this.registerRule(new S_ExtrusionSquelette(this));
        this.registerRule(new S_VertexDualToVolume(this));
        this.registerRule(new CreateTriangle(this));
        this.registerRule(new ExtrudeIndependentFaceWithSide(this));
        this.registerRule(new S_DualToSkeleton(this));
        this.registerRule(new ExtrudeFaceToPyramid(this));
        this.registerRule(new ExtrudeVertexToBranch(this));
        this.registerRule(new ExtrudeVertexToBranchFolded(this));
        this.registerRule(new DeleteCC(this));
        this.registerRule(new test_boucle(this));
        this.registerRule(new VertexDualToFaceSquare(this));
        this.registerRule(new S_VertexDualToFace(this));
        this.registerRule(new VertexDualToFace(this));
        this.registerRule(new VertexDualToFaceTri(this));
        this.registerRule(new S_DualToSkeleton2D(this));
        this.registerRule(new ExtrudeIndependentFaceZAxis(this));
        this.registerRule(new EdgeDim2(this));
        this.registerRule(new squareOrTriangulate(this));
        this.registerRule(new MakeFaceOrange(this));
        this.registerRule(new CreateSquareFace(this));
    }

    public final JerboaEmbeddingInfo getPos() {
        return pos;
    }

    public final JerboaEmbeddingInfo getColor() {
        return color;
    }

    public final JerboaEmbeddingInfo getNorm() {
        return norm;
    }

}
