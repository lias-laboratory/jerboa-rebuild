package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.jogamp.newt.util.MainThread;

import fr.ensma.lias.jerboa.SpecEditor.Listeners.ChangeSpecificationNameActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.ChangeSpecificationSelectionActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.ImportInitialSpecificationActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.NewInitialSpecificationActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.NewSpecificationActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.LoadReevSpecificationActionListener;
import fr.ensma.lias.jerboa.SpecEditor.Listeners.SaveSpecificationActionListener;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;

/**
 * @author Tom BOIREAU
 * 
 *         Represents the {@link ParametricSpecification} selector
 */
public class SESpecsSelector extends JPanel {

	private static final long serialVersionUID = 199851214914656586L;

	/**
	 * Prefix use for a new reevaluated {@link ParametricSpecification}
	 */
	public static final String NEW_SPEC_NAME_PREFIX = "Reevaluation ";

	// Attribute
	private final SEPanel parent;
	private ParametricSpecification initialSpec;
	private final ChangeSpecificationSelectionActionListener selectionListener;
	private LinkedList<ParametricSpecification> specs_list;

	// Component
	private final JComboBox<String> cmb_bx;

	/**
	 * Constructor of {@link SESpecsSelector}
	 * 
	 * @param parent      {@link SEPanel}, parent of the component
	 * @param initialSpec The initial {@link ParametricSpecification} used for the
	 *                    reevaluation
	 */
	public SESpecsSelector(SEPanel parent, ParametricSpecification initialSpec) {
		super(new BorderLayout());
		super.setBorder(BorderFactory.createTitledBorder("Specifications Selector"));

		// Define the menubar
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem newInitSpec = new JMenuItem("New Initial Specification");
		newInitSpec.setMnemonic('N');
		newInitSpec.addActionListener(new NewInitialSpecificationActionListener(this));
		
		JMenuItem newReevSpec = new JMenuItem("Create Reevaluation Specification");
		newReevSpec.setMnemonic('C');
		newReevSpec.addActionListener(new NewSpecificationActionListener(this));

		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic('S');
		save.addActionListener(new SaveSpecificationActionListener(this));

		JMenuItem importInit = new JMenuItem("Import Initial Specification");
		importInit.setMnemonic('I');
		importInit.addActionListener(new ImportInitialSpecificationActionListener(this, parent)); //TODO
		
		JMenuItem loadReev = new JMenuItem("Load Reevaluation Specification");
		loadReev.setMnemonic('L');
		loadReev.addActionListener(new LoadReevSpecificationActionListener(this, parent));

		JMenuItem changeName = new JMenuItem("Rename Current Specification");
		changeName.setMnemonic('R');
		changeName.addActionListener(new ChangeSpecificationNameActionListener(this));

		fileMenu.add(newInitSpec);
		fileMenu.addSeparator();
		fileMenu.add(newReevSpec);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(importInit);
		fileMenu.add(loadReev);
		fileMenu.addSeparator();
		fileMenu.add(changeName);

		menuBar.add(fileMenu);
		
		// Define attribute
		this.parent = parent;
		this.initialSpec = initialSpec;
		this.specs_list = new LinkedList<ParametricSpecification>();
		
		// We need to store the listener be able disable listening and re-enable it !
		this.selectionListener = new ChangeSpecificationSelectionActionListener(this, this.parent);
		
		// Define the component
		this.cmb_bx = new JComboBox<String>();
		this.cmb_bx.setEnabled(false);
		this.cmb_bx.addActionListener(this.selectionListener);

		// Define the specification as initial
		this.initialSpec.setInitialSpec(true);

		// Add the initial specification to the combobox
		this.cmb_bx.addItem(initialSpec.getDisplayName());

		// Add manually the initial specification
		this.specs_list.add(this.initialSpec);

		// Add al components
		super.add(menuBar, BorderLayout.NORTH);
		super.add(this.cmb_bx, BorderLayout.CENTER);
	}

	/**
	 * @return The selected {@link ParametricSpecification}
	 */
	public ParametricSpecification getSelectedSpecification() {

		int index = cmb_bx.getSelectedIndex();

		if (index >= 0 && index < getNbOfSpecification())
			return specs_list.get(index);

		return this.initialSpec;
	}

	/**
	 * Update all names of the ComboBox
	 */
	public void updateComboBox() {

		// Disable the listener
		this.cmb_bx.removeActionListener(this.selectionListener);

		// Save the current index
		int index = cmb_bx.getSelectedIndex();

		// Reset all items
		this.cmb_bx.removeAllItems();

		// Add all items
		for (ParametricSpecification spec : specs_list)
			this.cmb_bx.addItem(spec.getDisplayName());

		// Reset the selection
		this.cmb_bx.setSelectedIndex(index);

		// Re-enable the listener
		this.cmb_bx.addActionListener(this.selectionListener);

	}

	/**
	 * Add {@link ParametricSpecification} to the selector
	 * 
	 * @param param_spec
	 */
	public void addSpec(ParametricSpecification param_spec) {

		// Add to the specification
		this.specs_list.add(param_spec);

		// Add the item
		this.cmb_bx.addItem(param_spec.getDisplayName());

		// Enable combobox after the initial spec
		if (!this.cmb_bx.isEnabled())
			this.cmb_bx.setEnabled(true);

		// Set the selection correctly
		this.cmb_bx.setSelectedIndex(this.cmb_bx.getItemCount() - 1);
	}

	/**
	 * @return The initial {@link ParametricSpecification}
	 */
	public ParametricSpecification getInitialSpecification() {
		return this.initialSpec;
	}

	/**
	 * @return The number of {@link ParametricSpecification} available
	 */
	public int getNbOfSpecification() {
		return specs_list.size();
	}

	/**
	 * Clear all Specifications and create new initial specification
	 * 
	 * May erase unsaved parametric specification
	 */
	public void clearAllAndNewInitSpec() {

		this.cmb_bx.removeActionListener(this.selectionListener);

		this.initialSpec = new ParametricSpecification();

		this.specs_list.clear();

		this.cmb_bx.removeAllItems();

		this.addSpec(this.initialSpec);

		this.cmb_bx.setEnabled(false);

		this.parent.getSpecEditor().showParametricSpecification(this.initialSpec);
		
		this.parent.updateViewer(this.initialSpec);

		this.parent.getModeler().spec = this.initialSpec;
		
		this.parent.getGmapviewer().updateIHM();

		this.cmb_bx.addActionListener(this.selectionListener);
	}
	
	/**
	 * Clear all Specifications and create new initial specification
	 * 
	 * May erase unsaved parametric specification
	 */
	public void clearAllAndSetInitSpec(ParametricSpecification ps) {

		this.cmb_bx.removeActionListener(this.selectionListener);

		this.initialSpec = ps;

		this.specs_list.clear();

		this.cmb_bx.removeAllItems();

		this.addSpec(this.initialSpec);

		this.cmb_bx.setEnabled(false);

		this.parent.getSpecEditor().showParametricSpecification(this.initialSpec);
		
		this.parent.updateViewer(this.initialSpec);

		this.parent.getModeler().spec = this.initialSpec;
		
		this.parent.getGmapviewer().updateIHM();

		this.cmb_bx.addActionListener(this.selectionListener);
	}
}
