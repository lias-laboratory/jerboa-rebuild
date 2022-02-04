package fr.ensma.lias.jerboa.core.rule.expression;

import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleExpression;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

public class SplitExpression implements JerboaRuleExpression {

    private JerboaEmbeddingInfo info;

    public SplitExpression(JerboaEmbeddingInfo info) {
        this.info = info;
    }

    @Override
    public Object compute(JerboaGMap arg0, JerboaRuleOperation arg1, JerboaRowPattern arg2,
            JerboaRuleNode arg3) throws JerboaException {
        OrbitLabel label = new OrbitLabel();
        OrbitLabel oldLabel = arg2.get(arg3.getID()).<OrbitLabel>ebd(getEmbedding());

        System.out.println(getOrbitType() + " split label from " + oldLabel.toString() + " to "
                + label.toString());
        return label;
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
