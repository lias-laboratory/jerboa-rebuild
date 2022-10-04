package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaInputHooksGeneric;
import up.jerboa.exception.JerboaException;

public class Demo {

	public static void main(String[] args) throws JerboaException {

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);

		ModelerGenerated modeler = new ModelerGenerated();

		JerboaRebuiltBridge bridge = new JerboaRebuiltBridge(modeler);
		GMapViewer gmapviewer = new GMapViewer(frame, modeler, bridge);

		frame.getContentPane().add(gmapviewer);
		frame.setSize(1024, 768);
		frame.pack();

		frame.setPreferredSize(new Dimension(1024, 768));

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// manually rebuild model described in spec case3
		var gmap = bridge.getGMap(); // gmap in which we rebuild the model

		// register rules to be used
		JerboaRebuiltRule pentagon = (JerboaRebuiltRule) modeler.getRule("CreatePentagon");
		// JerboaRebuiltRule triangulateFace = (JerboaRebuiltRule)
		// modeler.getRule("FaceTriangulation");

		// =================================================== First Spec Entry :
		// createPentagon()

		// new JerboaInputHooksGeneric() is required even though there
		// is no topological parameter
		JerboaRuleResult PentagonAppResult =
				pentagon.applyRule(gmap, new JerboaInputHooksGeneric());

		// get information on dart to find
		String nodeName = "n6"; // in a levelOrbitHR we already have it

		// get dart for next rule application
		int nodeIndex = pentagon.getRightIndexRuleNode(nodeName);


		// ========================================= Second Spec Entry :
		// InsertVertexFolded(NID(6))

		List<JerboaDart> currentDart = PentagonAppResult.get(nodeIndex);

		// Insert a vertex on edge identified by node n6
		JerboaRebuiltRule insertVertex =
				(JerboaRebuiltRule) modeler.getRule("InsertVertexFolded");
		JerboaRuleResult insertVertexAppResult = insertVertex.applyRule(gmap,
				new JerboaInputHooksGeneric(Arrays.asList(currentDart)));

		// ===========================================Third Spec Entry : InsertEdge(NID(10),
		// NID(0))
		// First Parameter NID(10)
		nodeName = "n1";

		int colIndex = insertVertex.getRightIndexRuleNode(nodeName);
		int rowIndex = -1;
		JerboaDart previousDart = currentDart.get(0);
		for (int i = 0; i < insertVertexAppResult.sizeCol(); i++) {
			for (int j = 0; j < insertVertexAppResult.sizeRow(i); j++) {
				if (insertVertexAppResult.get(i, j) == previousDart)
					rowIndex = j;
			}
		}

		if (rowIndex == -1) {
			rowIndex = 0;
			System.out.println(
					"Manually secured row index in THIS case. It should not be -1");
		}

		List<JerboaDart> firstParameter =
				Arrays.asList(insertVertexAppResult.get(colIndex, rowIndex));

		// Second Parameter NID(0)
		nodeName = "n0";

		nodeIndex = pentagon.getRightIndexRuleNode(nodeName);

		List<JerboaDart> secondParameter = PentagonAppResult.get(nodeIndex);

		// Apply third rule
		JerboaRebuiltRule insertEdge = (JerboaRebuiltRule) modeler.getRule("InsertEdge");
		insertEdge.applyRule(gmap, new JerboaInputHooksGeneric(
				Arrays.asList(firstParameter, secondParameter)));

		// JerboaRuleResult insertEdgeAppResult = insertEdge.applyRule(gmap,
		// new JerboaInputHooksGeneric(Arrays.asList(firstParameter, secondParameter)));

		// find row of previous dart in col[0]
		// int prevDartIndex;

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.invalidate();
				frame.repaint(1000);
				gmapviewer.updateIHM();
			}
		});

	}

}
