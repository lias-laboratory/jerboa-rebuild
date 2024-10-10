package fr.ensma.lias.jerboa.core.rule.rules.Dual;


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
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFaceSquare;
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFaceTri;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFaceZAxis;

/* End raw Imports */



/**
 * 
 */



public class S_VertexDualToVolume extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public S_VertexDualToVolume(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "S_VertexDualToVolume", "Dual");

        // -------- LEFT GRAPH
        JerboaRuleNode lvertex = new JerboaRuleNode("vertex", 0, JerboaOrbit.orbit(), 3);
        left.add(lvertex);
        hooks.add(lvertex);

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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart vertex) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(vertex);
        return applyRule(gmap, ____jme_hooks);
	}

@Override
    public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks) throws JerboaException {
// BEGIN SCRIPT GENERATION
        float offset = Vec3.dist(hooks.dart(0, 0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),hooks.dart(0, 0).alpha(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0));
		Vec3 center = new Vec3(hooks.dart(0, 0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0));
		JerboaRuleResult face = null;
		int nb_verts = gmap.collect(hooks.dart(0, 0),JerboaOrbit.orbit(1,2,3),JerboaOrbit.orbit(2,3)).size();
		if(((nb_verts > 0) && (nb_verts < 3))) {
		   JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		   _v_hook2.addCol(hooks.dart(0, 0));
		   ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).setCenter(center);
		   ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).setVOffset((offset / 2));
		   face = ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).applyRule(gmap, _v_hook2);
		}
		else {
		   JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		   _v_hook3.addCol(hooks.dart(0, 0));
		   ((VertexDualToFaceTri)modeler.getRule("VertexDualToFaceTri")).setCenter(center);
		   ((VertexDualToFaceTri)modeler.getRule("VertexDualToFaceTri")).setVOffset((offset / 2));
		   face = ((VertexDualToFaceTri)modeler.getRule("VertexDualToFaceTri")).applyRule(gmap, _v_hook3);
		}
		JerboaInputHooksGeneric _v_hook4 = new JerboaInputHooksGeneric();
		_v_hook4.addCol(face.get(0).get(0));
		JerboaRuleResult volume = ((ExtrudeIndependentFaceZAxis)modeler.getRule("ExtrudeIndependentFaceZAxis")).applyRule(gmap, _v_hook4);
		return volume;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart vertex() {
        return curleftPattern.getNode(0);
    }

} // end rule Class