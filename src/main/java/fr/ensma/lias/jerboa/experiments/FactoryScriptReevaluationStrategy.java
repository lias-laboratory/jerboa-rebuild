package fr.ensma.lias.jerboa.experiments;

/** FactoryScriptSReevaluationStrategy */
public class FactoryScriptReevaluationStrategy {

  public static ScriptReevaluationStrategy create(int ID) {
    switch (ID) {
      case 0:
        return createBaseTester();
      case 1:
        return createSubSupStrategy();
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
}
