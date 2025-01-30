package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.ensma.lias.jerboa.SpecEditor.Listeners.MoveAppActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.ParametricSpecificationListener;
import fr.ensma.lias.jerboa.core.engine.RebuildEngine;
import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import up.jerboa.exception.JerboaException;

/**
 * @author Tom BOIREAU
 * 
 *         Represent {@link JPanel} with the {@link ParametricSpecification} to
 *         edit
 */
public class SESpecEditor extends JPanel implements ParametricSpecificationListener {

	private static final long serialVersionUID = -8826168535522766867L;

	/**
	 * {@link SEPanel}, parent of the component
	 */
	public final SEPanel parent;

	// Attributes
	private ParametricSpecification ps;
	private SEAppliedRuleEmpty emptySceneState;
	private ArrayList<SEAppliedRule> cmpnts;

	// Component
	private final JPanel paramPanel;
	
	// Button group is just use to ensure consistency of the selection 
	private ButtonGroup selectionGroup;

	/**
	 * Constructor of {@link SESpecEditor}
	 * 
	 * @param parent {@link SEPanel}, parent of the component
	 */
	public SESpecEditor(SEPanel parent) {
		super(new BorderLayout());
		super.setBorder(BorderFactory.createTitledBorder("Specification Editor"));
		
		// Define parent 
		this.parent = parent;

		// Define paramPanel, it will contains all SEAppliedRule
		this.paramPanel = new JPanel();
		this.paramPanel.setLayout(new BoxLayout(this.paramPanel, BoxLayout.Y_AXIS));

		// Initialize the list
		this.cmpnts = new ArrayList<SEAppliedRule>();
		
		// Border layout containing the empty scene's state and the others
		this.emptySceneState = new SEAppliedRuleEmpty(this, this.ps);
		JPanel jp = new JPanel(new BorderLayout());
		jp.add(this.emptySceneState, BorderLayout.NORTH);
		jp.add(this.paramPanel, BorderLayout.CENTER);
		
		// Add ScrollPanel to prevent the panel overflow
		JScrollPane scrollPane = new JScrollPane(jp);

		// Panel uses for the move buttons
		JPanel moveAction = new JPanel();
		moveAction.setLayout(new BoxLayout(moveAction, BoxLayout.X_AXIS));

		// Move buttons
		JButton upBtn = new JButton("˄");
		JButton downBtn = new JButton("˅");
		moveAction.add(upBtn);
		moveAction.add(downBtn);

		// Listener for move action
		upBtn.addActionListener(new MoveAppActionListener(this, -1));
		downBtn.addActionListener(new MoveAppActionListener(this, 1));

		// Add components
		super.add(scrollPane, BorderLayout.CENTER);
		super.add(moveAction, BorderLayout.SOUTH);
	}

	/**
	 * Show a {@link ParametricSpecification} to edit
	 * 
	 * @param ps {@link ParametricSpecification} to be displayed
	 */
	public void showParametricSpecification(ParametricSpecification ps) {

		// Begin to reset the selection group
		this.selectionGroup = new ButtonGroup();
		
		// Add the empty scene's state to the selection group
		this.selectionGroup.add(this.emptySceneState.getToggle());
		this.emptySceneState.getToggle().setSelected(true);

		// Avoid issue for the first showing
		if (this.ps != null)
			// Disable lister of the old specification
			this.ps.setListener(null);

		// Clear all SEAppliedRule
		this.cmpnts.clear();
		this.paramPanel.removeAll();

		// Save the new specification
		this.ps = ps;
		
		// For each application, we add it to the param panel
		for (Application app : this.ps.getApplications()) {
			
			// We don't use addApplication method to more optimization without index and repaint
			
			// Define the new SEAppliedRule
			SEAppliedRule sear = new SEAppliedRule(this, this.ps, app);
			sear.setSize(WIDTH - 10, 20);

			// Add the toggle to the group for the selection
			this.selectionGroup.add(sear.getToggle());

			// If it's an initial specification, we disable application
			sear.setEnabled(!this.ps.isInitialSpec());

			// Add component
			this.cmpnts.add(sear);
			this.paramPanel.add(sear);

			// Refresh selection and properties
			this.parent.getPropsEditor().showApplication(app);
			sear.getToggle().setSelected(true);
		}

		// Change the back color to make an indicator
		if (this.ps.isInitialSpec())
			super.setBackground(Color.lightGray);
		else
			super.setBackground(Color.cyan);

		// Add listener to the new specification
		this.ps.setListener(this);

		// Update the view
		this.paramPanel.revalidate();
		this.paramPanel.repaint();
	}
	
	private void addApplication(Application specEntry, int newpos) {
		
		// Define the new SEAppliedRule
		SEAppliedRule sear = new SEAppliedRule(this, this.ps, specEntry);
		sear.setSize(WIDTH - 10, 20);

		// Add the toggle to the group for the selection
		this.selectionGroup.add(sear.getToggle());

		// If it's an initial specification, we disable application
		sear.setEnabled(!this.ps.isInitialSpec());

		// Add component
		this.cmpnts.add(newpos, sear);
		this.paramPanel.add(sear, newpos);

		// Refresh selection and properties
		this.parent.getPropsEditor().showApplication(specEntry);
		sear.getToggle().setSelected(true);
		
		// Update the view
		this.paramPanel.revalidate();
		this.paramPanel.repaint();
	}

