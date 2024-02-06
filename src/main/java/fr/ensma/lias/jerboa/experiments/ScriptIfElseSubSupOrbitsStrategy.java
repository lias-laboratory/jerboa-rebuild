package fr.ensma.lias.jerboa.experiments;

import up.jerboa.core.JerboaOrbit;

/** ScriptIfElseSubOverOrbitsStrategy */
public class ScriptIfElseSubSupOrbitsStrategy extends ScriptReevaluationStrategy {

  boolean isSubOrbit(JerboaOrbit match, JerboaOrbit orbit) {
    return orbit.contains(match) && !match.contains(orbit);
  }

  boolean isSupOrbit(JerboaOrbit match, JerboaOrbit orbit) {
    return match.contains(orbit) && !orbit.contains(match);
  }

  @Override
  public boolean test(JerboaOrbit match, JerboaOrbit orbit) {
    ScriptIfElseBaseStrategy base = new ScriptIfElseBaseStrategy();
    return base.test(match, orbit) || isSubOrbit(match, orbit) || isSupOrbit(match, orbit);
  }
}
