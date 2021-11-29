package fr.ensma.lias.jerboa.embeddings;

import up.jerboa.core.JerboaRuleOperation;

public class OrbitLabel {

	private Integer label;
	private static int counter = 0;
	private JerboaRuleOperation rule;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("rule ").append(rule.toString()).append(label.toString());
		return sb.toString();
	}

	public OrbitLabel(JerboaRuleOperation rule) {
		this.label = counter;
		counter += 1;
		this.rule = rule;
	}

	public Integer getLabel() {
		return this.label;
	}

}
