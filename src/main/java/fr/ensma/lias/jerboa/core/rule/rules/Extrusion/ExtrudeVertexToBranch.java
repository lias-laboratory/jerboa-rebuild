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



public class ExtrudeVertexToBranch extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public ExtrudeVertexToBranch(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "ExtrudeVertexToBranch", "Extrusion");

        // -------- LEFT GRAPH
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 1, JerboaOrbit.orbit(3), 3);
        left.add(ln1);
        left.add(ln0);
        hooks.add(ln1);
        ln1.setAlpha(1, ln1);
        ln0.setAlpha(2, ln1);
        ln0.setAlpha(1, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 1, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn8 = new JerboaRuleNode("n8", 2, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn10 = new JerboaRuleNode("n10", 3, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn12 = new JerboaRuleNode("n12", 4, JerboaOrbit.orbit(3), 3, new ExtrudeVertexToBranchExprRn12pos());
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 5, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 6, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn9 = new JerboaRuleNode("n9", 7, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn11 = new JerboaRuleNode("n11", 8, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn16 = new JerboaRuleNode("n16", 9, JerboaOrbit.orbit(3), 3, new ExtrudeVertexToBranchExprRn16pos());
        right.add(rn0);
        right.add(rn6);
        right.add(rn8);
        right.add(rn10);
        right.add(rn12);
        right.add(rn1);
        right.add(rn7);
        right.add(rn9);
        right.add(rn11);
        right.add(rn16);
        rn0.setAlpha(1, rn6);
        rn6.setAlpha(2, rn8);
        rn6.setAlpha(0, rn10);
        rn8.setAlpha(0, rn12);
        rn10.setAlpha(2, rn12);
        rn12.setAlpha(1, rn12);
        rn10.setAlpha(1, rn10);
        rn1.setAlpha(1, rn7);
        rn7.setAlpha(2, rn9);
        rn9.setAlpha(0, rn16);
        rn7.setAlpha(0, rn11);
        rn11.setAlpha(2, rn16);
        rn16.setAlpha(1, rn16);
        rn11.setAlpha(1, rn11);
        rn8.setAlpha(1, rn8);
        rn9.setAlpha(1, rn9);
        rn0.setAlpha(2, rn1);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 1;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        case 4: return -1;
        case 5: return 0;
        case 6: return -1;
        case 7: return -1;
        case 8: return -1;
        case 9: return -1;
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
        case 6: return 0;
        case 7: return 0;
        case 8: return 0;
        case 9: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n1) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n1);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ExtrudeVertexToBranchExprRn12pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(( - 0.5f),0.5f,0.0f));
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

    private class ExtrudeVertexToBranchExprRn16pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(0.5f,0.5f,0.0f));
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

    private JerboaDart n0() {
        return curleftPattern.getNode(1);
    }

} // end rule Class