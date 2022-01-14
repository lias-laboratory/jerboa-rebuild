package fr.ensma.lias.jerboa.core.rule.expression;

import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleExpression;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

public class UnchangedExpression implements JerboaRuleExpression {

    private JerboaEmbeddingInfo info;

    public UnchangedExpression(JerboaEmbeddingInfo info) {
        this.info = info;
    }

    @Override
    public Object compute(JerboaGMap arg0, JerboaRuleOperation arg1, JerboaRowPattern arg2,
            JerboaRuleNode arg3) throws JerboaException {
        var unchangedLabel = arg2.getNode(arg3.getID()).getEmbedding(this.getEmbedding());
        System.out.println(getOrbitType() + " unchanged label: " + unchangedLabel);
        return unchangedLabel;
    }

    @Override
    public int getEmbedding() {
        return info.getID();
    }

    @Override
    public String getName() {
        return info.getName();
    }

    private String getOrbitType() {
        return info.getName().substring(0, info.getName().length() - 7);
    }
}
