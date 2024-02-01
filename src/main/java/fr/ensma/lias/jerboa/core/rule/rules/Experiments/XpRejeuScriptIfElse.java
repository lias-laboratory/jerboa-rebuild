package fr.ensma.lias.jerboa.core.rule.rules.Experiments;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.experiments.MesScriptsRejeu;
import up.jerboa.core.*;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.exception.JerboaException;

/* Raw Imports : */

/* End raw Imports */

/** */
public class XpRejeuScriptIfElse extends JerboaRuleScript {

  private transient JerboaRowPattern curleftPattern;

  // BEGIN PARAMETERS Transformed

  // END PARAMETERS

  public XpRejeuScriptIfElse(ModelerGenerated modeler) throws JerboaException {

    super(modeler, "XpRejeuScriptIfElse", "Experiments");

    // -------- LEFT GRAPH

    // -------- RIGHT GRAPH
    ;
    // ------- USER DECLARATION
  }

  public int reverseAssoc(int i) {
    return -1;
  }

  public int attachedNode(int i) {
    return -1;
  }

  public JerboaRuleResult applyRule(JerboaGMap gmap) throws JerboaException {
    JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
    return applyRule(gmap, ____jme_hooks);
  }

  @Override
  public JerboaRuleResult apply(final JerboaGMap gmap, final JerboaInputHooks hooks)
      throws JerboaException {
    // BEGIN SCRIPT GENERATION
    return MesScriptsRejeu.exe3(modeler, gmap, hooks, 3);
    // END SCRIPT GENERATION

  }
} // end rule Class
