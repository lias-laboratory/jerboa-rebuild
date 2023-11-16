package fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples;
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



public class CollapseTriangularFace extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public CollapseTriangularFace(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "CollapseTriangularFace", "ExplicitExamples");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln8 = new JerboaRuleNode("n8", 8, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln9 = new JerboaRuleNode("n9", 9, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln10 = new JerboaRuleNode("n10", 10, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln11 = new JerboaRuleNode("n11", 11, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln12 = new JerboaRuleNode("n12", 12, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln13 = new JerboaRuleNode("n13", 13, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln14 = new JerboaRuleNode("n14", 14, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln15 = new JerboaRuleNode("n15", 15, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln16 = new JerboaRuleNode("n16", 16, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln17 = new JerboaRuleNode("n17", 17, JerboaOrbit.orbit(), 3);
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
        left.add(ln12);
        left.add(ln13);
        left.add(ln14);
        left.add(ln15);
        left.add(ln16);
        left.add(ln17);
        hooks.add(ln0);
        ln1.setAlpha(0, ln2);
        ln2.setAlpha(1, ln3);
        ln3.setAlpha(0, ln4);
        ln4.setAlpha(1, ln5);
        ln5.setAlpha(0, ln0);
        ln0.setAlpha(1, ln1);
        ln1.setAlpha(2, ln7);
        ln2.setAlpha(2, ln8);
        ln3.setAlpha(2, ln9);
        ln4.setAlpha(2, ln10);
        ln5.setAlpha(2, ln11);
        ln0.setAlpha(2, ln6);
        ln7.setAlpha(0, ln8);
        ln9.setAlpha(0, ln10);
        ln6.setAlpha(1, ln12);
        ln12.setAlpha(2, ln13);
        ln13.setAlpha(1, ln7);
        ln8.setAlpha(1, ln14);
        ln9.setAlpha(1, ln15);
        ln15.setAlpha(2, ln14);
        ln10.setAlpha(1, ln16);
        ln11.setAlpha(1, ln17);
        ln17.setAlpha(2, ln16);
        ln11.setAlpha(0, ln6);
        ln0.setAlpha(3, ln0);
        ln1.setAlpha(3, ln1);
        ln2.setAlpha(3, ln2);
        ln3.setAlpha(3, ln3);
        ln4.setAlpha(3, ln4);
        ln5.setAlpha(3, ln5);
        ln11.setAlpha(3, ln11);
        ln6.setAlpha(3, ln6);
        ln12.setAlpha(3, ln12);
        ln13.setAlpha(3, ln13);
        ln7.setAlpha(3, ln7);
        ln8.setAlpha(3, ln8);
        ln9.setAlpha(3, ln9);
        ln10.setAlpha(3, ln10);
        ln16.setAlpha(3, ln16);
        ln17.setAlpha(3, ln17);
        ln14.setAlpha(3, ln14);
        ln15.setAlpha(3, ln15);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn12 = new JerboaRuleNode("n12", 0, JerboaOrbit.orbit(), 3, new CollapseTriangularFaceExprRn12pos());
        JerboaRuleNode rn13 = new JerboaRuleNode("n13", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn14 = new JerboaRuleNode("n14", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn15 = new JerboaRuleNode("n15", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn16 = new JerboaRuleNode("n16", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn17 = new JerboaRuleNode("n17", 5, JerboaOrbit.orbit(), 3);
        right.add(rn12);
        right.add(rn13);
        right.add(rn14);
        right.add(rn15);
        right.add(rn16);
        right.add(rn17);
        rn12.setAlpha(2, rn13);
        rn13.setAlpha(1, rn14);
        rn14.setAlpha(2, rn15);
        rn15.setAlpha(1, rn16);
        rn16.setAlpha(2, rn17);
        rn17.setAlpha(1, rn12);
        rn12.setAlpha(3, rn12);
        rn13.setAlpha(3, rn13);
        rn14.setAlpha(3, rn14);
        rn15.setAlpha(3, rn15);
        rn16.setAlpha(3, rn16);
        rn17.setAlpha(3, rn17);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 12;
        case 1: return 13;
        case 2: return 14;
        case 3: return 15;
        case 4: return 16;
        case 5: return 17;
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
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class CollapseTriangularFaceExprRn12pos implements JerboaRuleExpression {

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

    private JerboaDart n12() {
        return curleftPattern.getNode(12);
    }

    private JerboaDart n13() {
        return curleftPattern.getNode(13);
    }

    private JerboaDart n14() {
        return curleftPattern.getNode(14);
    }

    private JerboaDart n15() {
        return curleftPattern.getNode(15);
    }

    private JerboaDart n16() {
        return curleftPattern.getNode(16);
    }

    private JerboaDart n17() {
        return curleftPattern.getNode(17);
    }

} // end rule Class