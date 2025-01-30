package fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples;


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



public class TriangulateSquareFaceFull extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public TriangulateSquareFaceFull(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "TriangulateSquareFaceFull", "ExplicitExamples");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        left.add(ln4);
        left.add(ln5);
        left.add(ln6);
        left.add(ln7);
        hooks.add(ln0);
        ln1.setAlpha(0, ln2);
        ln3.setAlpha(0, ln4);
        ln5.setAlpha(0, ln6);
        ln7.setAlpha(0, ln0);
        ln0.setAlpha(1, ln1);
        ln2.setAlpha(1, ln3);
        ln4.setAlpha(1, ln5);
        ln6.setAlpha(1, ln7);
        ln7.setAlpha(3, ln7);
        ln6.setAlpha(3, ln6);
        ln5.setAlpha(3, ln5);
        ln4.setAlpha(3, ln4);
        ln3.setAlpha(3, ln3);
        ln2.setAlpha(3, ln2);
        ln1.setAlpha(3, ln1);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn8 = new JerboaRuleNode("n8", 8, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn9 = new JerboaRuleNode("n9", 9, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn10 = new JerboaRuleNode("n10", 10, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn11 = new JerboaRuleNode("n11", 11, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn12 = new JerboaRuleNode("n12", 12, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn13 = new JerboaRuleNode("n13", 13, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn14 = new JerboaRuleNode("n14", 14, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn15 = new JerboaRuleNode("n15", 15, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn16 = new JerboaRuleNode("n16", 16, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn17 = new JerboaRuleNode("n17", 17, JerboaOrbit.orbit(), 3, new TriangulateSquareFaceFullExprRn17pos());
        JerboaRuleNode rn18 = new JerboaRuleNode("n18", 18, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn19 = new JerboaRuleNode("n19", 19, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn20 = new JerboaRuleNode("n20", 20, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn21 = new JerboaRuleNode("n21", 21, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn22 = new JerboaRuleNode("n22", 22, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn23 = new JerboaRuleNode("n23", 23, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        right.add(rn8);
        right.add(rn9);
        right.add(rn10);
        right.add(rn11);
        right.add(rn12);
        right.add(rn13);
        right.add(rn14);
        right.add(rn15);
        right.add(rn16);
        right.add(rn17);
        right.add(rn18);
        right.add(rn19);
        right.add(rn20);
        right.add(rn21);
        right.add(rn22);
        right.add(rn23);
        rn1.setAlpha(0, rn2);
        rn3.setAlpha(0, rn4);
        rn5.setAlpha(0, rn6);
        rn7.setAlpha(0, rn0);
        rn0.setAlpha(1, rn8);
        rn1.setAlpha(1, rn9);
        rn2.setAlpha(1, rn10);
        rn3.setAlpha(1, rn11);
        rn4.setAlpha(1, rn12);
        rn5.setAlpha(1, rn13);
        rn6.setAlpha(1, rn14);
        rn7.setAlpha(1, rn15);
        rn8.setAlpha(0, rn16);
        rn9.setAlpha(0, rn17);
        rn10.setAlpha(0, rn18);
        rn11.setAlpha(0, rn19);
        rn12.setAlpha(0, rn20);
        rn13.setAlpha(0, rn21);
        rn14.setAlpha(0, rn22);
        rn15.setAlpha(0, rn23);
        rn16.setAlpha(1, rn23);
        rn17.setAlpha(1, rn18);
        rn19.setAlpha(1, rn20);
        rn21.setAlpha(1, rn22);
        rn14.setAlpha(2, rn15);
        rn13.setAlpha(2, rn12);
        rn21.setAlpha(2, rn20);
        rn11.setAlpha(2, rn10);
        rn19.setAlpha(2, rn18);
        rn9.setAlpha(2, rn8);
        rn17.setAlpha(2, rn16);
        rn22.setAlpha(2, rn23);
        rn9.setAlpha(3, rn9);
        rn8.setAlpha(3, rn8);
        rn16.setAlpha(3, rn16);
        rn17.setAlpha(3, rn17);
        rn18.setAlpha(3, rn18);
        rn19.setAlpha(3, rn19);
        rn20.setAlpha(3, rn20);
        rn21.setAlpha(3, rn21);
        rn22.setAlpha(3, rn22);
        rn23.setAlpha(3, rn23);
        rn11.setAlpha(3, rn11);
        rn10.setAlpha(3, rn10);
        rn12.setAlpha(3, rn12);
        rn13.setAlpha(3, rn13);
        rn14.setAlpha(3, rn14);
        rn15.setAlpha(3, rn15);
        rn7.setAlpha(3, rn7);
        rn6.setAlpha(3, rn6);
        rn5.setAlpha(3, rn5);
        rn4.setAlpha(3, rn4);
        rn3.setAlpha(3, rn3);
        rn2.setAlpha(3, rn2);
        rn1.setAlpha(3, rn1);
        rn0.setAlpha(3, rn0);
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
        case 12: return -1;
        case 13: return -1;
        case 14: return -1;
        case 15: return -1;
        case 16: return -1;
        case 17: return -1;
        case 18: return -1;
        case 19: return -1;
        case 20: return -1;
        case 21: return -1;
        case 22: return -1;
        case 23: return -1;
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
        case 10: return 0;
        case 11: return 0;
        case 12: return 0;
        case 13: return 0;
        case 14: return 0;
        case 15: return 0;
        case 16: return 0;
        case 17: return 0;
        case 18: return 0;
        case 19: return 0;
        case 20: return 0;
        case 21: return 0;
        case 22: return 0;
        case 23: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class TriangulateSquareFaceFullExprRn17pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
Vec3 res = new Vec3();
res.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(curleftPattern.getNode(0),JerboaOrbit.orbit(0,1),0));
return res;
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

} // end rule Class