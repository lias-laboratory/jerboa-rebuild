package fr.ensma.lias.jerboa.SpecEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.ensma.lias.jerboa.bridge.JerboaRebuiltBridge;
import fr.ensma.lias.jerboa.core.engine.RebuildEngine;
import fr.ensma.lias.jerboa.core.engine.UserStrategies;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import fr.up.xlim.sic.ig.jerboa.viewer.GMapViewer;

/**
 * @author Tom BOIREAU
 * 
 *         Spec editor default panel, the parent of all others 
 * 
 *         Stores all sub panels
 * 
 */
public class SEPanel extends JPanel {

	private static final long serialVersionUID = 6890692542416878223L;

	// Children
	private final SESpecsSelector spec_selector;
	private final SESpecEditor spec_editor;
	private final SEPropertiesEditor props_editor;
	
	// Attribute
	private final GMapViewer gmapviewer;
	private final JerboaRebuiltBridge bridge;
	private final ModelerGenerated modeler;

	/**
	 * Constructor of {@link SEPanel}
	 * 
	 * @param gmapviewer {@link GMapViewer} used by the main frame
	 * @param bridge     {@link JerboaRebuiltBridge} use for {@link RebuildEngine}
	 * @param modeler    {@link ModelerGenerated} modeler use by {@link GMapViewer}
	 */
	public SEPanel(GMapViewer gmapviewer, JerboaRebuiltBridge bridge, ModelerGenerated modeler) {
		
		// Border layout use in vertical
		super(new BorderLayout(5, 5));

		// Define all attributes
		this.gmapviewer = gmapviewer;
		this.bridge = bridge;
		this.modeler = modeler;

		// We get the initial specification to show it
		ParametricSpecification initialSpec = this.modeler.spec;

		// Define all children
		this.props_editor = new SEPropertiesEditor();
		this.spec_editor = new SESpecEditor(this);
		this.spec_selector = new SESpecsSelector(this, initialSpec);

		// Show the initial specification
		this.spec_editor.showParametricSpecification(initialSpec);

		// Add children to the panel
		super.add(this.spec_selector, BorderLayout.NORTH);
		super.add(this.spec_editor, BorderLayout.CENTER);
		super.add(this.props_editor, BorderLayout.SOUTH);

		// Define size of the panel
		Dimension d = super.getPreferredSize();
		d.width = 380;
		super.setPreferredSize(d);
	}

	/**
	 * @return The {@link SESpecsSelector} used in the panel
	 */
	public SESpecsSelector getSpecSelector() {
		return spec_selector;
	}

	/**
	 * @return The {@link SESpecEditor} used in the panel
	 */
	public SESpecEditor getSpecEditor() {
		return spec_editor;
	}

	/**
	 * @return The {@link SEPropertiesEditor} used in the panel
	 */
	public SEPropertiesEditor getPropsEditor() {
		return props_editor;
	}

	/**
	 * @return The {@link GMapViewer} used in the panel
	 */
	public GMapViewer getGmapviewer() {
		return gmapviewer;
	}

	/**
	 * @return The {@link JerboaRebuiltBridge} used in the panel
	 */
	public JerboaRebuiltBridge getBridge() {
		return bridge;
	}

	/**
	 * @return The {@link ModelerGenerated} used in the panel
	 */
	public ModelerGenerated getModeler() {
		return modeler;
	}
	
	/**
	 * Update the Jerboa viewer using the {@link RebuildEngine}
	 * 
	 * @param init Initial {@link ParametricSpecification}
	 * @param reev Reevaluation {@link ParametricSpecification}
	 * @param appId ApplicationId up to reevaluate the model
	 */
	public void updateViewer(ParametricSpecification init, ParametricSpecification reev, int appId) {
		
		// Clear the dartSelection to avoid bad selection (ex : removed dart)
		this.getGmapviewer().clearDartSelection();
		
		// Erase the Gmap of the viewer
		this.getBridge().getGMap().clear();

		// We make some copy to prevent the data modification
		ParametricSpecification cpinit = new ParametricSpecification(init);
		ParametricSpecification ps = new ParametricSpecification(reev);
		ParametricSpecification temp = this.getModeler().spec;
		
		// Set a new specification in the modeler to prevent bad reevaluation
		this.getModeler().spec = new ParametricSpecification();

		// Initialization of the RebuildEngine
		RebuildEngine re = new RebuildEngine(this.getModeler(), cpinit, ps);
		re.initializeReevaluation();
		
		// Reevaluation
		re.reevaluate(this.getBridge().getGMap(), this.getGmapviewer(), appId);

		// We replace the right spec in the modeler
		this.getModeler().spec = temp;
	}
	
	/**
	 * Update the Jerboa viewer using the {@link RebuildEngine} 
	 * 
	 * @param spec {@link ParametricSpecification}
	 */
	public void updateViewer(ParametricSpecification spec) {
		// Maybe another system must be better ?
		updateViewer(spec, spec);
	}
	
	/**
	 * Update the Jerboa viewer using the {@link RebuildEngine} up to the end
	 * 
	 * @param init Initial {@link ParametricSpecification}
	 * @param reev Reevaluation {@link ParametricSpecification}
	 */
	public void updateViewer(ParametricSpecification init, ParametricSpecification reev) {
		this.updateViewer(init, reev, 0);
	}

}
