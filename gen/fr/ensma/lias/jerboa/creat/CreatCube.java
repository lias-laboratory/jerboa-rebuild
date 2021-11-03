package fr.ensma.lias.jerboa.creat;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.JerboaRebuilt;
import fr.ensma.lias.jerboa.embeddings.Points;
 // BEGIN HEADER IMPORT


import up.xlim.ig.jerboa.demo.extrusion.ExtrudeFace;

 // END HEADER IMPORT

/* Raw Imports : */

/* End raw Imports */



/**
 * 
 */



public class CreatCube extends JerboaRuleScript {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 

 // BEGIN COPY PASTE OF HEADER



 // END COPY PASTE OF HEADER


    public CreatCube(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "CreatCube", "creat");

        // -------- LEFT GRAPH

        // -------- RIGHT GRAPH
;
        // ------- USER DECLARATION 
 // BEGIN COPY PASTE OF HEADER

 // END COPY PASTE OF HEADER
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
        /*[Ljava.lang.StackTraceElement;@476d7b57*/// END SCRIPT GENERATION

	}
} // end rule Class