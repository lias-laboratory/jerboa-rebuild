package fr.ensma.lias.jerboa.core.rule.rules.Extrusion;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;
import fr.ensma.lias.jerboa.embeddings.Vec3;



/**
 * 
 */



public class ExtrudeIndependentFace extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public ExtrudeIndependentFace(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "ExtrudeIndependentFace", "Extrusion");

        // -------- LEFT GRAPH
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(0,1), 3);
        left.add(ln1);
        hooks.add(ln1);
        ln1.setAlpha(2, ln1);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(0,1), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(0,-1), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(-1,2), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 3, JerboaOrbit.orbit(-1,2), 3, new ExtrudeIndependentFaceExprRn4pos());
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 4, JerboaOrbit.orbit(0,-1), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 5, JerboaOrbit.orbit(0,1), 3);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        rn1.setAlpha(2, rn2);
        rn6.setAlpha(2, rn5);
        rn2.setAlpha(1, rn3);
        rn5.setAlpha(1, rn4);
        rn3.setAlpha(0, rn4);
        rn2.setAlpha(3, rn2);
        rn5.setAlpha(3, rn5);
        rn6.setAlpha(3, rn6);
        rn4.setAlpha(3, rn4);
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
        case 4: return -1;
        case 5: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        case 2: return 0;
        case 3: return 0;
        case 4: return 0;
        case 5: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n1) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n1);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ExtrudeIndependentFaceExprRn4pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(0,1,0);
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
    private JerboaDart n1() {
        return curleftPattern.getNode(0);
    }

} // end rule Class