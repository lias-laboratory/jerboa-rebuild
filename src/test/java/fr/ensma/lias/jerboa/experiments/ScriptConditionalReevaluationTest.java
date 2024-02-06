package fr.ensma.lias.jerboa.experiments;

import static org.junit.Assert.*;

import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceAndCover;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.Rainure2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.RainureDouble2D;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.Retrecissement2D;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.PierceFaceAndCoverFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.Rainure2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDivise2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.RainureDouble2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.Retrecissement2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.core.rule.rules.CreationFake.InsertVertexFoldedFake;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.rule.rules.Split.TriangulateFace;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.ChamferCorner2DFake;
import fr.ensma.lias.jerboa.core.rule.rules.SplitFake.TriangulateFaceFake;
import fr.ensma.lias.jerboa.core.tracking.JerboaStaticDetection;
import fr.ensma.lias.jerboa.datastructures.Event;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.Pair;
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

  private String printOrbits(List<JerboaRuleNode> foundOrbits) {

    return foundOrbits.stream()
        .map(node -> node.getName())
        .collect(Collectors.joining(";", "[", "]"));
  }

  private String printCombination(Pair<List<Integer>, List<JerboaRuleNode>> p) {
    StringBuilder sb = new StringBuilder();
    for (Integer i : p.l()) {
      for (JerboaRuleNode r : p.r()) {
        sb.append(i).append(",").append(r.getName()).append(" ");
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  @Test
  public void test_matchRHS_triangulation_vertexCreation_WhenBaseStrategy() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();
    TriangulateFace triangulateRule = (TriangulateFace) modeler.getRule("TriangulateFace");

    JerboaStaticDetection detector = new JerboaStaticDetection(triangulateRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            triangulateRule,
            detector,
            JerboaOrbit.orbit(0, 1, 3),
            JerboaOrbit.orbit(1, 2, 3),
            Event.CREATION,
            0);

    System.out.println(printOrbits(foundOrbits));

    assertEquals(1, foundOrbits.size());
    assertEquals("[n2]", printOrbits(foundOrbits));
  }

  @Test
  public void test_matchRHS_pierce_vertexCreation_WhenBaseStrategy() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    PierceFaceAndCover pierceRule = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");

    JerboaStaticDetection detector = new JerboaStaticDetection(pierceRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            pierceRule,
            detector,
            JerboaOrbit.orbit(0, 1, 3),
            JerboaOrbit.orbit(1, 2, 3),
            Event.CREATION,
            0);

    System.out.println(printOrbits(foundOrbits));

    assertEquals(0, foundOrbits.size());
  }

  @Test
  public void test_matchRHS_pierce_vertexCreation_WhenSubSupOrbitStrategy() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    PierceFaceAndCover pierceRule = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");

    JerboaStaticDetection detector = new JerboaStaticDetection(pierceRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            pierceRule,
            detector,
            JerboaOrbit.orbit(0, 1, 3),
            JerboaOrbit.orbit(1, 2, 3),
            Event.CREATION,
            1);

    assertEquals(1, foundOrbits.size());
  }

  @Test
  public void test_matchRHS_rainure_edgeCreation_WhenBaseStrategy() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    Rainure2D rainureRule = (Rainure2D) modeler.getRule("Rainure2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            rainureRule, detector, JerboaOrbit.orbit(0), JerboaOrbit.orbit(0), Event.CREATION, 0);

    assertEquals(1, foundOrbits.size());
    assertEquals("[n4]", printOrbits(foundOrbits));
  }

  @Test
  public void test_matchRHS_rainureDouble_edgeCreation_WhenBaseStrategy() throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    RainureDouble2D rainureDoubleRule = (RainureDouble2D) modeler.getRule("RainureDouble2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureDoubleRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            rainureDoubleRule,
            detector,
            JerboaOrbit.orbit(0),
            JerboaOrbit.orbit(0),
            Event.CREATION,
            0);

    assertEquals(1, foundOrbits.size());
    assertEquals("[n8]", printOrbits(foundOrbits));
  }

  @Test
  public void test_matchRHS_rainureDouble_edgeCreation_WhenSubSupOrbitStrategy()
      throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    RainureDouble2D rainureDoubleRule = (RainureDouble2D) modeler.getRule("RainureDouble2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureDoubleRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            rainureDoubleRule,
            detector,
            JerboaOrbit.orbit(0),
            JerboaOrbit.orbit(0),
            Event.CREATION,
            1);

    assertEquals(4, foundOrbits.size());
    assertEquals("[n2;n4;n6;n8]", printOrbits(foundOrbits));
  }

  @Test
  public void test_matchRHS_rainureDouble_edgeCreation_WhenSubSupOrbitStrictStrategy()
      throws JerboaException {
    ModelerGenerated modeler = new ModelerGenerated();

    RainureDouble2D rainureDoubleRule = (RainureDouble2D) modeler.getRule("RainureDouble2D");

    JerboaStaticDetection detector = new JerboaStaticDetection(rainureDoubleRule);

    List<JerboaRuleNode> foundOrbits =
        ScriptConditionalReevaluation.collectOrbitsRoots(
            rainureDoubleRule,
            detector,
            JerboaOrbit.orbit(0),
            JerboaOrbit.orbit(0),
            Event.CREATION,
            2);

    assertEquals(3, foundOrbits.size());
    assertEquals("[n2;n4;n6]", printOrbits(foundOrbits));
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
        ScriptConditionalReevaluation.getLHSDarts(JerboaOrbit.orbit(0), leftPattern);

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
        ScriptConditionalReevaluation.getLHSDarts(JerboaOrbit.orbit(), leftPattern);

    System.out.println("Found darts are : " + l);
  }

  @Test
  public void test_getLHSDarts_triangulateFace() throws JerboaException {

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    TriangulateFaceFake tf = (TriangulateFaceFake) modeler.getRule("TriangulateFaceFake");
    List<JerboaRowPattern> leftPatternA;

    try {
      tf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPatternA = (List<JerboaRowPattern>) tf.getFakeLeft();

    JerboaRuleNode n = tf.getRightRuleNode(tf.getRightIndexRuleNode("n2"));

    ScriptConditionalReevaluation.getLHSDarts(JerboaOrbit.orbit(0), leftPatternA)
        .forEach(d -> System.out.println(d));
    ;
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
        ScriptConditionalReevaluation.getLHSDarts(JerboaOrbit.orbit(), leftPattern);

    System.out.println("Found darts are : " + l);
  }

  @Test
  public void test_match_rainure_rainuredouble() throws JerboaException {
    //

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    Rainure2DFake rf = (Rainure2DFake) modeler.getRule("Rainure2DFake");
    List<JerboaRowPattern> leftPatternA;

    try {
      rf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPatternA = (List<JerboaRowPattern>) rf.getFakeLeft();

    RainureDouble2DFake rdf = (RainureDouble2DFake) modeler.getRule("RainureDouble2DFake");
    List<JerboaRowPattern> leftPatternB;

    try {
      rdf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPatternB = (List<JerboaRowPattern>) rdf.getFakeLeft();

    JerboaRuleNode n = rf.getRightRuleNode(rf.getRightIndexRuleNode("n4"));

    Pair<List<Integer>, List<JerboaRuleNode>> p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            rf, rdf, leftPatternA, leftPatternB, n, JerboaOrbit.orbit(0), Event.CREATION);

    List<Integer> expectedInstanceMatch = Arrays.asList(0);
    JerboaRuleNode n8 = rdf.getRightRuleNode(rdf.getRightIndexRuleNode("n8"));
    List<JerboaRuleNode> expectedRuleNodesMatch = Arrays.asList(n8);
    assertTrue(
        expectedInstanceMatch.containsAll(p.l()) && p.l().containsAll(expectedInstanceMatch));
    assertTrue(
        expectedRuleNodesMatch.containsAll(p.r()) && p.r().containsAll(expectedRuleNodesMatch));
  }

  @Test
  public void test_match_insertVertexFolded_chamferCorner2D() throws JerboaException {
    //

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    // JerboaDart dart2 = squareRuleResult.get(0, 0);
    JerboaDart dart2 = squareRuleResult.get(1, 0);

    List<List<JerboaDart>> topoParameterIVFF =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    List<List<JerboaDart>> topoParameterCCF =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart2)));

    InsertVertexFoldedFake ivff =
        (InsertVertexFoldedFake) modeler.getRule("InsertVertexFoldedFake");
    List<JerboaRowPattern> leftPatternA;

    try {
      ivff.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameterIVFF));
    } catch (Exception e) {
    }

    leftPatternA = (List<JerboaRowPattern>) ivff.getFakeLeft();

    ChamferCorner2DFake ccf = (ChamferCorner2DFake) modeler.getRule("ChamferCorner2DFake");
    List<JerboaRowPattern> leftPatternB;

    try {
      ccf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameterCCF));
    } catch (Exception e) {
    }

    leftPatternB = (List<JerboaRowPattern>) ccf.getFakeLeft();

    JerboaRuleNode n = ivff.getRightRuleNode(ivff.getRightIndexRuleNode("n0"));

    Pair<List<Integer>, List<JerboaRuleNode>> p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            ivff, ccf, leftPatternA, leftPatternB, n, JerboaOrbit.orbit(0), Event.SPLIT);

    List<Integer> expectedInstanceMatch = Arrays.asList(1);
    assertTrue(
        expectedInstanceMatch.containsAll(p.l()) && p.l().containsAll(expectedInstanceMatch));
  }

  @Test
  public void test_match_triangulation_pierce() throws JerboaException {
    //

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    List<List<JerboaDart>> topoParameter =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    TriangulateFaceFake tf = (TriangulateFaceFake) modeler.getRule("TriangulateFaceFake");
    List<JerboaRowPattern> leftPatternA;

    try {
      tf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPatternA = (List<JerboaRowPattern>) tf.getFakeLeft();

    PierceFaceAndCoverFake pfcf =
        (PierceFaceAndCoverFake) modeler.getRule("PierceFaceAndCoverFake");
    List<JerboaRowPattern> leftPatternB;

    try {
      pfcf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    leftPatternB = (List<JerboaRowPattern>) pfcf.getFakeLeft();

    PierceFaceAndCover pfc = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");
    JerboaRuleResult rightPatternB;

    try {
      rightPatternB = pfcf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameter));
    } catch (Exception e) {
    }

    JerboaRuleNode n = tf.getRightRuleNode(tf.getRightIndexRuleNode("n2"));

    Pair<List<Integer>, List<JerboaRuleNode>> p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            tf, pfcf, leftPatternA, leftPatternB, n, JerboaOrbit.orbit(1, 2), Event.CREATION);

    List<Integer> expectedInstanceMatch = Arrays.asList(0);
    JerboaRuleNode n2 = pfcf.getRightRuleNode(pfcf.getRightIndexRuleNode("n2"));
    List<JerboaRuleNode> expectedRuleNodesMatch = Arrays.asList();
    assertTrue(
        expectedInstanceMatch.containsAll(p.l()) && p.l().containsAll(expectedInstanceMatch));
    assertTrue(
        expectedRuleNodesMatch.containsAll(p.r()) && p.r().containsAll(expectedRuleNodesMatch));
  }

  @Test
  public void test_match_rainuredivise_retrecissement() throws JerboaException {
    //

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    JerboaDart dart2 = squareRuleResult.get(3, 0);

    List<List<JerboaDart>> topoParameterRDF =
        Stream.of(Arrays.asList(dart), Arrays.asList(dart2)).collect(Collectors.toList());

    List<List<JerboaDart>> topoParameterRF =
        new ArrayList<List<JerboaDart>>(Arrays.asList(Arrays.asList(dart)));

    RainureDivise2DFake rdf = (RainureDivise2DFake) modeler.getRule("RainureDivise2DFake");
    List<JerboaRowPattern> leftPatternA;

    try {
      rdf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameterRDF));
    } catch (Exception e) {
    }

    leftPatternA = (List<JerboaRowPattern>) rdf.getFakeLeft();

    Retrecissement2DFake rf = (Retrecissement2DFake) modeler.getRule("Retrecissement2DFake");
    List<JerboaRowPattern> leftPatternB;

    try {
      rf.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameterRF));
    } catch (Exception e) {
    }

    Retrecissement2D r = (Retrecissement2D) modeler.getRule("Retrecissement2D");

    try {
      r.apply(modeler.getGMap(), new JerboaInputHooksGeneric(topoParameterRF));
    } catch (Exception e) {
    }

    leftPatternB = (List<JerboaRowPattern>) rf.getFakeLeft();

    JerboaRuleNode n = rdf.getRightRuleNode(rdf.getRightIndexRuleNode("n3"));

    Pair<List<Integer>, List<JerboaRuleNode>> p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            rdf, rf, leftPatternA, leftPatternB, n, JerboaOrbit.orbit(0), Event.CREATION);

    printCombination(p);

    List<Integer> expectedInstanceMatch = Arrays.asList(0);
    JerboaRuleNode n11 = rf.getRightRuleNode(rf.getRightIndexRuleNode("n11"));
    List<JerboaRuleNode> expectedRuleNodesMatch = Arrays.asList(n11);
    assertTrue(
        expectedInstanceMatch.containsAll(p.l()) && p.l().containsAll(expectedInstanceMatch));
    assertTrue(
        expectedRuleNodesMatch.containsAll(p.r()) && p.r().containsAll(expectedRuleNodesMatch));
  }
}
