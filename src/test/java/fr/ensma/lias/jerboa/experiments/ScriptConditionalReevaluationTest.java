package fr.ensma.lias.jerboa.experiments;

import static org.junit.Assert.*;

import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDivise2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.Retrecissement2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivQuadFake;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivTriFake;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.exception.JerboaException;

public class ScriptConditionalReevaluationTest {

  private JerboaRuleResult createSquare(ModelerGenerated modeler) {
    JerboaGMap gmap = modeler.getGMap();
    CreateSquareFace csf = (CreateSquareFace) modeler.getRule("CreateSquareFace");
    try {
      return csf.apply(gmap, new JerboaInputHooksGeneric(Arrays.asList()));
    } catch (JerboaException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Test
  public void test_getDartInstance_subdiv() throws JerboaException {

    ModelerGenerated modeler = new ModelerGenerated();

    JerboaRuleResult squareRuleResult = createSquare(modeler);
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    SubdivQuadFake sqf = (SubdivQuadFake) modeler.getRule("SubdivQuadFake");
    SubdivTriFake stf = (SubdivTriFake) modeler.getRule("SubdivTriFake");
    List<JerboaRowPattern> leftPattern;

    try {
      stf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
      //
    }

    leftPattern = (List<JerboaRowPattern>) stf.getFakeLeft();
    int rowSTF = ScriptConditionalReevaluation.getDartInstance(dart, leftPattern);
    int rowSQF = ScriptConditionalReevaluation.getDartInstance(dart, leftPattern);
    assertEquals(rowSTF, rowSQF);
  }

  @Test
  public void test_getDartInstance_rainure() throws JerboaException {

    ModelerGenerated modeler = new ModelerGenerated();

    JerboaRuleResult squareRuleResult = createSquare(modeler);
    JerboaDart dart1 = squareRuleResult.get(3, 0);
    JerboaDart dart2 = squareRuleResult.get(0, 0);
    System.out.println("DART: " + dart1);
    List<List<JerboaDart>> topoParameters =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart1), Arrays.asList(dart2)));

    RainureDivise2DFake rdf = (RainureDivise2DFake) modeler.getRule("RainureDivise2DFake");
    Retrecissement2DFake r2D = (Retrecissement2DFake) modeler.getRule("Retrecissement2DFake");
    List<JerboaRowPattern> leftPattern;

    try {
      rdf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameters));
    } catch (Exception e) {
    }

    leftPattern = (List<JerboaRowPattern>) rdf.getFakeLeft();
    int rowRDF = ScriptConditionalReevaluation.getDartInstance(dart1, leftPattern);
    assertEquals(0, rowRDF);
    int rowRDF2 = ScriptConditionalReevaluation.getDartInstance(dart2, leftPattern);
    assertEquals(1, rowRDF2);
  }

  // @Test
  // public void test_matchRHS_triangulation_pierce() throws JerboaException {
  //   ModelerGenerated modeler = new ModelerGenerated();

  //   TriangulateFace triangulateRule = (TriangulateFace) modeler.getRule("TriangulateFace");
  //   PierceFaceAndCover pierceRule = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");

  //   JerboaStaticDetection detectorTriangulate = new JerboaStaticDetection(triangulateRule);
  //   JerboaStaticDetection detectorPierce = new JerboaStaticDetection(pierceRule);

  //   List<List<JerboaRuleNode>> foundOrbitsTriangulate =
  //       ScriptConditionalReevaluation.findRHSOrbits(
  //           triangulateRule, detectorTriangulate, "n2", JerboaOrbit.orbit(1, 2, 3),
  // Event.CREATION);
  //   System.out.println("Triangulation orbit found are : " + foundOrbitsTriangulate);

  //   List<List<JerboaRuleNode>> foundOrbitsPierce =
  //       ScriptConditionalReevaluation.findRHSOrbits(
  //           pierceRule, detectorPierce, null, JerboaOrbit.orbit(1, 2, 3), Event.CREATION);
  //   System.out.println("Pierce orbit found are : " + foundOrbitsPierce);

  //   assertEquals(foundOrbitsTriangulate.size(), foundOrbitsPierce.size());
  // }

  // @Test
  // public void test_matchRHS_rainure_rainureDouble() throws JerboaException {
  //   ModelerGenerated modeler = new ModelerGenerated();

  //   Rainure2D rainureRule = (Rainure2D) modeler.getRule("Rainure2D");
  //   RainureDouble2D rainureDoubleRule = (RainureDouble2D) modeler.getRule("RainureDouble2D");

  //   JerboaStaticDetection detectorRainure = new JerboaStaticDetection(rainureRule);
  //   JerboaStaticDetection detectorRainureDouble = new JerboaStaticDetection(rainureDoubleRule);

  //   List<List<JerboaRuleNode>> foundOrbitsRainure =
  //       ScriptConditionalReevaluation.findRHSOrbits(
  //           rainureRule, detectorRainure, "n4", JerboaOrbit.orbit(0), Event.CREATION);
  //   System.out.println("Rainure orbit found are : " + foundOrbitsRainure);

  //   List<List<JerboaRuleNode>> foundOrbitsRainureDouble =
  //       ScriptConditionalReevaluation.findRHSOrbits(
  //           rainureDoubleRule, detectorRainureDouble, null, JerboaOrbit.orbit(0),
  // Event.CREATION);
  //   System.out.println("RainureDouble orbit found are : " + foundOrbitsRainureDouble);

  //   assertEquals(1, foundOrbitsRainure.size());
  //   assertEquals(4, foundOrbitsRainureDouble.size());
  // }

}
