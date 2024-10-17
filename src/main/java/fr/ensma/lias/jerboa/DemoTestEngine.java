package fr.ensma.lias.jerboa;

import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.engine.RebuildEngine;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import up.jerboa.exception.JerboaException;

public class DemoTestEngine {

  public static void main(String[] args)
      throws JerboaException, IOException, InterruptedException, InvocationTargetException {

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

    RebuildEngine demo =
        new RebuildEngine(
            modeler, //
            "./examples", //
            "exemple-if-in-for-eval.json", //
            "exemple-if-in-for-eval.json", //
            false);

    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            frame.invalidate();
            frame.repaint(1000);
            gmapviewer.updateIHM();
          }
        });

    demo.initializeReevaluation();
    demo.reevaluate(bridge.getGMap(), gmapviewer);
    demo.exportTrees();
  }
}
