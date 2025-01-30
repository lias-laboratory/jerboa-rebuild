package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SESpecsSelector} on user clicks on "new"
 *         button
 */
public class NewSpecificationActionListener implements ActionListener {

	private final SESpecsSelector spec_selector;

	/**
	 * Constructor of {@link NewSpecificationActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that trigger the listener
	 */
	public NewSpecificationActionListener(SESpecsSelector spec_selector) {
		this.spec_selector = spec_selector;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				String name = SESpecsSelector.NEW_SPEC_NAME_PREFIX + (int) (spec_selector.getNbOfSpecification());

				ParametricSpecification new_ps = new ParametricSpecification(name,
						spec_selector.getInitialSpecification().getApplications(),
						spec_selector.getInitialSpecification().getNextApplicationNumber(), false);

				spec_selector.addSpec(new_ps);
			};
		};
		t.start();

	}

}
