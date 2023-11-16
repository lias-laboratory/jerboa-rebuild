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
import fr.ensma.lias.jerboa.core.rule.rules.Color.MakeFaceBlue;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;

/* End raw Imports */



/**
 * 
 */



public class TriOrColor extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public TriOrColor(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "TriOrColor", "");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0,1), 3);
        left.add(ln0);
        hooks.add(ln0);

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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

@Override
    public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks) throws JerboaException {
// BEGIN SCRIPT GENERATION
        int compteur = 0;
		for(JerboaDart arete : gmap.collect(hooks.dart(0),JerboaOrbit.orbit(0,1),JerboaOrbit.orbit(0))){
		   compteur ++ ;
		}
		
		if((compteur == 3)) {
		   JerboaInputHooksGeneric _v_hook2 = new JerboaInputHooksGeneric();
		   _v_hook2.addCol(hooks.dart(0, 0));
		   JerboaRuleResult resBlue = ((MakeFaceBlue)modeler.getRule("MakeFaceBlue")).applyRule(gmap, _v_hook2);
		}
		else {
		   JerboaInputHooksGeneric _v_hook3 = new JerboaInputHooksGeneric();
		   _v_hook3.addCol(hooks.dart(0, 0));
		   JerboaRuleResult resTri = ((TriangulateFace)modeler.getRule("TriangulateFace")).applyRule(gmap, _v_hook3);
		}
		return null;
		// END SCRIPT GENERATION

	}
    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

} // end rule Class
