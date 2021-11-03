package fr.ensma.lias.jerboa.bridge;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.jerboa.JerboaRebuilt;
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

public class JerboaRebuiltBridge implements GMapViewerBridge, JerboaGMapDuplicateFactory {

	private JerboaRebuilt modeler;

	public JerboaRebuiltBridge(JerboaRebuilt modeler) {
		this.modeler = modeler;
	}

	@Override
	public JerboaEmbeddingInfo convert(JerboaEmbeddingInfo info) {
		return info;
	}

	@Override
	public Object duplicate(JerboaEmbeddingInfo info, Object value) {
		return value;
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
		return null;
	}

	@Override
	public GMapViewerPoint coords(JerboaDart dart) {
		Vec3 v = dart.ebd("pos");
		if(v != null)
			return new GMapViewerPoint(v.x(), v.y(), v.z());
		else
			return new GMapViewerPoint(0,0,0);
	}

	@Override
     	public JerboaGMap duplicate(JerboaGMap gmap) throws JerboaGMapDuplicateException {
        	JerboaGMap res = new JerboaGMapArray(modeler,gmap.getCapacity());
         	gmap.duplicateInGMap(res, this);
         	return res;
	}

	@Override
	public List<Pair<String, String>> getCommandLineHelper() {
		return new ArrayList<>();
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
		return false;
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
	public void load(IJerboaModelerViewer arg0, JerboaMonitorInfo arg1) {
	}

	@Override
	public GMapViewerTuple normals(JerboaDart arg0) {
		return null;
	}

	@Override
	public boolean parseCommandLine(PrintStream arg0, String arg1) {
		return false;
	}

	@Override
	public void save(IJerboaModelerViewer arg0, JerboaMonitorInfo arg1) {
	}
}
