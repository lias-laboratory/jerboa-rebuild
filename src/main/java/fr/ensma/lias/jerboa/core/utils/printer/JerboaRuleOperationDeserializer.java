package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import java.lang.reflect.Type;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.exception.JerboaException;

/** JerboaRuleOperationDeserializer */
public class JerboaRuleOperationDeserializer implements JsonDeserializer<JerboaRuleOperation> {

  ModelerGenerated modeler;

  public JerboaRuleOperationDeserializer(ModelerGenerated modeler) throws JerboaException {
    this.modeler = modeler;
  }

  @Override
  public JerboaRuleOperation deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();
    return modeler.getRule(jObject.get("name").getAsString());
  }
}
