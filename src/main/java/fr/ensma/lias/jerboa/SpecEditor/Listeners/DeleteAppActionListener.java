package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import fr.ensma.lias.jerboa.SpecEditor.SEAppliedRule;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import up.jerboa.exception.JerboaException;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SEAppliedRule} on user clicks the "delete"
 *         checkbox
 */
public class DeleteAppActionListener implements ActionListener {

	private final SEAppliedRule app;
	private final JCheckBox checkbox;

	/**
	 * Constructor of {@link DeleteAppActionListener}
	 * 
	 * @param app  {@link SEAppliedRule} that trigger the listener
	 * @param spec {@link JCheckBox} source of the initial trigger
	 */
	public DeleteAppActionListener(SEAppliedRule app, JCheckBox checkbox) {
		this.app = app;
		this.checkbox = checkbox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				boolean isDeleted = !checkbox.isSelected();

				try {
					if (isDeleted) {
						app.getSpec().updateApplicationType(app.getRule(), ApplicationType.DELETE);
					} else {
						app.getSpec().updateApplicationType(app.getRule(), ApplicationType.INIT);
					}
				} catch (JerboaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				app.updateEnables();
			};
		};
		t.start();
	}

}
