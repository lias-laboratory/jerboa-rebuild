package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.embeddings.OrbitLabel;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

public class JerboaLauncher {

    public static void main(String[] args) throws JerboaException {

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);

        JerboaRebuiltModeler modeler = new JerboaRebuiltModeler();

        /*
         * Si je l'ajoute ici, je dois tout de même l'enregistrer depuis registerEbdsAndResetGMAP
         * dans JerboaRebuilt. Si je le fais depuis JerboaRebuiltModeler (mon wrapper qui doit
         * «étendre» JerboaRebuilt) => Embedding seems shared between many modelers Si je n'utilise
         * pas registerEbdsAndResetGMAP le plongement reste initialisé à -1 -> OOB
         */

        JerboaRebuiltBridge bridge = new JerboaRebuiltBridge(modeler);
        GMapViewer gmapviewer = new GMapViewer(frame, modeler, bridge);

        frame.getContentPane().add(gmapviewer);
        frame.setSize(1024, 768);
        frame.pack();

        frame.setPreferredSize(new Dimension(1024, 768));

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

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
