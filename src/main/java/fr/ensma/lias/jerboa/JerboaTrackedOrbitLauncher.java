package fr.ensma.lias.jerboa;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.ensma.lias.jerboa.bridge.JerboaBridgeDynaOrTracking;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaModelerDynOrTrack;
import fr.ensma.lias.jerboa.tracking.rule.rules.RawDynaOrTrackModeler;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.exception.JerboaException;

public class JerboaTrackedOrbitLauncher {

	public static void main(String[] args) throws JerboaException {

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);

		// RawDynaOrTrackModeler modeler = new RawDynaOrTrackModeler();
		ModelerGenerated modeler = new ModelerGenerated();
		JerboaModelerDynOrTrack modtrack = new JerboaModelerDynOrTrack(modeler, JerboaOrbit.orbit(0,1), JerboaOrbit.orbit(0,1,2,3));

		JerboaBridgeDynaOrTracking bridge = new JerboaBridgeDynaOrTracking(modtrack);
		GMapViewer gmapviewer = new GMapViewer(frame, modtrack, bridge);

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