	@Override
	public void onInitApplication(Application specEntry, int newpos) {
		addApplication(specEntry, newpos);
	}

	@Override
	public void onAddApplication(Application specEntry, int newpos) {
		addApplication(specEntry, newpos);
	}

	@Override
	public void onDeleteApplication(Application specEntry, int newpos) {
		// Rien à faire
	}

	@Override
	public void onDefinitiveDeleteApplication(Application specEntry) {

		//TODO: à améliorer
		
		SEAppliedRule lst_cmp = null;
		boolean found = false;

		for (SEAppliedRule cmp : this.cmpnts) {
			if (cmp.getRule() == specEntry) {

				this.paramPanel.remove(cmp);
				this.cmpnts.remove(cmp);

				found = true;

				if (lst_cmp != null) {
					this.parent.getPropsEditor().showApplication(lst_cmp.getRule());
					lst_cmp.getToggle().setSelected(true);
				} else {
					continue;
				}

				this.paramPanel.revalidate();
				this.paramPanel.repaint();

				return;
			} else if (found) {
				this.parent.getPropsEditor().showApplication(cmp.getRule());
				cmp.getToggle().setSelected(true);

				this.paramPanel.revalidate();
				this.paramPanel.repaint();

				return;
			} else {
				lst_cmp = cmp;
			}
		}

	}

	@Override
	public void onMoveApplication(Application specEntry, int oldindex, int newindex) {

		// Search the right component
		for (SEAppliedRule cmp : this.cmpnts) {
			if (cmp.getRule() == specEntry) {

				// Remove it from the panel
				this.paramPanel.remove(cmp);
				
				// Add it to the right place
				this.paramPanel.add(cmp, newindex);

				// Update the view
				this.paramPanel.revalidate();
				this.paramPanel.repaint();

				return;
			}
		}
	}

	/**
	 * Update the 3D viewer using the {@link RebuildEngine}
	 * 
	 * @param id Parameter of the {@link RebuildEngine} for the Reevaluation
	 */
	public void updateViewer(int id) {

		// Get the specSelector to get specifications
		SESpecsSelector spec_selector = parent.getSpecSelector();
		ParametricSpecification init = spec_selector.getInitialSpecification();
		ParametricSpecification ps = spec_selector.getSelectedSpecification();

		// Avoid visual bugs
		if (ps.isInitialSpec())
			return;

		// Use parent methods to update the viewer
		parent.updateViewer(init, ps, id);
		parent.getModeler().spec = spec_selector.getSelectedSpecification();
	}

	/**
	 * @return The current {@link ParametricSpecification}
	 */
	public ParametricSpecification getCurrentSpecification() {
		return this.ps;
	}

	@Override
	public void update() throws JerboaException {
		// Call updateViewer with the applicationID of the next application
		Application next = getCurrentSpecification().getApplicationAfter(getCurrentApplication().getApplicationID());
		updateViewer(next == null ? 0 : next.getApplicationID());
	}

	@Override
	public Application getCurrentApplication() throws JerboaException {
		// TODO: A améliorer
		System.err.println("getCurrentApplication");
		
		// Search the right components
		for (Component cmp : this.paramPanel.getComponents()) {
			if (cmp instanceof SEAppliedRule) {
				SEAppliedRule appRule = (SEAppliedRule) cmp;
				if (appRule.getToggle().isSelected()) {
					
					// And return it
					return appRule.getRule();
				}
			} else {
				// TODO : Throwing a more accurate exception
				throw new JerboaException("SESpecEditor.getCurrentIndex > Invalid components Reached");
			}
		}
		return null;
	}
	
	
	/**
	 * Get the index of an Application, -1 if application is not contains in the spec
	 * 
	 * @param app {@link Application} to found
	 * @return The index of the application
	 * @throws JerboaException 
	 */
	public int getIndexOfApplication(Application app) throws JerboaException {
		// TODO: A améliorer
		System.err.println("getIndexOfApplication");
		
		int index = 0;
		// Search the right components
		for (Component cmp : this.paramPanel.getComponents()) {
			if (cmp instanceof SEAppliedRule) {
				SEAppliedRule appRule = (SEAppliedRule) cmp;
				if (appRule.getRule() == app) {
					
					// Return it
					return index;
				}
				// Else add index for next
				index++;
			} else {
				// TODO : Throwing a more accurate exception
				throw new JerboaException("SESpecEditor.getCurrentIndex > Invalid components Reached");
			}
		}
		return -1;
	}

	@Override
	public int getCurrentIndex() throws JerboaException {
		// TODO: A améliorer
		System.err.println("getCurrentIndex");

		// Search the right components
		int index = 0;
		for (Component cmp : this.paramPanel.getComponents()) {
			if (cmp instanceof SEAppliedRule) {
				SEAppliedRule appRule = (SEAppliedRule) cmp;
				if (appRule.getToggle().isSelected()) {
					
					// Return it
					return index;
				}
				index++;
			} else {
				// TODO : Throwing a more accurate exception
				throw new JerboaException("SESpecEditor.getCurrentIndex > Invalid components Reached");
			}
		}
		return -1;
	}

}
