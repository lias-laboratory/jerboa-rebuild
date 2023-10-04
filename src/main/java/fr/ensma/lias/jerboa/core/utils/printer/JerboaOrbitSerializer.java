package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import java.lang.reflect.Type;

/** JerboaOrbitSerializer */
public class JerboaOrbitSerializer implements JsonSerializer<PersistentName> {

  @Override
  public JsonElement serialize(
      PersistentName src, Type typeOfSrc, JsonSerializationContext context) {
    String PN = "PersistentName";
    String orbitType = "orbitType";
    JsonObject jObject = new JsonObject();
    jObject.addProperty(PN, src.getPIs().toString());

    String orbit = "";
    if (src.getOrbitType() != null) {
      orbit = src.getOrbitType().toString().replace('<', '(').replace('>', ')').replace("a", "");
    }

    jObject.addProperty(orbitType, orbit);

    return jObject;
  }
}
