package fr.ensma.lias.jerboa;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.JerboaRebuilt;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;



/**
 * 
 */



public class ExtrudeFaceEdge extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public ExtrudeFaceEdge(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "ExtrudeFaceEdge", "");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(2, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0), 3, new ExtrudeFaceEdgeExprRn0vertexTracker(), new ExtrudeFaceEdgeExprRn0halfFaceTracker());
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(0), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(-1), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(-1), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(0), 3, new ExtrudeFaceEdgeExprRn4pos(), new ExtrudeFaceEdgeExprRn4vertexTracker(), new ExtrudeFaceEdgeExprRn4halfFaceTracker());
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        rn0.setAlpha(2, rn1);
        rn1.setAlpha(1, rn2);
        rn2.setAlpha(0, rn3);
        rn4.setAlpha(1, rn3);
        rn2.setAlpha(2, rn2);
        rn3.setAlpha(2, rn3);
        rn4.setAlpha(2, rn4);
        rn2.setAlpha(3, rn2);
        rn1.setAlpha(3, rn1);
        rn3.setAlpha(3, rn3);
        rn4.setAlpha(3, rn4);
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
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ExtrudeFaceEdgeExprRn0vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Modify n0. Label: ");
System.out.println(label.toString());
return label;
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "vertexTracker";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getVertexTracker().getID();
        }
    }

    private class ExtrudeFaceEdgeExprRn0halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face unchanged n0. Label: ");
System.out.println(hfLabel.toString());
return hfLabel;
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "halfFaceTracker";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getHalfFaceTracker().getID();
        }
    }

    private class ExtrudeFaceEdgeExprRn4pos implements JerboaRuleExpression {

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
            return ((JerboaRebuilt)modeler).getPos().getID();
        }
    }

    private class ExtrudeFaceEdgeExprRn4vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Create n4. Label: ");
System.out.println(label.toString());
return label;
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "vertexTracker";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getVertexTracker().getID();
        }
    }

    private class ExtrudeFaceEdgeExprRn4halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face create. Label: ");
System.out.println(hfLabel.toString());
return hfLabel;
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "halfFaceTracker";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getHalfFaceTracker().getID();
        }
    }

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

} // end rule Class