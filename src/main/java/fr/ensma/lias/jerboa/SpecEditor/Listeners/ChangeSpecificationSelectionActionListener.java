package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ensma.lias.jerboa.SpecEditor.SEPanel;
import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by the changes of selection in the
 *         {@link SESpecsSelector}
 */
public class ChangeSpecificationSelectionActionListener implements ActionListener {

	private final SESpecsSelector spec_selector;
	private final SEPanel parent;

	/**
	 * Constructor of the {@link ChangeSpecificationSelectionActionListener}
	 * 
	 * @param spec_selector {@link SESpecsSelector} that will trigger the listener
	 * @param parent        SEPanel, parent of {@link SESpecsSelector}
	 */
	public ChangeSpecificationSelectionActionListener(SESpecsSelector spec_selector, SEPanel parent) {
		this.spec_selector = spec_selector;
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				ParametricSpecification spec = spec_selector.getSelectedSpecification();

				if (spec == null)
					return;

				parent.getSpecEditor().showParametricSpecification(spec);

				parent.getGmapviewer().clearDartSelection();
				parent.getBridge().getGMap().clear();

				parent.updateViewer(spec_selector.getInitialSpecification(), spec);

				parent.getModeler().spec = spec;

				return;
			};
		};
		t.start();

	}

}
