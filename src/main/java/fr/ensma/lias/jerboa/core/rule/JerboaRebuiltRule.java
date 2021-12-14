package fr.ensma.lias.jerboa.core.rule;

import java.util.List;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRule extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;

    public JerboaRebuiltRule(JerboaModeler modeler, String name, String category)
            throws JerboaException {
        super(modeler, name, category);
    }

    @Override
    public int attachedNode(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int reverseAssoc(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

}
