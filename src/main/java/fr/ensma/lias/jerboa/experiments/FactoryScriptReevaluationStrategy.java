package fr.ensma.lias.jerboa.experiments;

/** FactoryScriptSReevaluationStrategy */
public class FactoryScriptReevaluationStrategy {

  public static ScriptReevaluationStrategy create(int ID) {
    switch (ID) {
      case 0:
        return createBaseTester();
      case 1:
        return createSubSupStrategy();
      case 2:
        return createSubSupStrictStrategy();
      default:
        System.out.println("Warning: unrecognized strategy ID. Falling back to 0 (base)");
        return createBaseTester();
    }
  }

  public static ScriptIfElseBaseStrategy createBaseTester() {
    return new ScriptIfElseBaseStrategy();
  }

  public static ScriptIfElseSubSupOrbitsStrategy createSubSupStrategy() {
    return new ScriptIfElseSubSupOrbitsStrategy();
  }

  public static ScriptIfElseSubSubOrbitsStrictStrategy createSubSupStrictStrategy() {
    return new ScriptIfElseSubSubOrbitsStrictStrategy();
  }
}
