package fr.ensma.lias.jerboa.experiments;

import static org.junit.Assert.*;

import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceAndCover;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.Rainure2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.RainureDouble2D;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDouble2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.InsertVertexFoldedFake;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.ChamferCorner2DFake;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
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

  private void printOrbits(List<List<JerboaRuleNode>> foundOrbits) {

    System.out.println("Found Orbits are : ");
    foundOrbits.stream()
        .forEach(
            orbit ->
                System.out.println(
                    '\t'
                        + orbit.stream()
                            .map(n -> n.getName())
                            .collect(Collectors.joining(";", "[", "]"))));
  }

  @Test
  public void test_matchRHS_triangulation_creation_vertex() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    TriangulateFace triangulateRule = (TriangulateFace) modeler.getRule("TriangulateFace");

    JerboaStaticDetection detector = new JerboaStaticDetection(triangulateRule);

    List<List<JerboaRuleNode>> foundOrbits =
        ScriptConditionalReevaluation.findRHSOrbits(
            triangulateRule, detector, "n2", JerboaOrbit.orbit(1, 2, 3), Event.CREATION);

    printOrbits(foundOrbits);

    assertEquals(1, foundOrbits.size());
  }

  @Test
  public void test_matchRHS_pierce_creation_vertex() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    PierceFaceAndCover pierceRule = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");

    JerboaStaticDetection detector = new JerboaStaticDetection(pierceRule);

    List<List<JerboaRuleNode>> foundOrbits =
        ScriptConditionalReevaluation.findRHSOrbits(
            pierceRule, detector, null, JerboaOrbit.orbit(1, 2, 3), Event.CREATION);

    printOrbits(foundOrbits);

    assertEquals(1, foundOrbits.size());
  }

  @Test
  public void test_matchRHS_rainure_create_edge() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    Rainure2D rainureRule = (Rainure2D) modeler.getRule("Rainure2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureRule);

    List<List<JerboaRuleNode>> foundOrbits =
        ScriptConditionalReevaluation.findRHSOrbits(
            rainureRule, detector, "n4", JerboaOrbit.orbit(0), Event.CREATION);

    printOrbits(foundOrbits);

    assertEquals(1, foundOrbits.size());
  }

  @Test
  public void test_matchRHS_rainureDouble_create_edge() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    RainureDouble2D rainureDoubleRule = (RainureDouble2D) modeler.getRule("RainureDouble2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureDoubleRule);

    List<List<JerboaRuleNode>> foundOrbits =
        ScriptConditionalReevaluation.findRHSOrbits(
            rainureDoubleRule, detector, null, JerboaOrbit.orbit(0), Event.CREATION);

    printOrbits(foundOrbits);

    assertEquals(4, foundOrbits.size());
  }

  @Test
  public void test_getOriginDarts_rainure_created_edge() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    RainureDouble2DFake rdf = (RainureDouble2DFake) modeler.getRule("RainureDouble2DFake");
    List<JerboaRowPattern> leftPattern;

    try {
      rdf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPattern = (List<JerboaRowPattern>) rdf.getFakeLeft();

    // Get darts for an origin type <0>
    List<JerboaDart> l =
        ScriptConditionalReevaluation.getLHSDarts(
            JerboaOrbit.orbit(0), leftPattern, Arrays.asList());

    System.out.println("Found darts are : " + l);
  }

  @Test
  public void test_getOriginDarts_insertvertexfolded_split_edge() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    InsertVertexFoldedFake ivff =
        (InsertVertexFoldedFake) modeler.getRule("InsertVertexFoldedFake");
    List<JerboaRowPattern> leftPattern;

    try {
      ivff.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPattern = (List<JerboaRowPattern>) ivff.getFakeLeft();

    // Get darts for an origin type <>
    List<JerboaDart> l =
        ScriptConditionalReevaluation.getLHSDarts(
            JerboaOrbit.orbit(), leftPattern, Arrays.asList());

    System.out.println("Found darts are : " + l);
  }

  @Test
  public void test_getOriginDarts_chamfercorner2D_unchanged_vertex() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    ChamferCorner2DFake ccf = (ChamferCorner2DFake) modeler.getRule("ChamferCorner2DFake");
    List<JerboaRowPattern> leftPattern;

    try {
      ccf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPattern = (List<JerboaRowPattern>) ccf.getFakeLeft();

    // Get darts for an origin type <>
    List<JerboaDart> l =
        ScriptConditionalReevaluation.getLHSDarts(
            JerboaOrbit.orbit(), leftPattern, Arrays.asList());

    System.out.println("Found darts are : " + l);
  }
  // @Test
  // public void test_getDartInstance_subdiv() throws JerboaException {

  //   ModelerGenerated modeler = new ModelerGenerated();

  //   JerboaRuleResult squareRuleResult = createSquare(modeler);
  //   JerboaDart dart = squareRuleResult.get(0, 0);
  //   List<List<JerboaDart>> topoParameter =
  //       new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

  //   SubdivQuadFake sqf = (SubdivQuadFake) modeler.getRule("SubdivQuadFake");
  //   SubdivTriFake stf = (SubdivTriFake) modeler.getRule("SubdivTriFake");
  //   List<JerboaRowPattern> leftPattern;

  //   try {
  //     stf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
  //   } catch (Exception e) {
  //     //
  //   }

  //   leftPattern = (List<JerboaRowPattern>) stf.getFakeLeft();

  //   Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> darts =
  //       ScriptConditionalReevaluation.findOriginDarts(
  //           JerboaOrbit.orbit(0), leftPattern, Arrays.asList());

  //   System.out.println(darts.keySet());
  //   // ScriptConditionalReevaluation.matchLHSDarts(stf, null, leftPattern, null, darts);
  //   // System.out.println(darts.values());

  //   // int rowSTF = ScriptConditionalReevaluation.getDartInstance(dart, leftPattern);
  //   // int rowSQF = ScriptConditionalReevaluation.getDartInstance(dart, leftPattern);
  //   // assertEquals(rowSTF, rowSQF);
  // }

  // @Test
  // public void test_getDartInstance_rainureDivise() throws JerboaException {

  //   ModelerGenerated modeler = new ModelerGenerated();

  //   JerboaRuleResult squareRuleResult = createSquare(modeler);
  //   JerboaDart dart1 = squareRuleResult.get(3, 0);
  //   JerboaDart dart2 = squareRuleResult.get(0, 0);
  //   System.out.println("DART: " + dart1);
  //   List<List<JerboaDart>> topoParameters =
  //       new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart1),
  // Arrays.asList(dart2)));

  //   RainureDivise2DFake rdf = (RainureDivise2DFake) modeler.getRule("RainureDivise2DFake");
  //   Retrecissement2DFake r2D = (Retrecissement2DFake) modeler.getRule("Retrecissement2DFake");
  //   List<JerboaRowPattern> leftPattern;

  //   try {
  //     rdf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameters));
  //   } catch (Exception e) {
  //   }

  //   leftPattern = (List<JerboaRowPattern>) rdf.getFakeLeft();
  //   for (var i : leftPattern) System.out.println(i);

  //   Map<JerboaDart, List<Pair<JerboaRuleNode, Integer>>> darts =
  //       ScriptConditionalReevaluation.findOriginDarts(
  //           JerboaOrbit.orbit(), leftPattern, Arrays.asList());

  //   System.out.println(darts.keySet());

  //   ScriptConditionalReevaluation.matchLHSDarts(rdf, null, leftPattern, null, darts);

  //   System.out.println(darts.values());

  //   // int rowRDF = ScriptConditionalReevaluation.getDartInstance(dart1, leftPattern);
  //   // assertEquals(0, rowRDF);
  //   // int rowRDF2 = ScriptConditionalReevaluation.getDartInstance(dart2, leftPattern);
  //   // assertEquals(1, rowRDF2);
  // }
}
