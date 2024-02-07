package fr.ensma.lias.jerboa.experiments;

import up.jerboa.core.JerboaOrbit;

public abstract class ScriptReevaluationStrategy {

  public abstract boolean test(JerboaOrbit match, JerboaOrbit orbit);
}
