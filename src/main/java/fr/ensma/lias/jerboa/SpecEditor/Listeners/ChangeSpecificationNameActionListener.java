package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import fr.ensma.lias.jerboa.SpecEditor.SEQuestionDialog;
import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by the changes of name button in the
 *         {@link SESpecsSelector}
 */
public class ChangeSpecificationNameActionListener implements ActionListener {

	private static final String NAME = "Parametric specification name";
	private static final String QUESTION = "New parametric specification name :";

	private final SESpecsSelector spec_selector;

	/**
	 * Constructor of the {@link ChangeSpecificationNameActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that will trigger the listener
	 * @param parent        SEPanel, parent of {@link SESpecsSelector}
	 */
	public ChangeSpecificationNameActionListener(SESpecsSelector spec_selector) {
		this.spec_selector = spec_selector;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				ParametricSpecification spec = spec_selector.getSelectedSpecification();

				if (spec == null)
					return;

				SEQuestionDialog dialog = new SEQuestionDialog(QUESTION);
				int res = JOptionPane.showConfirmDialog(null, dialog, NAME, JOptionPane.OK_CANCEL_OPTION);
				if (res == JOptionPane.OK_OPTION) {
					String newname = dialog.getAnswer();

					spec.setDisplayName(newname);

					spec_selector.updateComboBox();
				}

				return;
			};
		};
		t.start();

	}

}
