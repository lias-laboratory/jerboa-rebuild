package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Color;
import java.lang.reflect.Type;

public class ColorSerializer implements JsonSerializer<Color> {

  @Override
  public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject color = new JsonObject();
    color.addProperty("red", src.getRed());
    color.addProperty("green", src.getGreen());
    color.addProperty("blue", src.getBlue());
    return context.serialize(color);
  }
}
