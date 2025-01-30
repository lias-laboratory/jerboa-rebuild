package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SESpecsSelector} on user clicks on "save"
 *         button
 */
public class SaveSpecificationActionListener implements ActionListener {

	private final SESpecsSelector spec_selector;

	/**
	 * Constructor of {@link SaveSpecificationActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that trigger the listener
	 */
	public SaveSpecificationActionListener(SESpecsSelector spec_selector) {
		this.spec_selector = spec_selector;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				JFileChooser chooser = new JFileChooser(new File("."));
				int retour = chooser.showOpenDialog(null);
				if (retour == JFileChooser.APPROVE_OPTION) {
					spec_selector.getSelectedSpecification().export(chooser.getSelectedFile().getAbsolutePath());
				}
			};
		};
		t.start();

	}

}
