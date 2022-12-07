package fr.ensma.lias.jerboa;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JDialog;
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

	/**
	 * ici j'ai mis le code de mon traitement qui peut prendre beaucoup de temps! tu verras dans la
	 * suite que ce traitement est lancee dans un autre thread
	 */
	public static void montraitement() {
		JerboaLongTaskWait longtask = new JerboaLongTaskWait();
		int i = 0;
		// je fais mon traitement
		boolean cont = true;
		do {

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			i++;
			System.out.println("Traitement jusqu'a i = " + i);
			cont = longtask.waitUI();

		} while (cont);
		System.out.println("Pouf je continue mon traitement");
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

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.invalidate();
				frame.repaint(1000);
				gmapviewer.updateIHM();
			}
		});

		// j'ai lance mon calcul dans un autre thread expres pour montrer
		// ce calcul long, histoire de garder la main pour les modifs
		Thread moncalculparallel = new Thread(new Runnable() {

			@Override
			public void run() {
				montraitement();
			}
		});
		moncalculparallel.start(); // je lance le traitement sur un autre thread
	}

}
