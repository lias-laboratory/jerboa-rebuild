package fr.ensma.lias.jerboa.core.utils.printer;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.LevelEventHR;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecifications;
import fr.ensma.lias.jerboa.datastructures.PersistentName;
import fr.ensma.lias.jerboa.datastructures.SpecificationEntry;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.exception.JerboaException;

public class JSONPrinter {

	static String resourcesPath = "./src/test/resources";
	static String exportsPath = "./exports";

	public JSONPrinter() {}

	public static <T> void exportHistoryRecord(HashMap<Integer, List<LevelEventHR>> leaves,
			String fileName) throws IOException {
		GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
		// gBuilder.registerTypeAdapter(LevelOrbitHRSerializer.class, new LevelOrbitHRSerializer());
		String json = gBuilder.create().toJson(leaves);
		Files.write(Path.of(exportsPath + "/" + fileName), json.getBytes(),
				StandardOpenOption.CREATE);
	}

	public static void exportHistoryRecord(HashMap<Integer, List<LevelEventHR>> leaves,
			String directory, String fileName) throws IOException {
		GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
		String json = gBuilder.create().toJson(leaves);
		Files.write(Path.of(directory + "/" + fileName), json.getBytes(),
				StandardOpenOption.CREATE);
	}

	public static <T> void exportParametricSpecification(Collection<T> c, String fileName)
			throws IOException {
		GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
		gBuilder.registerTypeAdapter(PersistentName.class, new JerboaOrbitSerializer());
		gBuilder.registerTypeAdapter(JerboaRuleOperation.class,
				new JerboaRuleOperationSerializer());
		String json = gBuilder.create().toJson(c);
		Files.write(Path.of(fileName), json.getBytes(), StandardOpenOption.CREATE);
	}

	public static ParametricSpecifications importParametricSpecification(String filename,
			ModelerGenerated modeler) throws IOException, JerboaException {
		JsonReader reader = new JsonReader(new FileReader(resourcesPath + "/" + filename));

		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(JerboaRuleOperation.class,
				new JerboaRuleOperationDeserializer(modeler));
		builder.registerTypeAdapter(PersistentName.class, new PersistentNameDeserializer());
		SpecificationEntry[] spec = builder.create().fromJson(reader, SpecificationEntry[].class);
		List<SpecificationEntry> entries = Arrays.asList(spec);

		ParametricSpecifications paramSpec = new ParametricSpecifications(entries);

		return paramSpec;
	}

	public static ParametricSpecifications importParametricSpecification(String directory,
			String filename, ModelerGenerated modeler) throws IOException, JerboaException {
		JsonReader reader = new JsonReader(new FileReader(directory + "/" + filename));

		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(JerboaRuleOperation.class,
				new JerboaRuleOperationDeserializer(modeler));
		builder.registerTypeAdapter(PersistentName.class, new PersistentNameDeserializer());
		SpecificationEntry[] spec = builder.create().fromJson(reader, SpecificationEntry[].class);
		List<SpecificationEntry> entries = Arrays.asList(spec);

		ParametricSpecifications paramSpec = new ParametricSpecifications(entries);

		return paramSpec;
	}

}
