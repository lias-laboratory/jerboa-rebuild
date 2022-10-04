package fr.ensma.lias.jerboa.core.utils.printer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import fr.ensma.lias.jerboa.datastructures.PersistentID;
import fr.ensma.lias.jerboa.datastructures.PersistentIdElement;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import up.jerboa.core.JerboaOrbit;

/**
 * JerboaOrbitDeserializer
 */
public class PersistentNameDeserializer implements JsonDeserializer<PersistentName> {

	@Override
	public PersistentName deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject jObject = json.getAsJsonObject();
		String pName = jObject.get("PersistentName").getAsString();

		PersistentName PN = new PersistentName();

		String[] pIStrings = pName.replaceAll("\\[", "").replaceAll("]", "").split(",");
		for (int i = 0; i < pIStrings.length; i++) {
			PersistentID PI = new PersistentID();
			String[] pIdElementStrings =
					pIStrings[i].replaceAll("\\{", "").replaceAll("}", "").split(";\\s*");
			for (int j = 0; j < pIdElementStrings.length; j++) {
				String[] pieString = pIdElementStrings[j].split("\\s-\\s");
				int appId = Integer.valueOf(pieString[0]);
				String nodeName = pieString[1];
				PersistentIdElement pie = new PersistentIdElement(appId, nodeName);
				PI.add(pie);
			}
			PN.add(PI);
		}

		String orbitTypeString = jObject.get("orbitType").getAsString();
		String[] orbitTypesStrings =
				orbitTypeString.replaceAll("\\(", "").replaceAll("\\)", "").split("\\s*,\\s*");
		List<Integer> orbitType = new ArrayList<>();
		for (int i = 0; i < orbitTypesStrings.length; i++) {
			orbitType.add(Integer.valueOf(orbitTypesStrings[i]));
		}
		PN.setOrbitType(JerboaOrbit.orbit(orbitType));

		return PN;
	}


}
