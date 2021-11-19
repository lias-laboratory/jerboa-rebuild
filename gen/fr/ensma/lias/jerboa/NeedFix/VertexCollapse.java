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



public class VertexCollapse extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public VertexCollapse(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "VertexCollapse", "NeedFix");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(-1,-1), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(-1,-1), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        hooks.add(ln0);
        ln0.setAlpha(0, ln1);
        ln2.setAlpha(1, ln0);
        ln1.setAlpha(1, ln3);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 0, JerboaOrbit.orbit(-1,-1), 3, new VertexCollapseExprRn2pos(), new VertexCollapseExprRn2vertexTracker(), new VertexCollapseExprRn2halfFaceTracker());
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 1, JerboaOrbit.orbit(-1,-1), 3);
        right.add(rn2);
        right.add(rn3);
        rn2.setAlpha(1, rn3);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 2;
        case 1: return 3;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
        }
        return -1;
    }

    @Override
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaInputHooks _hooks) throws JerboaException {
        preprocess(gmap, _hooks);
        JerboaRuleResult res = super.applyRule(gmap, _hooks);
        return res;
    }
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class VertexCollapseExprRn2pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return Vec3.mid(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),curleftPattern.getNode(1).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0));
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

    private class VertexCollapseExprRn2vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
if((curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1) == curleftPattern.getNode(1).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1))) {
   System.out.print("Vertex Modify n2. Label: ");
   System.out.println(curleftPattern.getNode(2).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1));
}
else {
   System.out.print("Vertex Merge n2, n3. Label: ");
   System.out.println(curleftPattern.getNode(2).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1));
}
System.out.print("Tester en postprocess si ");
System.out.print(curleftPattern.getNode(2));
System.out.print(" et ");
System.out.print(curleftPattern.getNode(2).alpha(1).alpha(2).alpha(1));
System.out.println(" sont le même sommet ou non");
return new OrbitLabel();
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

    private class VertexCollapseExprRn2halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face modify. Label: ");
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

    @Override
    public boolean hasPreprocess() { return true; }
    @Override
    public boolean preprocess(JerboaGMap gmap, JerboaInputHooks hooks) throws JerboaException {
	// BEGIN PREPROCESS CODE
System.out.println("run pre-process");
return true;

	// END PREPROCESS CODE
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

} // end rule Class