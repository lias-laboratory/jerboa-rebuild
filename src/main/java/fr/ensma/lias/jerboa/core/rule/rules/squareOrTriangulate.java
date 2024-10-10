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
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceAndCover;

/* End raw Imports */



/**
 * 
 */



public class squareOrTriangulate extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public squareOrTriangulate(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "squareOrTriangulate", "");

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
        int nbVerts = gmap.collect(hooks.dart(0, 0),JerboaOrbit.orbit(0,1,3),JerboaOrbit.orbit(1,3)).size();
		if((nbVerts == 4)) {
		   JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		   _v_hook2.addCol(((JerboaInputHooksGeneric)hooks).getCol(0));
		   ((TriangulateFace)modeler.getRule("TriangulateFace")).applyRule(gmap, _v_hook2);
		}
		else {
		   JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		   _v_hook3.addCol(((JerboaInputHooksGeneric)hooks).getCol(0));
		   ((PierceFaceAndCover)modeler.getRule("PierceFaceAndCover")).applyRule(gmap, _v_hook3);
		}
		return null;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart face() {
        return curleftPattern.getNode(0);
    }

} // end rule Class