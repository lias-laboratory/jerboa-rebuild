package fr.ensma.lias.jerboa.core.rule.rules.CarvingFake;
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



public class Retrecissement2DFake extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected Object fakeLeft; 

	// END PARAMETERS 



    public Retrecissement2DFake(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "Retrecissement2DFake", "CarvingFake");

        // -------- LEFT GRAPH
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln8 = new JerboaRuleNode("n8", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln9 = new JerboaRuleNode("n9", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 5, JerboaOrbit.orbit(), 3);
        left.add(ln6);
        left.add(ln0);
        left.add(ln8);
        left.add(ln9);
        left.add(ln1);
        left.add(ln7);
        hooks.add(ln6);
        ln6.setAlpha(0, ln0);
        ln0.setAlpha(1, ln8);
        ln8.setAlpha(0, ln9);
        ln9.setAlpha(1, ln1);
        ln1.setAlpha(0, ln7);
        ln6.setAlpha(2, ln6);
        ln0.setAlpha(2, ln0);
        ln8.setAlpha(2, ln8);
        ln9.setAlpha(2, ln9);
        ln1.setAlpha(2, ln1);
        ln7.setAlpha(2, ln7);
        ln6.setAlpha(3, ln6);
        ln0.setAlpha(3, ln0);
        ln8.setAlpha(3, ln8);
        ln9.setAlpha(3, ln9);
        ln1.setAlpha(3, ln1);
        ln7.setAlpha(3, ln7);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn10 = new JerboaRuleNode("n10", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn11 = new JerboaRuleNode("n11", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn12 = new JerboaRuleNode("n12", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn13 = new JerboaRuleNode("n13", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 5, JerboaOrbit.orbit(), 3);
        right.add(rn6);
        right.add(rn10);
        right.add(rn11);
        right.add(rn12);
        right.add(rn13);
        right.add(rn7);
        rn6.setAlpha(0, rn10);
        rn10.setAlpha(1, rn11);
        rn11.setAlpha(0, rn12);
        rn12.setAlpha(1, rn13);
        rn13.setAlpha(0, rn7);
        rn6.setAlpha(2, rn6);
        rn10.setAlpha(2, rn10);
        rn11.setAlpha(2, rn11);
        rn12.setAlpha(2, rn12);
        rn13.setAlpha(2, rn13);
        rn7.setAlpha(2, rn7);
        rn6.setAlpha(3, rn6);
        rn10.setAlpha(3, rn10);
        rn11.setAlpha(3, rn11);
        rn12.setAlpha(3, rn12);
        rn13.setAlpha(3, rn13);
        rn7.setAlpha(3, rn7);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        fakeLeft = null;    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        case 4: return -1;
        case 5: return 5;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        case 2: return 0;
        case 3: return 0;
        case 4: return 0;
        case 5: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n6, Object fakeLeft) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n6);
        setFakeLeft(fakeLeft);
        return applyRule(gmap, ____jme_hooks);
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
    private JerboaDart n6() {
        return curleftPattern.getNode(0);
    }

    private JerboaDart n0() {
        return curleftPattern.getNode(1);
    }

    private JerboaDart n8() {
        return curleftPattern.getNode(2);
    }

    private JerboaDart n9() {
        return curleftPattern.getNode(3);
    }

    private JerboaDart n1() {
        return curleftPattern.getNode(4);
    }

    private JerboaDart n7() {
        return curleftPattern.getNode(5);
    }

	public Object getFakeLeft(){
		return fakeLeft;
	}
	public void setFakeLeft(Object _fakeLeft){
		this.fakeLeft = _fakeLeft;
	}
} // end rule Class