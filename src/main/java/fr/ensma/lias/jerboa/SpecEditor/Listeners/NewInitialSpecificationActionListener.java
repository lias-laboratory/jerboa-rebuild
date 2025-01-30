package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import fr.ensma.lias.jerboa.SpecEditor.SEQuestionDialog;
import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by the new initial spec button in the
 *         {@link SESpecsSelector}
 */
public class NewInitialSpecificationActionListener implements ActionListener {

	private static final String NAME = "Warning, plese save your parametrics specifications";
	private static final String LABEL = "Please note, this action will delete all unsaved parametric specifications.";

	private final SESpecsSelector spec_selector;

	/**
	 * Constructor of the {@link NewInitialSpecificationActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that will trigger the listener
	 * @param parent        SEPanel, parent of {@link SESpecsSelector}
	 */
	public NewInitialSpecificationActionListener(SESpecsSelector spec_selector) {
		this.spec_selector = spec_selector;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				ParametricSpecification spec = spec_selector.getSelectedSpecification();

				if (spec == null)
					return;

				JLabel lbl = new JLabel(LABEL);

				int res = JOptionPane.showConfirmDialog(null, lbl, NAME, JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (res == JOptionPane.OK_OPTION) {
					spec_selector.clearAllAndNewInitSpec();
				}

				return;
			};
		};
		t.start();

	}

}
