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
import javax.swing.SwingUtilities;

import fr.ensma.lias.jerboa.SpecEditor.Listeners.DefinitiveDeleteAppActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.DeleteAppActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.MoveAppActionListener;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Represents an applied rule on the GUI
 */
public class SEAppliedRule extends SEAppliedRuleBase implements MouseListener, ActionListener {

	private static final long serialVersionUID = 5541266792334790759L;
	
	// Components used by the class 
	private final JCheckBox chckBoxDelete;
	private final JButton btnDelAdded;
	private final JButton upBtn;
	private final JButton downBtn;

	// Rule
	private final Application rule;

	/**
	 * Constructor for {@link SEAppliedRule}
	 * 
	 * @param spec_editor {@link SEAppliedRule} that use this {@link SEAppliedRule}
	 *                    instance
	 * @param spec        {@link ParametricSpecification} of the
	 *                    {@link SEAppliedRule}
	 * @param rule        {@link Application} which represents the
	 *                    {@link SEAppliedRule}
	 */
	public SEAppliedRule(SESpecEditor spec_editor, ParametricSpecification spec, Application rule) {
		super(spec_editor, spec, 
				rule.getApplicationID() + " | " + rule.getRule().getName());
		
		// Construct all components used in the class
		this.chckBoxDelete = new JCheckBox();
		this.btnDelAdded = new JButton("X");
		this.btnDelAdded.setSize(15, 15);
		this.upBtn = new JButton("˄");
		this.downBtn = new JButton("˅");

		// Define attributes
		this.rule = rule;

		// Set listener for a remove action
		this.chckBoxDelete.addActionListener(new DeleteAppActionListener(this, this.chckBoxDelete));
		this.btnDelAdded.addActionListener(new DefinitiveDeleteAppActionListener(this, this.spec));
		
		// Listener to move the application
		this.upBtn.addActionListener(new MoveAppActionListener(this, spec_editor, -1));
		this.downBtn.addActionListener(new MoveAppActionListener(this, spec_editor, 1));
		
		//Define panels before adding
			// Move actions panel
		JPanel moveAction = new JPanel();
		moveAction.setLayout(new BoxLayout(moveAction, BoxLayout.X_AXIS));
		moveAction.add(this.upBtn);
		moveAction.add(this.downBtn);

			// Center Panel
		JPanel centerPanel = new JPanel(new BorderLayout(0, 5));
		centerPanel.add(this.lblName, BorderLayout.CENTER);
		centerPanel.add(moveAction, BorderLayout.EAST);
		
			//Right Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));
		Dimension d = rightPanel.getPreferredSize();
		d.width = 50;
		rightPanel.setPreferredSize(d);
		rightPanel.add(Box.createHorizontalGlue());
		rightPanel.add(this.rule.getApplicationType() == ApplicationType.INIT 
				|| this.rule.getApplicationType() == ApplicationType.MOVE 
				|| this.rule.getApplicationType() == ApplicationType.DELETE 
				? this.chckBoxDelete : this.btnDelAdded);
		rightPanel.add(Box.createHorizontalGlue());
		
		// Define size of components
		this.btnDelAdded.setPreferredSize(new Dimension(45, 30));
		this.upBtn.setPreferredSize(new Dimension(45, 30));
		this.downBtn.setPreferredSize(new Dimension(45, 30));
		
		// Update all enabling of components
		updateEnables();

		// Finally add components to the final panel
		super.add(centerPanel, BorderLayout.CENTER);
		super.add(rightPanel, BorderLayout.EAST);
	}

	/**
	 * Method to update activation of the different elements of panel
	 */
	public void updateEnables() {
		// TODO: Simplify this method
		if (rule.getApplicationType() != ApplicationType.ADD) {
			this.btnDelAdded.setEnabled(false);
		} else {
			this.chckBoxDelete.setEnabled(false);
		}

		if (rule.getApplicationType() == ApplicationType.DELETE) {
			chckBoxDelete.setSelected(false);
			lblName.setEnabled(false);

		} else {
			chckBoxDelete.setSelected(true);
			lblName.setEnabled(true);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO: Simplify this method
		super.setEnabled(enabled);

		if (enabled && rule.getApplicationType() != ApplicationType.ADD) {
			this.btnDelAdded.setEnabled(false);
		} else if (enabled) {
			this.chckBoxDelete.setEnabled(false);
		} else {
			this.btnDelAdded.setEnabled(enabled);
			this.chckBoxDelete.setEnabled(enabled);
		}
		
		this.upBtn.setEnabled(enabled);
		this.downBtn.setEnabled(enabled);
	}

	/**
	 * @return The {@link Application} which represent the applied rule
	 */
	public Application getRule() {
		return rule;
	}
	
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
				spec_editor.parent.getPropsEditor().showApplication(rule);

				// We need to update the Jerboa viewer
				Application next = spec.getApplicationAfter(getRule().getApplicationID());
				spec_editor.updateViewer(next == null ? 0 : next.getApplicationID());
			}
		});
		/*
		// A selection isn't done on UI thread to prevent reevaluation lock
		Thread t = new Thread() {
			@Override
			public void run() {
				
				// Active the selection
				toggleBtn.setSelected(true);

				// We show properties of the application
				spec_editor.parent.getPropsEditor().showApplication(rule);

				// We need to update the Jerboa viewer
				Application next = spec.getApplicationAfter(getRule().getApplicationID());
				spec_editor.updateViewer(next == null ? 0 : next.getApplicationID());
			}
		};
		
		t.start();*/
	}
}