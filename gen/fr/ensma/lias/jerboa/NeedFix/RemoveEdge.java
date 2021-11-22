package fr.ensma.lias.jerboa.NeedFix;


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



public class RemoveEdge extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public RemoveEdge(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "RemoveEdge", "NeedFix");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0,2), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(-1,-1), 3);
        left.add(ln0);
        left.add(ln1);
        hooks.add(ln0);
        ln0.setAlpha(1, ln1);
        ln0.setAlpha(3, ln0);
        ln1.setAlpha(3, ln1);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(-1,1), 3, new RemoveEdgeExprRn1vertexTracker(), new RemoveEdgeExprRn1halfFaceTracker());
        right.add(rn1);
        rn1.setAlpha(3, rn1);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class RemoveEdgeExprRn1vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Modify n1. Label: ");
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

    private class RemoveEdgeExprRn1halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
if((curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(2) == curleftPattern.getNode(0).alpha(2).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(2))) {
   System.out.print("Half-face Merge. Label: ");
   System.out.println(hfLabel.toString());
}
else {
   System.out.print("Half-face Modify. Label: ");
   System.out.println(hfLabel.toString());
}
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

    private JerboaDart n1() {
        return curleftPattern.getNode(1);
    }

} // end rule Class