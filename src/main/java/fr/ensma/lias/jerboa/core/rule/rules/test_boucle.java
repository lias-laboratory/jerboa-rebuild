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
import fr.ensma.lias.jerboa.core.rule.rules.Dual.VertexDualToFaceSquare;

/* End raw Imports */



/**
 * 
 */



public class test_boucle extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public test_boucle(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "test_boucle", "");

        // -------- LEFT GRAPH
        JerboaRuleNode llin_skeleton = new JerboaRuleNode("lin_skeleton", 0, JerboaOrbit.orbit(), 3);
        left.add(llin_skeleton);
        hooks.add(llin_skeleton);

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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart lin_skeleton) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(lin_skeleton);
        return applyRule(gmap, ____jme_hooks);
	}

@Override
    public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks) throws JerboaException {
// BEGIN SCRIPT GENERATION
        for(JerboaDart vertex : gmap.collect(hooks.dart(0, 0),JerboaOrbit.orbit(0,1,2),JerboaOrbit.orbit(1,2))){
		   JerboaInputHooksGeneric _v_hook0 = new JerboaInputHooksGeneric();
		   _v_hook0.addCol(vertex);
		   ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).setCenter(vertex.<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0));
		   ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).setVOffset((Vec3.dist(vertex.<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),vertex.alpha(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0)) / 2));
		   JerboaRuleResult squareFace = ((VertexDualToFaceSquare)modeler.getRule("VertexDualToFaceSquare")).applyRule(gmap, _v_hook0);
		}
		
		return null;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart lin_skeleton() {
        return curleftPattern.getNode(0);
    }

} // end rule Class