package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ensma.lias.jerboa.SpecEditor.SEAppliedRule;
import fr.ensma.lias.jerboa.SpecEditor.SESpecEditor;
import up.jerboa.exception.JerboaException;

/**
 * @author Tom BOIREAU
 * 
 *         Listener trigger by {@link SESpecEditor} on user clicks on button to
 *         move an AppliedRule
 */
public class MoveAppActionListener implements ActionListener {

	private final SEAppliedRule applied_rule;
	private final SESpecEditor spec_editor;
	private final int delta;

	/**
	 * Constructor of {@link MoveAppActionListener}
	 * 
	 * @param spec_editor {@link SESpecEditor} that trigger the lister
	 * @param delta       Offset of the move, can be negative
	 */
	public MoveAppActionListener(SEAppliedRule applied_rule, SESpecEditor spec_editor, int delta) {
		this.applied_rule = applied_rule;
		this.spec_editor = spec_editor;
		this.delta = delta;
	}
	
	public MoveAppActionListener(SESpecEditor spec_editor, int delta) {
		this(null, spec_editor, delta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Thread t = new Thread() {
			public void run() {
				int curr;
				try {
					curr = applied_rule != null ? spec_editor.getIndexOfApplication(applied_rule.getRule()) : spec_editor.getCurrentIndex();
					spec_editor.getCurrentSpecification().moveApplication(curr, curr + delta);
				} catch (JerboaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
		t.start();

	}

}
