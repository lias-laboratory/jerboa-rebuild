package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import up.jerboa.core.JerboaRuleOperation;

/** JerboaOrbitSerializer */
public class JerboaRuleOperationSerializer implements JsonSerializer<JerboaRuleOperation> {

  @Override
  public JsonElement serialize(
      JerboaRuleOperation src, Type typeOfSrc, JsonSerializationContext context) {
    String name = "name";
    JsonObject jObject = new JsonObject();
    jObject.addProperty(name, src.getName());
    return jObject;
  }
}
