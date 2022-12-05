package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaMonitorInfo;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaProgressBar;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaTask;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;
import up.jerboa.exception.JerboaException;

public class Demo {

	public static void count(Semaphore s) {
		// Thread t = new Thread(new Runnable() {

		// @Override
		// public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("counter = " + i);
			// try {
			SwingUtilities.invokeLater(new Runnable() {
				// SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showConfirmDialog(null, "Continue?");
				}
			});
			// } catch (InvocationTargetException | InterruptedException e) {
			// e.printStackTrace();
			// }
			s.release();
		}

		// }
		// });
		// t.start();
	}

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

		final Semaphore s = new Semaphore(1);

		JerboaTask t = new JerboaTask() {
			@Override
			public void run(JerboaMonitorInfo info) {
				info.setMinMax(1, 1);
				count(s);
			}
		};

		new JerboaProgressBar(frame, "progressbar", "wait until done", t);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.invalidate();
				frame.repaint(1000);
				gmapviewer.updateIHM();
				try {
					s.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
