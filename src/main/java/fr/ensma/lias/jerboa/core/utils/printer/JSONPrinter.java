package fr.ensma.lias.jerboa.core.utils.printer;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.LevelEventHR;
import fr.ensma.lias.jerboa.datastructures.LevelEventMT;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.ensma.lias.jerboa.datastructures.PersistentName;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.exception.JerboaException;

public class JSONPrinter {

  static String resourcesPath = "./src/test/resources";
  static String exportsPath = "./exports";

  public JSONPrinter() {}

  /**
   * Export a {@link HistoryRecord} to JSON
   *
   * @param <T>
   * @param leaves
   * @param fileName
   * @throws IOException
   */
  public static <T> void exportHistoryRecord(
      Map<Integer, List<LevelEventHR>> leaves, String fileName) throws IOException {
    GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
    // gBuilder.registerTypeAdapter(LevelOrbitHRSerializer.class, new LevelOrbitHRSerializer());
    String json = gBuilder.create().toJson(leaves);
    Files.write(Path.of(exportsPath + "/" + fileName), json.getBytes(), StandardOpenOption.CREATE);
  }

  /**
   * Export a {@link HistoryRecord} to JSON
   *
   * @param leaves
   * @param directory
   * @param fileName
   * @throws IOException
   */
  public static void exportHistoryRecord(
      Map<Integer, List<LevelEventHR>> leaves, String directory, String fileName)
      throws IOException {
    GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
    String json = gBuilder.create().toJson(leaves);
    Files.write(Path.of(directory + "/" + fileName), json.getBytes(), StandardOpenOption.CREATE);
  }

  /**
   * Export a {@link ParametricSpecification} to JSON
   *
   * @param <T>
   * @param c
   * @param fileName
   * @throws IOException
   */
  public static <T> void exportParametricSpecification(Collection<T> c, String fileName)
      throws IOException {
    GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
    gBuilder.registerTypeAdapter(PersistentName.class, new JerboaOrbitSerializer());
    gBuilder.registerTypeAdapter(JerboaRuleOperation.class, new JerboaRuleOperationSerializer());
    String json = gBuilder.create().toJson(c);
    Files.write(Path.of(fileName), json.getBytes(), StandardOpenOption.CREATE);
  }

  /**
   * Import a {@link ParametricSpecification} from JSON
   *
   * @param filename
   * @param modeler
   * @return
   * @throws IOException
   * @throws JerboaException
   */
  public static ParametricSpecification importParametricSpecification(
      String filename, ModelerGenerated modeler) throws IOException, JerboaException {
    JsonReader reader = new JsonReader(new FileReader(resourcesPath + "/" + filename));

    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapter(
        JerboaRuleOperation.class, new JerboaRuleOperationDeserializer(modeler));
    builder.registerTypeAdapter(PersistentName.class, new PersistentNameDeserializer());

    Application[] applications = builder.create().fromJson(reader, Application[].class);
    ParametricSpecification parametricSpecification =
        new ParametricSpecification(Arrays.asList(applications));

    return parametricSpecification;
  }

  /**
   * Import a {@link ParametricSpecification} from JSON
   *
   * @param directory
   * @param filename
   * @param modeler
   * @return
   * @throws IOException
   * @throws JerboaException
   */
  public static ParametricSpecification importParametricSpecification(
      String directory, String filename, ModelerGenerated modeler)
      throws IOException, JerboaException {
    JsonReader reader = new JsonReader(new FileReader(directory + "/" + filename));

    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapter(
        JerboaRuleOperation.class, new JerboaRuleOperationDeserializer(modeler));
    builder.registerTypeAdapter(PersistentName.class, new PersistentNameDeserializer());

    Application[] applications = builder.create().fromJson(reader, Application[].class);
    ParametricSpecification parametricSpecification =
        new ParametricSpecification(Arrays.asList(applications));

    return parametricSpecification;
  }
  
  
  /**
   * Import a {@link ParametricSpecification} from JSON
   *
   * @param file
   * @param modeler
   * @return
   * @throws IOException
   * @throws JerboaException
   */
  public static ParametricSpecification importParametricSpecification(
      File file, ModelerGenerated modeler)
      throws IOException, JerboaException {
    JsonReader reader = new JsonReader(new FileReader(file));

    GsonBuilder builder = new GsonBuilder();

    builder.registerTypeAdapter(
        JerboaRuleOperation.class, new JerboaRuleOperationDeserializer(modeler));
    builder.registerTypeAdapter(PersistentName.class, new PersistentNameDeserializer());

    Application[] applications = builder.create().fromJson(reader, Application[].class);
    ParametricSpecification parametricSpecification =
        new ParametricSpecification(Arrays.asList(applications));

    return parametricSpecification;
  }
  

  /**
   * Export a {@link MatchingTree} to JSON
   *
   * @param leaves
   * @param fileName
   * @throws IOException
   */
  public static void exportMatchingTree(List<List<LevelEventMT>> leaves, String fileName)
      throws IOException {
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    String json = builder.create().toJson(leaves);
    Files.write(Path.of(exportsPath + "/" + fileName), json.getBytes(), StandardOpenOption.CREATE);
  }

  /**
   * Export a {@link ReevaluationTree} to JSON
   *
   * @param tree
   * @param directory
   * @param fileName
   * @throws IOException
   */
  public static void exportReevaluationTree(
      List<List<LevelEventMT>> tree, String directory, String fileName) throws IOException {
    //
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    String json = builder.create().toJson(tree);
    Files.write(Path.of(directory + "/" + fileName), json.getBytes(), StandardOpenOption.CREATE);
  }
}
