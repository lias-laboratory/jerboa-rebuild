package fr.ensma.lias.jerboa.bridge;

import java.awt.Color;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaModelerDynOrTrack;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaMonitorInfo;
import fr.up.xlim.sic.ig.jerboa.viewer.IJerboaModelerViewer;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerBridge;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerColor;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerPoint;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerTuple;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaGMapArray;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.util.JerboaGMapDuplicateFactory;
import up.jerboa.core.util.Pair;
import up.jerboa.exception.JerboaGMapDuplicateException;

public class JerboaBridgeDynaOrTracking implements GMapViewerBridge, JerboaGMapDuplicateFactory {

	private JerboaModelerDynOrTrack modeler;
	private JerboaEmbeddingInfo ebdColor;
	private int ebdColorID;

	public JerboaBridgeDynaOrTracking(JerboaModelerDynOrTrack modeler) {
		this.modeler = modeler;
		this.ebdColor = modeler.getColor();
		this.ebdColorID = ebdColor.getID();
	}

	@Override
	public JerboaEmbeddingInfo convert(JerboaEmbeddingInfo info) {
		return info;
	}

	@Override
	public Object duplicate(JerboaEmbeddingInfo info, Object value) {
		return value;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Embeddings:\n");
		for (var ebd : modeler.getAllEmbedding()) {
			sb.append(ebd.toString()).append(" ID: ").append(ebd.getID()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean manageEmbedding(JerboaEmbeddingInfo arg0) {
		return false;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public GMapViewerColor colors(JerboaDart arg0) {

		Color c = Color.LIGHT_GRAY;
		if (arg0.ebd(ebdColorID) != null) {
			c = (Color) arg0.ebd(ebdColorID);
		}
		GMapViewerColor color = new GMapViewerColor(c);
		return color;
	}

	@Override
	public GMapViewerPoint coords(JerboaDart dart) {
		Vec3 v = dart.ebd("pos");
		if (v != null)
			return new GMapViewerPoint(v.x(), v.y(), v.z());
		else
			return new GMapViewerPoint(0, 0, 0);
	}

	@Override
	public JerboaGMap duplicate(JerboaGMap gmap) throws JerboaGMapDuplicateException {
		JerboaGMap res = new JerboaGMapArray(modeler, gmap.getCapacity());
		gmap.duplicateInGMap(res, this);
		return res;
	}

	@Override
	public List<Pair<String, String>> getCommandLineHelper() {
		ArrayList<Pair<String, String>> mescommandes = new ArrayList<>();
		mescommandes.add(new Pair<>("echo", " Renvoie le texte donné en argument dans la console"));
		return mescommandes;
	}

	@Override
	public JerboaGMap getGMap() {
		return modeler.getGMap();
	}

	@Override
	public JerboaModeler getModeler() {
		return modeler;
	}

	@Override
	public boolean getOrient(JerboaDart arg0) {
		return false;
	}

	@Override
	public boolean hasColor() {
		return true;
	}

	@Override
	public boolean hasNormal() {
		return false;
	}

	@Override
	public boolean hasOrient() {
		return false;
	}

	@Override
	public void load(IJerboaModelerViewer arg0, JerboaMonitorInfo arg1) {}

	@Override
	public GMapViewerTuple normals(JerboaDart arg0) {
		return null;
	}

	@Override
	public boolean parseCommandLine(PrintStream console, String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		String action = tokenizer.nextToken();
		switch (action) {
			case "echo": {
				String arg = line.substring(5);
				console.append("MA SORTIE: " + arg);
				return true; // la commande est gérée
			}
			case "exporttracking": {
				String filename = tokenizer.nextToken();
				String sorbitid = tokenizer.nextToken();
				modeler.exportTracking(filename, Integer.parseInt(sorbitid));
				return true;
			}
		}
		return false; // la commande n'est pas gérée
	}

	@Override
	public void save(IJerboaModelerViewer arg0, JerboaMonitorInfo arg1) {
		JFileChooser file = new JFileChooser(new File("."));
		file.showSaveDialog(null);
		String filename = file.getSelectedFile().toString();
	}

	// petit fix pour nouvelle version de Hakim
	public void loadFile(String filepath) {
		throw new Error("not yet implemented");
	}
}
