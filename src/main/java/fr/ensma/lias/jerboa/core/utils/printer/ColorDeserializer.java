package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import java.awt.Color;
import java.lang.reflect.Type;

/** JerboaRuleOperationDeserializer */
public class ColorDeserializer implements JsonDeserializer<Color> {

  ModelerGenerated modeler;

  @Override
  public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jObject = json.getAsJsonObject();
    Color color =
        new Color(
            jObject.get("red").getAsFloat(),
            jObject.get("green").getAsFloat(),
            jObject.get("blue").getAsFloat());
    return color;
  }
}
