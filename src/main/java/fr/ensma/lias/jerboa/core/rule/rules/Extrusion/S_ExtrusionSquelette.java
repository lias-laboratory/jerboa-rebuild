package fr.ensma.lias.jerboa.core.rule.rules.Extrusion;


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
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentVertex;
import fr.ensma.lias.jerboa.core.rule.rules.EdgeDim2;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeEdgeVertexYAxis;

/* End raw Imports */



/**
 * 
 */



public class S_ExtrusionSquelette extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public S_ExtrusionSquelette(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "S_ExtrusionSquelette", "Extrusion");

        // -------- LEFT GRAPH
        JerboaRuleNode lbrin = new JerboaRuleNode("brin", 0, JerboaOrbit.orbit(), 3);
        left.add(lbrin);
        hooks.add(lbrin);
        lbrin.setAlpha(0, lbrin);
        lbrin.setAlpha(1, lbrin);
        lbrin.setAlpha(2, lbrin);
        lbrin.setAlpha(3, lbrin);

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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart brin) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(brin);
        return applyRule(gmap, ____jme_hooks);
	}

@Override
    public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks) throws JerboaException {
// BEGIN SCRIPT GENERATION
        JerboaInputHooksGeneric _v_hook0 = new JerboaInputHooksGeneric();
		_v_hook0.addCol(hooks.dart(0, 0));
		JerboaRuleResult arete1 = ((ExtrudeIndependentVertex)modeler.getRule("ExtrudeIndependentVertex")).applyRule(gmap, _v_hook0);
		JerboaInputHooksGeneric _v_hook1 = new JerboaInputHooksGeneric();
		_v_hook1.addCol(hooks.dart(0, 0));
		((EdgeDim2)modeler.getRule("EdgeDim2")).applyRule(gmap, _v_hook1);
		JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		_v_hook2.addCol(hooks.dart(0, 0).alpha(0));
		JerboaRuleResult arete2 = ((ExtrudeEdgeVertexYAxis)modeler.getRule("ExtrudeEdgeVertexYAxis")).applyRule(gmap, _v_hook2);
		JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		_v_hook3.addCol(hooks.dart(0, 0).alpha(0).alpha(1).alpha(0));
		JerboaRuleResult arete3 = ((ExtrudeEdgeVertexYAxis)modeler.getRule("ExtrudeEdgeVertexYAxis")).applyRule(gmap, _v_hook3);
		return null;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart brin() {
        return curleftPattern.getNode(0);
    }

} // end rule Class