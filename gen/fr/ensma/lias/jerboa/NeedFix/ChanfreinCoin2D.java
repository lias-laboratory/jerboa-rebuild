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



public class ChanfreinCoin2D extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected float weight; 

	// END PARAMETERS 



    public ChanfreinCoin2D(JerboaRebuilt modeler) throws JerboaException {

        super(modeler, "ChanfreinCoin2D", "NeedFix");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(1,2), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(-1,2), 3, new ChanfreinCoin2DExprRn0halfFaceTracker());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(0,-1), 3, new ChanfreinCoin2DExprRn2vertexTracker(), new ChanfreinCoin2DExprRn2pos());
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(0,1), 3, new ChanfreinCoin2DExprRn3halfFaceTracker());
        right.add(rn0);
        right.add(rn2);
        right.add(rn3);
        rn0.setAlpha(3, rn0);
        rn2.setAlpha(1, rn0);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(2, rn2);
        rn3.setAlpha(3, rn3);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        weight = 1;    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return -1;
        case 2: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        case 2: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, float weight) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        setWeight(weight);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ChanfreinCoin2DExprRn0halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face Modify n0. Label: ");
System.out.print(hfLabel.toString());
System.out.print(" from Label: ");
System.out.println(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(2));
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

    private class ChanfreinCoin2DExprRn2vertexTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel label = new OrbitLabel();
System.out.print("Vertex Split n0. Label: ");
System.out.print(label.toString());
System.out.print(" from Label: ");
System.out.println(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.OrbitLabel>ebd(1));
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

    private class ChanfreinCoin2DExprRn2pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(Vec3.segmentABByWeight(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),curleftPattern.getNode(0).alpha(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0),weight));
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

    private class ChanfreinCoin2DExprRn3halfFaceTracker implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
OrbitLabel hfLabel = new OrbitLabel();
System.out.print("Half-face create n3. Label: ");
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

	public float getWeight(){
		return weight;
	}
	public void setWeight(float _weight){
		this.weight = _weight;
	}
} // end rule Class