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
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeIndependentFace;
import fr.ensma.lias.jerboa.core.rule.rules.Extrusion.ExtrudeVolumeFaceYAxis;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.InsertEdge;

/* End raw Imports */



/**
 * 
 */



public class S_extrudeStructure extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public S_extrudeStructure(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "S_extrudeStructure", "");

        // -------- LEFT GRAPH
        JerboaRuleNode lface = new JerboaRuleNode("face", 0, JerboaOrbit.orbit(), 3);
        left.add(lface);

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
		_v_hook0.addCol(hooks.dart(0, 0));
		JerboaRuleResult cube = ((ExtrudeIndependentFace)modeler.getRule("ExtrudeIndependentFace")).applyRule(gmap, _v_hook0);
		JerboaInputHooksGeneric _v_hook1 = new JerboaInputHooksGeneric();
		_v_hook1.addCol(cube.get(5).get(0));
		JerboaRuleResult tower1 = ((ExtrudeVolumeFaceYAxis)modeler.getRule("ExtrudeVolumeFaceYAxis")).applyRule(gmap, _v_hook1);
		JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		_v_hook2.addCol(tower1.get(6).get(0));
		JerboaRuleResult tower2 = ((ExtrudeVolumeFaceYAxis)modeler.getRule("ExtrudeVolumeFaceYAxis")).applyRule(gmap, _v_hook2);
		JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		_v_hook3.addCol(tower2.get(6).get(0));
		_v_hook3.addCol(tower2.get(6).get(0).alpha(0).alpha(1).alpha(0));
		JerboaRuleResult edge = ((InsertEdge)modeler.getRule("InsertEdge")).applyRule(gmap, _v_hook3);
		JerboaInputHooksGeneric _v_hook4 = new JerboaInputHooksGeneric();
		_v_hook4.addCol(edge.get(0).get(0));
		JerboaRuleResult branch1 = ((ExtrudeVolumeFaceYAxis)modeler.getRule("ExtrudeVolumeFaceYAxis")).applyRule(gmap, _v_hook4);
		JerboaInputHooksGeneric _v_hook5 = new JerboaInputHooksGeneric();
		_v_hook5.addCol(edge.get(0).get(1));
		JerboaRuleResult branch2 = ((ExtrudeVolumeFaceYAxis)modeler.getRule("ExtrudeVolumeFaceYAxis")).applyRule(gmap, _v_hook5);
		return null;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart face() {
        return curleftPattern.getNode(0);
    }

} // end rule Class