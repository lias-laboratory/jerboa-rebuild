/**
 * 
 */
package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Victor LAURIN
 *
 */
public class SEAppliedRuleEmpty extends SEAppliedRuleBase {

	/* ATTRIBUTES */
	private static final long serialVersionUID = -3355916881129756060L;

	/* CONSTRUCTORS */
	public SEAppliedRuleEmpty(SESpecEditor spec_editor, ParametricSpecification spec) {
		super(spec_editor, spec, "--- [EMPTY SCENE] ---");
		
		//Define panels before adding
			// Center Panel
		JPanel centerPanel = new JPanel(new BorderLayout(0, 5));
		centerPanel.add(this.lblName, BorderLayout.CENTER);
		
		// Finally add components to the final panel
		super.add(centerPanel, BorderLayout.CENTER);
	}
	
	/* METHODS */
	/**
	 * Use this method to select this {@link Application}
	 */
	@Override
	public void select() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// Active the selection
				toggleBtn.setSelected(true);

				// We show properties of the application
				spec_editor.parent.getPropsEditor().showEmptyApplication();

				// We need to update the Jerboa viewer
				if (!spec_editor.getCurrentSpecification().isInitialSpec()) {
					spec_editor.parent.getBridge().getGMap().clear();
					spec_editor.parent.getGmapviewer().updateIHM();
				}
			}
		});
		
		/*// A selection isn't done on UI thread to prevent reevaluation lock
		Thread t = new Thread() {
			@Override
			public void run() {
				
				// Active the selection
				toggleBtn.setSelected(true);

				// We show properties of the application
				spec_editor.parent.getPropsEditor().showEmptyApplication();

				// We need to update the Jerboa viewer
				if (!spec_editor.getCurrentSpecification().isInitialSpec()) {
					spec_editor.parent.getBridge().getGMap().clear();
					spec_editor.parent.getGmapviewer().updateIHM();
				}
			}
		};
		
		t.start();
		
		*/
	}
}
