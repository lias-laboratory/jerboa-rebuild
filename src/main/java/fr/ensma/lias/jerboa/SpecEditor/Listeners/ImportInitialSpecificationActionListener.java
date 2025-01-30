package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import fr.ensma.lias.jerboa.SpecEditor.SEPanel;
import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.core.utils.printer.JSONPrinter;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import up.jerboa.exception.JerboaException;

/**
 * @author Victor LAURIN
 * 
 *         Listener trigger by {@link SESpecsSelector} on user clicks on "load"
 *         button
 */
public class ImportInitialSpecificationActionListener implements ActionListener {

	private static final String NAME = "Warning, plese save your parametrics specifications";
	private static final String LABEL = "Please note, this action will delete all unsaved parametric specifications.";
	
	private final SESpecsSelector spec_selector;
	private final SEPanel parent;

	/**
	 * Constructor of {@link ImportInitialSpecificationActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that trigger the listener
	 */
	public ImportInitialSpecificationActionListener(SESpecsSelector spec_selector, SEPanel parent) {
		this.spec_selector = spec_selector;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				// Warning
				JLabel lbl = new JLabel(LABEL);

				int res = JOptionPane.showConfirmDialog(null, lbl, NAME, JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (res != JOptionPane.OK_OPTION) {
					return;
				}
				
				// File selection
				JFileChooser chooser = new JFileChooser(new File("."));
				int retour = chooser.showOpenDialog(null);
				if (retour == JFileChooser.APPROVE_OPTION) {
					try {
						File file = chooser.getSelectedFile();
						ParametricSpecification newspec = JSONPrinter.importParametricSpecification(file,
								parent.getModeler());

						newspec.setDisplayName(file.getName());
						newspec.setInitialSpec(true);
						
						spec_selector.clearAllAndSetInitSpec(newspec);
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
