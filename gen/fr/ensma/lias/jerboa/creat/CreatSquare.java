package fr.ensma.lias.jerboa.creat;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.JerboaRebuilt;
import fr.ensma.lias.jerboa.embeddings.Points;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;



/**
 * 
 */



public class CreatSquare extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public CreatSquare(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "CreatSquare", "creat");

        // -------- LEFT GRAPH

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3, new CreatSquareExprRn0point(), new CreatSquareExprRn0vertexTracker());
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3, new CreatSquareExprRn1point(), new CreatSquareExprRn1vertexTracker());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3, new CreatSquareExprRn3point(), new CreatSquareExprRn3vertexTracker());
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3, new CreatSquareExprRn5point(), new CreatSquareExprRn5vertexTracker());
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
        rn0.setAlpha(0, rn1);
        rn1.setAlpha(1, rn2);
        rn2.setAlpha(0, rn3);
        rn3.setAlpha(1, rn4);
        rn4.setAlpha(0, rn5);
        rn5.setAlpha(1, rn6);
        rn6.setAlpha(0, rn7);
        rn7.setAlpha(1, rn0);
        rn0.setAlpha(2, rn0);
        rn0.setAlpha(3, rn0);
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

    private class CreatSquareExprRn0point implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Points(0,0,0);
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "point";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getPoint().getID();
        }
    }

    private class CreatSquareExprRn0vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
// BEGIN LANGUAGE INPUT CODE

    System.out.println("Create orbit label " + label.getLabel());

// END LANGUAGE INPUT CODE
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

    private class CreatSquareExprRn1point implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Points(1,0,0);
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "point";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getPoint().getID();
        }
    }

    private class CreatSquareExprRn1vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
// BEGIN LANGUAGE INPUT CODE

    System.out.println("Create orbit label " + label.getLabel());

// END LANGUAGE INPUT CODE
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

    private class CreatSquareExprRn3point implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Points(1,0,1);
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "point";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getPoint().getID();
        }
    }

    private class CreatSquareExprRn3vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
// BEGIN LANGUAGE INPUT CODE

    System.out.println("Create orbit label " + label.getLabel());

// END LANGUAGE INPUT CODE
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

    private class CreatSquareExprRn5point implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Points(0,0,1);
// ======== END CODE TRANSLATION FOR EXPRESSION COMPUTATION
        }

        @Override
        public String getName() {
            return "point";
        }

        @Override
        public int getEmbedding() {
            return ((JerboaRebuilt)modeler).getPoint().getID();
        }
    }

    private class CreatSquareExprRn5vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
// BEGIN LANGUAGE INPUT CODE

    System.out.println("Create orbit label " + label.getLabel());

// END LANGUAGE INPUT CODE
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

} // end rule Class