package fr.ensma.lias.jerboa.bridge;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentIdElement;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;
import java.util.List;
import java.util.StringTokenizer;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;
import up.jerboa.exception.JerboaRuntimeException;
import up.jerboa.util.serialization.EmbeddingSerializationKind;
import up.jerboa.util.serialization.jba.JBAEmbeddingSerialization;

public class JerboaRebuiltEbdSerializer implements JBAEmbeddingSerialization {

  private ModelerGenerated modeler;

  public JerboaRebuiltEbdSerializer(ModelerGenerated modeler) {
    this.modeler = modeler;
  }

  @Override
  public EmbeddingSerializationKind kind() {
    return EmbeddingSerializationKind.SAVEANDLOAD;
  }

  @Override
  public boolean manageDimension(int dim) {
    return (dim == 3);
  }

  @Override
  public JerboaEmbeddingInfo searchCompatibleEmbedding(
      String ebdname, JerboaOrbit orbit, String type) {
    System.out.println("ebdname" + ebdname);
    if (ebdname.equals("color") && orbit.equals(modeler.getColor().getOrbit()))
      return modeler.getColor();
    else if (("point".equals(ebdname)
            || "position".equals(ebdname)
            || "pos".equals(ebdname)
            || "foldPoint".equals(ebdname))
        && orbit.equals(modeler.getPos().getOrbit())) return modeler.getPos();
    else if (ebdname.equals("PersistentID")) {
      return modeler.getPersistentID();
    }
    return null;
  }

  @Override
  public void completeProcess(JerboaGMap gmap, List<JerboaDart> created) throws JerboaException {}

  @Override
  public CharSequence serialize(JerboaEmbeddingInfo info, Object value) {
    switch (info.getName()) {
      case "point":
      case "pos":
        Vec3 p = (Vec3) value;
        return "" + p.x() + " " + p.y() + " " + p.z();
      case "color":
        Color c = (Color) value;
        return ""
            + (c.getRed() / 255.f)
            + " "
            + (c.getGreen() / 255.f)
            + " "
            + (c.getBlue() / 255.f)
            + " "
            + (c.getAlpha() / 255.f);
      case "PersistentID":
        PersistentID id = (PersistentID) value;

        return "" + id.toString();
      default:
        throw new JerboaRuntimeException("Unknown embedding in serialization!");
    }
  }

  private float clamp01(float a) {
    return Math.min(Math.max(0, a), 1);
  }

  @Override
  public Object unserialize(JerboaEmbeddingInfo info, String ebdline) {
    // ByteArrayInputStream(ebdline.getBytes());

    StringTokenizer token = new StringTokenizer(ebdline);
    switch (info.getName()) {
      case "pos":
      case "point":
        {
          float x = (Float.parseFloat(token.nextToken()));
          float y = (Float.parseFloat(token.nextToken()));
          float z = (Float.parseFloat(token.nextToken()));

          return new Vec3(x, y, z);
        }
      case "color":
        {
          float x = clamp01(Float.parseFloat(token.nextToken()));
          float y = clamp01(Float.parseFloat(token.nextToken()));
          float z = clamp01(Float.parseFloat(token.nextToken()));
          float w = clamp01(Float.parseFloat(token.nextToken()));

          return new Color(x, y, z, w);

          // return new Color((int)(x*255), (int)(y*255), (int)(z*255), (int)(w*255));
        }

      case "PersistentID":
        {
          String piStr = ebdline.replace("{", "");
          piStr = piStr.replace("}", "");
          PersistentID PI = new PersistentID();
          String[] pieString = piStr.split(";\\s");
          for (String s : pieString) {
            System.out.println(s);
            String[] pie = s.split("\\s-\\s");
            int appId = Integer.valueOf(pie[0]);
            String nodeName = pie[1];
            PI.add(new PersistentIdElement(appId, nodeName));
          }
          System.out.println("PI : " + PI.toString());
          return PI;
        }
      default:
        return null;
    }
  }
}
