package fr.ensma.lias.jerboa.core.rule;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.JerboaRuleScript;
import up.jerboa.exception.JerboaException;

public class JerboaRebuiltRuleScript extends JerboaRuleScript {

  protected JerboaRebuiltRuleScript(ModelerGenerated modeler, String name, String category) {
    super(modeler, name, category);
    // TODO Auto-generated constructor stub
    System.out.println("SCRIPT: Ctor is called");
  }

  @Override
  public JerboaRuleResult applyRule(JerboaGMap map, JerboaInputHooks hooks) throws JerboaException {
    JerboaRuleResult res = null;
    System.out.println("<SCRIPT>");
    res = super.applyRule(map, hooks);
    System.out.println("</SCRIPT>");
    return res;
  }

  @Override
  public JerboaRuleResult apply(JerboaGMap gmap, JerboaInputHooks hooks) throws JerboaException {
    // TODO Auto-generated method stub
    return null;
  }
}
