package fr.ensma.lias.jerboa.core.rule.expression;

import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleExpression;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.exception.JerboaException;

public class MergeExpression implements JerboaRuleExpression {

    private JerboaEmbeddingInfo info;

    public MergeExpression(JerboaEmbeddingInfo info) {
        this.info = info;
    }

    @Override
    public Object compute(JerboaGMap arg0, JerboaRuleOperation arg1, JerboaRowPattern arg2,
            JerboaRuleNode arg3) throws JerboaException {
        OrbitLabel label = new OrbitLabel();
        StringBuilder sb = new StringBuilder();
        sb.append(info.getName().substring(0, info.getName().length() - 7)).append(" ")
                .append("Merge").append(" Label: ").append(label.toString());
        System.out.println(sb.toString());
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
