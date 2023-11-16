package fr.ensma.lias.jerboa.core.rule.rules.Modif;
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



public class EdgeFlip extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public EdgeFlip(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "EdgeFlip", "Modif");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln8 = new JerboaRuleNode("n8", 8, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln9 = new JerboaRuleNode("n9", 9, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln10 = new JerboaRuleNode("n10", 10, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode ln11 = new JerboaRuleNode("n11", 11, JerboaOrbit.orbit(3), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        left.add(ln4);
        left.add(ln5);
        left.add(ln6);
        left.add(ln7);
        left.add(ln8);
        left.add(ln9);
        left.add(ln10);
        left.add(ln11);
        hooks.add(ln8);
        ln1.setAlpha(0, ln2);
        ln3.setAlpha(1, ln2);
        ln6.setAlpha(0, ln5);
        ln6.setAlpha(1, ln7);
        ln7.setAlpha(0, ln0);
        ln3.setAlpha(0, ln4);
        ln5.setAlpha(1, ln10);
        ln4.setAlpha(1, ln11);
        ln10.setAlpha(2, ln11);
        ln8.setAlpha(2, ln9);
        ln8.setAlpha(1, ln0);
        ln9.setAlpha(1, ln1);
        ln8.setAlpha(0, ln10);
        ln9.setAlpha(0, ln11);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn12 = new JerboaRuleNode("n12", 8, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn13 = new JerboaRuleNode("n13", 9, JerboaOrbit.orbit(3), 3, new EdgeFlipExprRn13pos());
        JerboaRuleNode rn14 = new JerboaRuleNode("n14", 10, JerboaOrbit.orbit(3), 3);
        JerboaRuleNode rn15 = new JerboaRuleNode("n15", 11, JerboaOrbit.orbit(3), 3, new EdgeFlipExprRn15pos());
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        right.add(rn12);
        right.add(rn13);
        right.add(rn14);
        right.add(rn15);
        rn1.setAlpha(0, rn2);
        rn1.setAlpha(1, rn0);
        rn0.setAlpha(0, rn7);
        rn6.setAlpha(0, rn5);
        rn5.setAlpha(1, rn4);
        rn4.setAlpha(0, rn3);
        rn13.setAlpha(0, rn15);
        rn12.setAlpha(0, rn14);
        rn12.setAlpha(2, rn13);
        rn14.setAlpha(2, rn15);
        rn7.setAlpha(1, rn13);
        rn6.setAlpha(1, rn12);
        rn14.setAlpha(1, rn3);
        rn15.setAlpha(1, rn2);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 1;
        case 2: return 2;
        case 3: return 3;
        case 4: return 4;
        case 5: return 5;
        case 6: return 6;
        case 7: return 7;
        case 8: return -1;
        case 9: return -1;
        case 10: return -1;
        case 11: return -1;
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
        case 8: return -1;
        case 9: return -1;
        case 10: return -1;
        case 11: return -1;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n8) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n8);
        return applyRule(gmap, ____jme_hooks);
	}

    private class EdgeFlipExprRn13pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return curleftPattern.getNode(7).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0);
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

    private class EdgeFlipExprRn15pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return curleftPattern.getNode(2).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0);
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

    private JerboaDart n1() {
        return curleftPattern.getNode(1);
    }

    private JerboaDart n2() {
        return curleftPattern.getNode(2);
    }

    private JerboaDart n3() {
        return curleftPattern.getNode(3);
    }

    private JerboaDart n4() {
        return curleftPattern.getNode(4);
    }

    private JerboaDart n5() {
        return curleftPattern.getNode(5);
    }

    private JerboaDart n6() {
        return curleftPattern.getNode(6);
    }

    private JerboaDart n7() {
        return curleftPattern.getNode(7);
    }

    private JerboaDart n8() {
        return curleftPattern.getNode(8);
    }

    private JerboaDart n9() {
        return curleftPattern.getNode(9);
    }

    private JerboaDart n10() {
        return curleftPattern.getNode(10);
    }

    private JerboaDart n11() {
        return curleftPattern.getNode(11);
    }

} // end rule Class