package fr.ensma.lias.jerboa.core.rule.rules.Subdivision;
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



public class SubdivQuad extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public SubdivQuad(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "SubdivQuad", "Subdivision");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0,1,2), 3);
        left.add(ln0);
        hooks.add(ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(-1,1,2), 3, new SubdivQuadExprRn0pos());
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(-1,-1,2), 3, new SubdivQuadExprRn1pos());
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 2, JerboaOrbit.orbit(2,1,-1), 3, new SubdivQuadExprRn3pos());
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 3, JerboaOrbit.orbit(2,-1,-1), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn3);
        right.add(rn2);
        rn0.setAlpha(0, rn1);
        rn2.setAlpha(0, rn3);
        rn2.setAlpha(1, rn1);
        rn0.setAlpha(3, rn0);
        rn1.setAlpha(3, rn1);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
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

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    private class SubdivQuadExprRn0pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
java.util.List<Vec3> faceList = new ArrayList<Vec3>();
for(JerboaDart face : gmap.collect(curleftPattern.getNode(0),JerboaOrbit.orbit(1,2),JerboaOrbit.orbit())){
   Vec3 a = new Vec3();
   a.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(face,JerboaOrbit.orbit(0,1),0));
   faceList.add(a);
}

Vec3 F = new Vec3();
F.barycenter(faceList);
java.util.List<JerboaDart> edgeList = gmap.collect(curleftPattern.getNode(0),JerboaOrbit.orbit(1,2),JerboaOrbit.orbit(2));
int k = edgeList.size();
Vec3 R = new Vec3();
for(JerboaDart edge : edgeList){
   R = R.addn(edge.alpha(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(edge.<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0)).div(2.0));
}

R = R.div(k);
return R.mul(2).addn(F).addn(curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).mul((k - 3))).div(k);
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

    private class SubdivQuadExprRn1pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
Vec3 FP0 = new Vec3();
FP0.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(curleftPattern.getNode(0),JerboaOrbit.orbit(0,1),0));
Vec3 FP1 = new Vec3();
FP1.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(curleftPattern.getNode(0).alpha(2),JerboaOrbit.orbit(0,1),0));
return curleftPattern.getNode(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0).addn(curleftPattern.getNode(0).alpha(0).<fr.ensma.lias.jerboa.embeddings.Vec3>ebd(0)).mul(0.5).addn(FP0).addn(FP1).div(3.0);
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

    private class SubdivQuadExprRn3pos implements JerboaRuleExpression {

        @Override
        public Object compute(JerboaGMap gmap, JerboaRuleOperation rule,JerboaRowPattern leftPattern, JerboaRuleNode rulenode) throws JerboaException {
            curleftPattern = leftPattern;
// ======== BEGIN CODE TRANSLATION FOR EXPRESSION COMPUTATION
            // ======== SEPARATION CODE TRANSLATION FOR EXPRESSION COMPUTATION
Vec3 res = new Vec3();
res.barycenter(gmap.<fr.ensma.lias.jerboa.embeddings.Vec3>collect(curleftPattern.getNode(0),JerboaOrbit.orbit(0,1),0));
return res;
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

} // end rule Class