package fr.ensma.lias.jerboa.datastructures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreatePentagon;
import fr.ensma.lias.jerboa.core.rule.rules.Creation.CreateSquareFace;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import java.util.ArrayList;
import org.junit.Test;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.exception.JerboaException;

public class ParametricSpecificationTest {

  private ModelerGenerated modeler = new ModelerGenerated();

  public ParametricSpecificationTest() throws JerboaException {}

  @Test
  public void test_ShallowCopy() {
    ParametricSpecification spec =
        new ParametricSpecification("Initial spec", new ArrayList<Application>(), 0, true);

    ParametricSpecification specCopy = spec;

    assertTrue(spec.getApplications() == specCopy.getApplications());
  }

  @Test
  public void test_copy_no_application_added_in_spec() throws JerboaException {
    JerboaRuleOperation createSquareFace = new CreateSquareFace(modeler);

    ParametricSpecification spec =
        new ParametricSpecification("Initial spec", new ArrayList<Application>(), 0, true);

    ParametricSpecification specCopy = new ParametricSpecification(spec);

    specCopy.addApplication(createSquareFace, null, new ArrayList<Integer>(), ApplicationType.INIT);
    specCopy.setDisplayName("Reevaluation spec");

    assertFalse(spec.getApplications() == specCopy.getApplications());
  }

  @Test
  public void test_copy_no_same_rule() throws JerboaException {
    JerboaRuleOperation createSquareFace = new CreateSquareFace(modeler);
    JerboaRuleOperation createPentagon = new CreatePentagon(modeler);
    //
    ParametricSpecification spec =
        new ParametricSpecification("Initial spec", new ArrayList<Application>(), 0, true);

    ParametricSpecification specCopy = new ParametricSpecification(spec);

    assertFalse(spec.getApplications() == specCopy.getApplications());

    spec.addApplication(createPentagon, null, new ArrayList<Integer>(), ApplicationType.INIT);
    specCopy.addApplication(createSquareFace, null, new ArrayList<Integer>(), ApplicationType.INIT);

    assertFalse(
        spec.getApplications()
            .get(0)
            .getRule()
            .getName()
            .equals(specCopy.getApplications().get(0).getRule().getName()));
  }
}
