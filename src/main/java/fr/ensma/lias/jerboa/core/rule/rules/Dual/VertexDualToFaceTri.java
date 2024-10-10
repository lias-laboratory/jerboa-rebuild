package fr.ensma.lias.jerboa.core.rule.rules.Dual;
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



public class VertexDualToFaceTri extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected Vec3 center; 
	protected float vOffset; 

	// END PARAMETERS 



    public VertexDualToFaceTri(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "VertexDualToFaceTri", "Dual");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(1,2,3), 3);
        left.add(ln0);
        hooks.add(ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(-1,-1,-1), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(-1,-1,-1), 3, new VertexDualToFaceTriExprRn2pos());
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(-1,-1,-1), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 3, JerboaOrbit.orbit(-1,-1,-1), 3, new VertexDualToFaceTriExprRn4pos());
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 4, JerboaOrbit.orbit(-1,-1,-1), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 5, JerboaOrbit.orbit(-1,-1,-1), 3, new VertexDualToFaceTriExprRn6pos());
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 6, JerboaOrbit.orbit(1,2,3), 3);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn0);
        rn2.setAlpha(0, rn3);
        rn3.setAlpha(1, rn4);
        rn4.setAlpha(0, rn5);
        rn5.setAlpha(1, rn6);
        rn6.setAlpha(0, rn1);
        rn2.setAlpha(1, rn1);
        rn1.setAlpha(2, rn1);
        rn2.setAlpha(2, rn2);
        rn3.setAlpha(2, rn3);
        rn4.setAlpha(2, rn4);
        rn6.setAlpha(2, rn6);
        rn5.setAlpha(2, rn5);
        rn2.setAlpha(3, rn2);
        rn4.setAlpha(3, rn4);
        rn3.setAlpha(3, rn3);
        rn1.setAlpha(3, rn1);
        rn5.setAlpha(3, rn5);
        rn6.setAlpha(3, rn6);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        center = new Vec3(0,0,0);        vOffset = 0.5f;    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        case 4: return -1;
        case 5: return -1;
        case 6: return 0;
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
        case 6: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, Vec3 center, float vOffset) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        setCenter(center);
        setVOffset(vOffset);
        return applyRule(gmap, ____jme_hooks);
	}

    private class VertexDualToFaceTriExprRn2pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3(center.x(),(center.y() + vOffset),center.z());
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

    private class VertexDualToFaceTriExprRn4pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3((center.x() + vOffset),(center.y() - vOffset),center.z());
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

    private class VertexDualToFaceTriExprRn6pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
return new Vec3((center.x() - vOffset),(center.y() - vOffset),center.z());
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

	public Vec3 getCenter(){
		return center;
	}
	public void setCenter(Vec3 _center){
		this.center = _center;
	}
	public float getVOffset(){
		return vOffset;
	}
	public void setVOffset(float _vOffset){
		this.vOffset = _vOffset;
	}
} // end rule Class