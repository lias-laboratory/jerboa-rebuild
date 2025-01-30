package fr.ensma.lias.jerboa.core.rule.rules.CreationFake;


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



public class CreateSquareFaceFake extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected Integer size; 
	protected Object fakeLeft; 

	// END PARAMETERS 



    public CreateSquareFaceFake(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "CreateSquareFaceFake", "CreationFake");

        // -------- LEFT GRAPH

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3, new CreateSquareFaceFakeExprRn1pos());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3, new CreateSquareFaceFakeExprRn3pos());
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3, new CreateSquareFaceFakeExprRn5pos());
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3, new CreateSquareFaceFakeExprRn7pos());
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        rn0.setAlpha(1, rn1);
        rn1.setAlpha(0, rn2);
        rn2.setAlpha(1, rn3);
        rn3.setAlpha(0, rn4);
        rn4.setAlpha(1, rn5);
        rn5.setAlpha(0, rn6);
        rn6.setAlpha(1, rn7);
        rn7.setAlpha(0, rn0);
        rn1.setAlpha(2, rn1);
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
        rn0.setAlpha(2, rn0);
        rn0.setAlpha(3, rn0);
        rn1.setAlpha(3, rn1);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        size = 1;    }

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

    public JerboaRuleResult applyRule(JerboaGMap gmap, Integer size, Object fakeLeft) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        setSize(size);
        setFakeLeft(fakeLeft);
        return applyRule(gmap, ____jme_hooks);
	}

    private class CreateSquareFaceFakeExprRn1pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(0,0,0);
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

    private class CreateSquareFaceFakeExprRn3pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(0,0,1);
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

    private class CreateSquareFaceFakeExprRn5pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(1,0,1);
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

    private class CreateSquareFaceFakeExprRn7pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(1,0,0);
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

	public Integer getSize(){
		return size;
	}
	public void setSize(Integer _size){
		this.size = _size;
	}
	public Object getFakeLeft(){
		return fakeLeft;
	}
	public void setFakeLeft(Object _fakeLeft){
		this.fakeLeft = _fakeLeft;
	}
} // end rule Class