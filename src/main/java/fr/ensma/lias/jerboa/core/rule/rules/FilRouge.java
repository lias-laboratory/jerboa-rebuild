package fr.ensma.lias.jerboa.core.rule.rules;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;
import fr.ensma.lias.jerboa.embeddings.Vec3;

/* Raw Imports : */
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFace;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertVertexFolded;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVolumeFaceYAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.Merge.FaceCollapse;
import fr.ensma.lias.jerboa.core.rule.rules.Split.ChamferCorner;
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceBlue;

/* End raw Imports */



/**
 * 
 */



public class FilRouge extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public FilRouge(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "FilRouge", "");

        // -------- LEFT GRAPH

        // -------- RIGHT GRAPH
;
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        return -1;
    }

    public int attachedNode(int i) {
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        return applyRule(gmap, ____jme_hooks);
	}

@Override
    public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks) throws JerboaException {
// BEGIN SCRIPT GENERATION
        JerboaInputHooksGeneric _v_hook0 = new JerboaInputHooksGeneric();
		JerboaRuleResult resSquare = ((CreateSquareFace)modeler.getRule("CreateSquareFace")).applyRule(gmap, _v_hook0);
		JerboaInputHooksGeneric _v_hook1 = new JerboaInputHooksGeneric();
		_v_hook1.addCol(resSquare.get(6));
		JerboaRuleResult resCube1 = ((ExtrudeIndependentFace)modeler.getRule("ExtrudeIndependentFace")).applyRule(gmap, _v_hook1);
		JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		_v_hook2.addCol(resCube1.get(4).get(7));
		JerboaRuleResult resVertex = ((InsertVertexFolded)modeler.getRule("InsertVertexFolded")).applyRule(gmap, _v_hook2);
		JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		_v_hook3.addCol(resCube1.get(5).get(7));
		JerboaRuleResult resCube2 = ((ExtrudeVolumeFaceYAxis)modeler.getRule("ExtrudeVolumeFaceYAxis")).applyRule(gmap, _v_hook3);
		JerboaInputHooksGeneric _v_hook4 = new JerboaInputHooksGeneric();
		_v_hook4.addCol(resCube1.get(1).get(5));
		JerboaRuleResult resTriangulation = ((TriangulateFace)modeler.getRule("TriangulateFace")).applyRule(gmap, _v_hook4);
		JerboaInputHooksGeneric _v_hook5 = new JerboaInputHooksGeneric();
		_v_hook5.addCol(resCube2.get(6).get(1));
		JerboaRuleResult resCollapse = ((FaceCollapse)modeler.getRule("FaceCollapse")).applyRule(gmap, _v_hook5);
		JerboaInputHooksGeneric _v_hook6 = new JerboaInputHooksGeneric();
		_v_hook6.addCol(resCollapse.get(0).get(1));
		JerboaRuleResult resChamfer = ((ChamferCorner)modeler.getRule("ChamferCorner")).applyRule(gmap, _v_hook6);
		JerboaInputHooksGeneric _v_hook7 = new JerboaInputHooksGeneric();
		_v_hook7.addCol(resTriangulation.get(0).get(2));
		((MakeFaceBlue)modeler.getRule("MakeFaceBlue")).applyRule(gmap, _v_hook7);
		return null;
		// END SCRIPT GENERATION

	}
} // end rule Class