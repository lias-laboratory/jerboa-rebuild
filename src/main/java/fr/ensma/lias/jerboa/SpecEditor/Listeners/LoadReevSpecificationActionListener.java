package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import fr.ensma.lias.jerboa.SpecEditor.SEPanel;
import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import up.jerboa.exception.JerboaException;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SESpecsSelector} on user clicks on "load"
 *         button
 */
public class LoadReevSpecificationActionListener implements ActionListener {

	private final SESpecsSelector spec_selector;
	private final SEPanel parent;

	/**
	 * Constructor of {@link LoadReevSpecificationActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that trigger the listener
	 */
	public LoadReevSpecificationActionListener(SESpecsSelector spec_selector, SEPanel parent) {
		this.spec_selector = spec_selector;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				JFileChooser chooser = new JFileChooser(new File("."));
				int retour = chooser.showOpenDialog(null);
				if (retour == JFileChooser.APPROVE_OPTION) {
					try {
						File file = chooser.getSelectedFile();
						ParametricSpecification newspec = JSONPrinter.importParametricSpecification(file,
								parent.getModeler());

						newspec.setDisplayName(file.getName());

						spec_selector.addSpec(newspec);
					} catch (IOException | JerboaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		};
		t.start();

	}

}
