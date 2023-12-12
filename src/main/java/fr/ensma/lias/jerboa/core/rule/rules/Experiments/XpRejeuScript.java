package fr.ensma.lias.jerboa.core.rule.rules.Experiments;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.experiments.MesScriptsRejeu;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;

/* Raw Imports : */

/* End raw Imports */



/**
 * 
 */



public class XpRejeuScript extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public XpRejeuScript(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "XpRejeuScript", "Experiments");

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
        return MesScriptsRejeu.exe1(modeler,gmap,hooks);
		// END SCRIPT GENERATION

	}
} // end rule Class
