/**
 * 
 */
package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import fr.ensma.lias.jerboa.SpecEditor.Listeners.DefinitiveDeleteAppActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.DeleteAppActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.MoveAppActionListener;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Victor LAURIN
 * 
 * 			Represents the base of an applied rule on the GUI
 */
public abstract class SEAppliedRuleBase extends JPanel implements MouseListener, ActionListener {

	/* ATTRIBUTES */
	protected static final long serialVersionUID = -3822304813737458954L;
	
	// Components used by the class
	protected final JRadioButton toggleBtn;
	protected final JLabel lblName;
	
	// Parent and specification
	protected final SESpecEditor spec_editor;
	protected final ParametricSpecification spec;
	
	/* CONSTRUCTORS */
	/**
	 * Constructor for {@link SEAppliedRuleBase}
	 * 
	 * @param spec_editor {@link SEAppliedRuleBase} that use this {@link SEAppliedRuleBase}
	 *                    instance
	 * @param spec        {@link ParametricSpecification} of the
	 *                    {@link SEAppliedRuleBase}
	 */
	public SEAppliedRuleBase(SESpecEditor spec_editor, ParametricSpecification spec, String labelName) {
		// BorderLayout with h-gap of 10
		super(new BorderLayout(10, 0));

		// Define the borderline
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
				
		// Define maximum size of the component
		this.setMaximumSize(new Dimension(380, 30));

		// Construct all components used in the class
		this.toggleBtn = new JRadioButton();
		this.lblName = new JLabel(labelName);

		// Define attributes
		this.spec_editor = spec_editor;
		this.spec = spec;
		
		// Listener to select the application
		this.toggleBtn.addActionListener(this);
		this.lblName.addMouseListener(this);
		super.addMouseListener(this);
				
		// Finally add the toggle button to the final panel
		super.add(this.toggleBtn, BorderLayout.WEST);
	}
	
	/* METHODS */
	/**
	 * @return The {@link ParametricSpecification} used by the application
	 */
	public ParametricSpecification getSpec() {
		return spec;
	}

	/**
	 * @return {@link JToggleButton} use for the selection
	 */
	public JToggleButton getToggle() {
		return this.toggleBtn;
	}

	/**
	 * Use this method to select this {@link Application}
	 */
	public abstract void select();

	@Override
	public void mouseClicked(MouseEvent e) {
		// select();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		select();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		select();
	}
}