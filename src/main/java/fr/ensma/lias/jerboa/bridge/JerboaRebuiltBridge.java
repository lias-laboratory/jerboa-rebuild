package fr.ensma.lias.jerboa.bridge;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaMonitorInfo;
import fr.up.xlim.sic.ig.jerboa.viewer.IJerboaModelerViewer;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerBridge;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerColor;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerPoint;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.GMapViewerTuple;
import fr.up.xlim.sic.ig.jerboa.viewer.tools.JerboaMonitorInfoBridgeSerializerMonitor;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaGMapArray;
import up.jerboa.core.JerboaModeler;
import up.jerboa.core.util.JerboaGMapDuplicateFactory;
import up.jerboa.core.util.Pair;
import up.jerboa.exception.JerboaException;
import up.jerboa.exception.JerboaGMapDuplicateException;
import up.jerboa.util.JerboaSerializerMonitor;
import up.jerboa.util.serialization.JerboaSerializeException;
import up.jerboa.util.serialization.jba.JBAFormat;

public class JerboaRebuiltBridge implements GMapViewerBridge, JerboaGMapDuplicateFactory {

	private static final GMapViewerColor lightGRAY = new GMapViewerColor(Color.LIGHT_GRAY);


	private ModelerGenerated modeler;
	// private JerboaEmbeddingInfo ebdColor;
	private int ebdColorID;
	private int ebdNormID;


	private IJerboaModelerViewer viewer;

	public JerboaRebuiltBridge(ModelerGenerated modeler) {
		this.modeler = modeler;
		this.ebdColorID = modeler.getColor().getID();
		this.ebdNormID = modeler.getNorm().getID();
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
	public boolean manageEmbedding(JerboaEmbeddingInfo ebdinfo) {
		switch (ebdinfo.getName()) {
			case "point":
				return true;
			case "pos":
				return true;
			case "position":
				return true;
			case "normal":
				return true;
		}
		// if("point".equals(ebdinfo.getName()) || "pos".equals(ebdinfo.getName()) ||
		// "position".equals(ebdinfo.getName())) {
		// return true;
		// }
		return false;
	}

	@Override
	public boolean canUndo() {
		return true;
	}


	@Override
	public GMapViewerColor colors(JerboaDart arg0) {

		GMapViewerColor color = lightGRAY;
		if (arg0.ebd(ebdColorID) != null) {
			Color c = (Color) arg0.ebd(ebdColorID);
			color = new GMapViewerColor(c);
		}
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
		return true;
	}

	@Override
	public boolean hasOrient() {
		return false;
	}

	@Override
	public void load(IJerboaModelerViewer arg0, JerboaMonitorInfo worker) {
		System.err.println("LANCMENT OUVERTURE FICHIER!");
		JFileChooser filec = new JFileChooser(".");

		FileFilter jbafilter = new FileNameExtensionFilter("JBA format (*.jba)", "jba");
		filec.addChoosableFileFilter(jbafilter);

		if (filec.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = filec.getSelectedFile();

			JerboaSerializerMonitor monitor = new JerboaMonitorInfoBridgeSerializerMonitor(worker);
			System.out.println("LOAD FILE: " + file);
			JBAFormat formatJBA =
					new JBAFormat(modeler, monitor, new JerboaRebuiltEbdSerializer(modeler));
			try (FileInputStream fis = new FileInputStream(file)) {
				formatJBA.load(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JerboaSerializeException e) {
				e.printStackTrace();
			} catch (JerboaException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public GMapViewerTuple normals(JerboaDart dart) {
		Vec3 n = dart.<Vec3>ebd(ebdNormID);
		if (n == null) {
			n = new Vec3();
		}
		GMapViewerPoint res = new GMapViewerPoint((float) n.x(), (float) n.y(), (float) n.z());
		return res;
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
		}
		return false; // la commande n'est pas gérée
	}

	@Override
	public void save(IJerboaModelerViewer arg0, JerboaMonitorInfo arg1) {
		System.err.println("SAVING FILE");
		JFileChooser file = new JFileChooser(new File("."));
		file.showSaveDialog(null);
		String filename = file.getSelectedFile().toString();
		modeler.spec.export(filename);
	}

	// petit fix pour nouvelle version de Hakim
	public void loadFile(String filepath) {
		throw new Error("not yet implemented");
	}

	@Override
	public IJerboaModelerViewer getViewer() {
		return this.viewer;
	}

	@Override
	public void setViewer(IJerboaModelerViewer viewer) {
		this.viewer = viewer;		
	}
}
