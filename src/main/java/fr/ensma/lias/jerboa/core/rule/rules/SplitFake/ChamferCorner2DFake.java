package fr.ensma.lias.jerboa.core.rule.rules.SplitFake;


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



public class ChamferCorner2DFake extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected float weight; 
	protected Object fakeLeft; 

	// END PARAMETERS 



    public ChamferCorner2DFake(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "ChamferCorner2DFake", "SplitFake");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(1), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(2, ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(-1), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(0), 3, new ChamferCorner2DFakeExprRn1pos());
        right.add(rn0);
        right.add(rn1);
        rn0.setAlpha(1, rn1);
        rn0.setAlpha(2, rn0);
        rn0.setAlpha(3, rn0);
        rn1.setAlpha(2, rn1);
        rn1.setAlpha(3, rn1);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        weight = 0.3f;    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, float weight, Object fakeLeft) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        setWeight(weight);
        setFakeLeft(fakeLeft);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ChamferCorner2DFakeExprRn1pos implements JerboaRuleExpression {

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
            return ((ModelerGenerated)modeler).getPos().getID();
        }
    }

    @Override
    public boolean hasPrecondition() { return true; }
    public boolean evalPrecondition(final JerboaGMap gmap, final List<JerboaRowPattern> leftPattern) throws JerboaException {

            // BEGIN PRECONDITION CODE
fakeLeft = new ArrayList<JerboaRowPattern>(leftPattern);
return false;
            // END PRECONDITION CODE
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
	public Object getFakeLeft(){
		return fakeLeft;
	}
	public void setFakeLeft(Object _fakeLeft){
		this.fakeLeft = _fakeLeft;
	}
} // end rule Class