package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.ensma.lias.jerboa.datastructures.Application;

/**
 * Represents a property of an {@link Application}
 * 
 * @author Tom BOIREAU
 * @param <T> Type of the value to print
 */
public class SEProperty<T> extends JPanel {

	private static final long serialVersionUID = -7829620283146593971L;

	// Component use in the panel
	private final JLabel label;
	private final JTextField textField;

	/**
	 * Constructor of {@link SEProperty}
	 * 
	 * @param prop_name label used for the property
	 */
	public SEProperty(String prop_name) {
		// Define the border layout
		super(new BorderLayout());

		// Define sub-components
		this.label = new JLabel(prop_name);
		this.textField = new JTextField();

		// Disable edition for the text field of the value
		this.textField.setEditable(false);

		// Add all components to the panel
		super.add(this.label, BorderLayout.WEST);
		super.add(this.textField, BorderLayout.CENTER);
	}

	/**
	 * Set the value of the property
	 * 
	 * @param val Value of the property
	 */
	public void setValue(T val) {
		this.textField.setText(val == null ? null : val.toString());
	}
}
