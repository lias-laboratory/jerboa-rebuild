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



public class InsertEdge extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public InsertEdge(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "InsertEdge", "");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(1,2), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(1,2), 3);
        left.add(ln0);
        left.add(ln1);
        hooks.add(ln0);
        hooks.add(ln1);
        ln0.setAlpha(3, ln0);
        ln1.setAlpha(3, ln1);
        ln0.setAlpha(0, ln0);
        ln1.setAlpha(0, ln1);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(-1,-1), 3, new InsertEdgeExprRn0vertexTracker(), new InsertEdgeExprRn0halfFaceTracker());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(-1,2), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(-1,2), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 3, JerboaOrbit.orbit(-1,-1), 3, new InsertEdgeExprRn1vertexTracker());
        right.add(rn0);
        right.add(rn2);
        right.add(rn3);
        right.add(rn1);
        rn0.setAlpha(2, rn0);
        rn0.setAlpha(3, rn0);
        rn0.setAlpha(1, rn2);
        rn2.setAlpha(0, rn3);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
        rn3.setAlpha(1, rn1);
        rn1.setAlpha(2, rn1);
        rn1.setAlpha(3, rn1);
        rn0.setAlpha(0, rn0);
        rn1.setAlpha(0, rn1);
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
        case 3: return 1;
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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, JerboaDart n1) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        ____jme_hooks.addCol(n1);
        return applyRule(gmap, ____jme_hooks);
	}

    private class InsertEdgeExprRn0vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Modify n0 from Label: ");
System.out.print(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1));
System.out.print(" to Label: ");
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

    private class InsertEdgeExprRn0halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face Split n0 from Label: ");
System.out.print(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(2));
System.out.print(" to Label: ");
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

    private class InsertEdgeExprRn1vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Modify n1 from Label: ");
System.out.print(curleftPattern.getNode(1).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1));
System.out.print(" to Label: ");
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

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

    private JerboaDart n1() {
        return curleftPattern.getNode(1);
    }

} // end rule Class