package fr.ensma.lias.jerboa.core.rule.rules;
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



public class ExtrudeFaceToPyramid extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected float height; 

	// END PARAMETERS 



    public ExtrudeFaceToPyramid(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "ExtrudeFaceToPyramid", "");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0,1), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(2, ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(0,-1), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(-1,2), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(1,2), 3, new ExtrudeFaceToPyramidExprRn3pos());
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 3, JerboaOrbit.orbit(0,1), 3);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn0);
        rn1.setAlpha(1, rn2);
        rn1.setAlpha(3, rn1);
        rn2.setAlpha(0, rn3);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
        rn0.setAlpha(2, rn1);
        rn0.setAlpha(3, rn0);
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
        case 3: return 0;
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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, float height) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        setHeight(height);
        return applyRule(gmap, ____jme_hooks);
	}

    private class ExtrudeFaceToPyramidExprRn3pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
Vec3 res = new Vec3();
res.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(curleftPattern.getNode(0),JerboaOrbit.orbit(0,1),0));
return new Vec3(res).addn(0.0f,height,0.0f);
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

	public float getHeight(){
		return height;
	}
	public void setHeight(float _height){
		this.height = _height;
	}
} // end rule Class