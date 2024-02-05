package fr.ensma.lias.jerboa.experiments;

import up.jerboa.core.JerboaOrbit;

/** ScriptIfElseBaseStrategy */
public class ScriptIfElseBaseStrategy extends ScriptReevaluationStrategy {

  @Override
  public boolean test(JerboaOrbit match, JerboaOrbit orbit) {
    return match.equals(orbit);
  }
}
