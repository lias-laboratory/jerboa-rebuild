package fr.ensma.lias.jerboa.SpecEditor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * {@link JPanel} use to make dialog box with a string answer
 * 
 * @author Tom BOIREAU
 */
public class SEQuestionDialog extends JPanel {

	private static final long serialVersionUID = -5235539869306443323L;
	
	// Component
	private JTextField field;

	/**
	 * Construct a specific panel with a question
	 * 
	 * @param question Question show to user
	 */
	public SEQuestionDialog(String question) {

		// Text field is use to get the answer of the user
		this.field = new JTextField(20);

		// The label to show the question
		JLabel lbl = new JLabel(question);

		// Add components to the panel
		super.add(lbl);
		super.add(field);
	}

	/**
	 * @return The answer of the user
	 */
	public String getAnswer() {
		return this.field.getText();
	}
}