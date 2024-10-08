package fr.ensma.lias.jerboa.core.rule.rules.Extrusion;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;


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



/**
 * 
 */



public class ExtrudeIndependentVertex extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public ExtrudeIndependentVertex(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "ExtrudeIndependentVertex", "Extrusion");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(0, ln0);
        ln0.setAlpha(1, ln0);
        ln0.setAlpha(2, ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3, new ExtrudeIndependentVertexExprRn1pos());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        rn0.setAlpha(0, rn1);
        rn0.setAlpha(1, rn0);
        rn0.setAlpha(3, rn0);
        rn1.setAlpha(1, rn1);
        rn1.setAlpha(3, rn1);
        rn2.setAlpha(0, rn3);
        rn0.setAlpha(2, rn2);
        rn1.setAlpha(2, rn3);
        rn2.setAlpha(1, rn2);
        rn3.setAlpha(1, rn3);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        case 2: return 0;
        case 3: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ExtrudeIndependentVertexExprRn1pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(0,0.5f,0);
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "pos";
        }

        @Override
        public int getEmbedding() {
            return ((ModelerGenerated)modeler).getPos().getID();
        }
    }

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

} // end rule Class