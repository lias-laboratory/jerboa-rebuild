package fr.ensma.lias.jerboa.experiments;

import up.jerboa.core.JerboaOrbit;

public class ScriptIfElseSubSupOrbitsStrictStrategy extends ScriptIfElseSubSupOrbitsStrategy {

  @Override
  public boolean test(JerboaOrbit match, JerboaOrbit orbit) {
    return isSubOrbit(match, orbit) || isSupOrbit(match, orbit);
  }
}
