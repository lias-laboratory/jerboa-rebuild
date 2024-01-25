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

    ScriptConditionalReevaluation.getLHSDarts(JerboaOrbit.orbit(0), leftPatternA, Arrays.asList())
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
        ScriptConditionalReevaluation.getLHSDarts(
            JerboaOrbit.orbit(), leftPattern, Arrays.asList());

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

    var p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            rf,
            rdf,
            leftPatternA,
            leftPatternB,
            Arrays.asList(),
            n,
            JerboaOrbit.orbit(0),
            Event.CREATION);

    for (Integer i : p.l()) {
      for (JerboaRuleNode r : p.r()) {
        System.out.println(i + "," + r.getName() + " ");
      }
      System.out.println("");
    }
  }

  @Test
  public void test_match_insertVertexFolded_chamferCorner2D() throws JerboaException {
    //

    ModelerGenerated modeler = new ModelerGenerated();

    // Create a square face
    JerboaRuleResult squareRuleResult = createSquare(modeler);
    // This dart will be used as a topological parameter for the rainure
    JerboaDart dart = squareRuleResult.get(0, 0);
    JerboaDart dart2 = squareRuleResult.get(7, 0);

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

    var p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            ivff,
            ccf,
            leftPatternA,
            leftPatternB,
            Arrays.asList(),
            n,
            JerboaOrbit.orbit(0),
            Event.CREATION);

    for (Integer i : p.l()) {
      for (JerboaRuleNode r : p.r()) {
        System.out.println(i + "," + r.getName() + " ");
      }
      System.out.println("");
    }
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

    var p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            tf,
            pfcf,
            leftPatternA,
            leftPatternB,
            Arrays.asList(),
            n,
            JerboaOrbit.orbit(1, 2),
            Event.CREATION);

    for (Integer i : p.l()) {
      for (JerboaRuleNode r : p.r()) {
        System.out.println(i + "," + r.getName() + " ");
      }
      System.out.println("");
    }
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

    var p =
        ScriptConditionalReevaluation.conditionalReevaluation(
            rdf,
            rf,
            leftPatternA,
            leftPatternB,
            Arrays.asList(),
            n,
            JerboaOrbit.orbit(0),
            Event.CREATION);

    for (Integer i : p.l()) {
      for (JerboaRuleNode node : p.r()) {
        System.out.println(i + "," + node.getName() + " ");
      }
      System.out.println("");
    }
  }
}
