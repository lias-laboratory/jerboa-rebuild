package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ensma.lias.jerboa.SpecEditor.SEAppliedRule;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import up.jerboa.exception.JerboaException;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SEAppliedRule} on user clicks the
 *         "definitive delete" button
 */
public class DefinitiveDeleteAppActionListener implements ActionListener {

	private final SEAppliedRule app;
	private final ParametricSpecification spec;

	/**
	 * Constructor of {@link DefinitiveDeleteAppActionListener}
	 * 
	 * @param app  {@link SEAppliedRule} that trigger the listener
	 * @param spec {@link ParametricSpecification} used by the {@link SEAppliedRule}
	 */
	public DefinitiveDeleteAppActionListener(SEAppliedRule app, ParametricSpecification spec) {
		this.app = app;
		this.spec = spec;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread() {
			public void run() {
				try {
					spec.definitiveRemove(app.getRule());
				} catch (JerboaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.start();
	}
}
