package fr.ensma.lias.jerboa.experiments;

import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceAndCover;
import fr.ensma.lias.jerboa.core.rule.rules.Carving.PierceFaceCoverDiamond;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.PierceFaceAndCoverFake;
import fr.ensma.lias.jerboa.core.rule.rules.CarvingFake.PierceFaceCoverDiamondFake;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.SubdivQuad;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivQuadFake;
import fr.ensma.lias.jerboa.core.rule.rules.Subdivision.SubdivTri;
import fr.ensma.lias.jerboa.core.rule.rules.SubdivisionFake.SubdivTriFake;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.util.ArrayList;
import java.util.List;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRowPattern;
import up.jerboa.exception.JerboaException;

/** MesScriptsRejeu */
public class MesScriptsRejeu {

  public static JerboaRuleResult exe1(JerboaModeler modeler, JerboaGMap gmap,
      JerboaInputHooks hooks) {
    System.out.println("HELLO");
    int res = Vec3.askInt("0 faux, autre vrai", "0");
    SubdivQuadFake sqf = (SubdivQuadFake) modeler.getRule("SubdivQuadFake");
    SubdivTri st = (SubdivTri) modeler.getRule("SubdivTri");

    SubdivQuad sq = (SubdivQuad) modeler.getRule("SubdivQuad");
    SubdivTriFake stf = (SubdivTriFake) modeler.getRule("SubdivTriFake");
    List<JerboaRowPattern> leftPattern;

    if (res != 0) {
      try {
        sqf.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
      leftPattern = (List<JerboaRowPattern>) sqf.getFakeLeft();
      for (JerboaRowPattern row : leftPattern) {
        System.out.println(row);
      }
      try {
        st.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    } else {
      try {
        stf.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
      leftPattern = (List<JerboaRowPattern>) stf.getFakeLeft();
      for (JerboaRowPattern row : leftPattern) {
        System.out.println(row);
      }
      try {
        sq.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static JerboaRuleResult exe2(JerboaModeler modeler, JerboaGMap gmap,
      JerboaInputHooks hooks) {
    System.out.println("Triangulation To PierceFaceAndCover");
    int res = Vec3.askInt("0 faux, autre vrai", "0");
    PierceFaceAndCoverFake pfcf =
        (PierceFaceAndCoverFake) modeler.getRule("PierceFaceAndCoverFake");
    PierceFaceCoverDiamond pfcd =
        (PierceFaceCoverDiamond) modeler.getRule("PierceFaceCoverDiamond");

    PierceFaceAndCover pfc = (PierceFaceAndCover) modeler.getRule("PierceFaceAndCover");
    PierceFaceCoverDiamondFake pfcdf =
        (PierceFaceCoverDiamondFake) modeler.getRule("PierceFaceCoverDiamondFake");
    List<JerboaRowPattern> leftPattern;

    if (res != 0) {
      try {
        pfcdf.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
      leftPattern = (List<JerboaRowPattern>) pfcdf.getFakeLeft();
      for (JerboaRowPattern row : leftPattern) {
        System.out.println(row);
      }
      try {
        pfc.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    } else {
      try {
        pfcf.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
      leftPattern = (List<JerboaRowPattern>) pfcf.getFakeLeft();
      for (JerboaRowPattern row : leftPattern) {
        System.out.println(row);
      }
      try {
        pfcd.apply(gmap, hooks);
      } catch (JerboaException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
