package fr.ensma.lias.jerboa;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.JerboaRebuilt;



/**
 * 
 */



public class CreateSquareFace extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public CreateSquareFace(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "CreateSquareFace", "");

        // -------- LEFT GRAPH

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        rn0.setAlpha(1, rn1);
        rn1.setAlpha(0, rn2);
        rn2.setAlpha(1, rn3);
        rn3.setAlpha(0, rn4);
        rn4.setAlpha(1, rn5);
        rn5.setAlpha(0, rn6);
        rn6.setAlpha(1, rn7);
        rn7.setAlpha(0, rn0);
        rn1.setAlpha(2, rn1);
        rn1.setAlpha(3, rn1);
        rn2.setAlpha(2, rn2);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(2, rn3);
        rn3.setAlpha(3, rn3);
        rn4.setAlpha(2, rn4);
        rn4.setAlpha(3, rn4);
        rn5.setAlpha(2, rn5);
        rn5.setAlpha(3, rn5);
        rn6.setAlpha(2, rn6);
        rn6.setAlpha(3, rn6);
        rn7.setAlpha(2, rn7);
        rn7.setAlpha(3, rn7);
        rn0.setAlpha(2, rn0);
        rn0.setAlpha(3, rn0);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        case 4: return -1;
        case 5: return -1;
        case 6: return -1;
        case 7: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        case 4: return -1;
        case 5: return -1;
        case 6: return -1;
        case 7: return -1;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        return applyRule(gmap, ____jme_hooks);
	}

} // end rule Class