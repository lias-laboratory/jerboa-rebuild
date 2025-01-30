package fr.ensma.lias.jerboa.SpecEditor;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.PersistentName;

/**
 * @author Tom BOIREAU
 * 
 *         Panel to see/edit properties of the selected {@link SEAppliedRule}
 * 
 */
public class SEPropertiesEditor extends JPanel {

	private static final long serialVersionUID = -7496911991807536100L;

	// Components
	private final SEProperty<Integer> app_id;
	private final SEProperty<ApplicationType> app_type;
	private final SEProperty<String> app_operation;
	private final SEProperty<String> app_PNs;
	private final SEProperty<String> app_dartIDs;
	
	private final Box app_listprops;

	// Attribute
	private Application curr_app;

	/**
	 * Constructor of the panel
	 */
	public SEPropertiesEditor() {
		super();

		// It's like a list
		super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Define a border with a title
		super.setBorder(BorderFactory.createTitledBorder("Properties Viewer"));

		// Define all components
		this.app_id = new SEProperty<Integer>("Application ID :");
		this.app_type = new SEProperty<ApplicationType>("Application Type :");
		this.app_operation = new SEProperty<String>("Operation :");
		this.app_PNs = new SEProperty<String>("PNs :");
		this.app_dartIDs = new SEProperty<String>("Dart IDs :");
		this.app_listprops = Box.createVerticalBox();

		// Add all components to the panel
		super.add(this.app_id);
		super.add(this.app_type);
		super.add(this.app_operation);
		super.add(this.app_PNs);
		super.add(this.app_dartIDs);
		super.add(this.app_listprops);
	}
	
	/**
	 * Display properties of an empty scene
	 */
	public void showEmptyApplication() {
		// Emptied the properties (no application here)
		this.curr_app = null;
		this.app_id.setValue(0);
		this.app_type.setValue(null);
		this.app_operation.setValue(null);
		this.app_PNs.setValue(null);
		this.app_dartIDs.setValue(null);
		
		this.app_listprops.removeAll();
	}

	/**
	 * Display properties of an {@link Application}
	 * 
	 * @param app {@link Application} to be displayed
	 */
	public void showApplication(Application app) {
		System.err.println("APPLICATION: " + app);
		// Change the current application
		this.curr_app = app;
		
		this.app_id.setValue(app.getApplicationID());
		
		this.app_type.setValue(app.getApplicationType());

		this.app_operation.setValue(app.getName());

		// We need to build the string for the list of PN
		StringBuilder strbd_pns = new StringBuilder();
		for (PersistentName pn : app.getPersistentNames()) {
			strbd_pns.append(pn.toString());
			strbd_pns.append(';');
		}
		this.app_PNs.setValue(strbd_pns.toString());

		// Same things for the list of dartID
		StringBuilder strbd_dartids = new StringBuilder();
		for (Integer id : app.getDartIDs()) {
			strbd_pns.append(id);
			strbd_pns.append(';');
		}
		this.app_dartIDs.setValue(strbd_dartids.toString());
		
		System.err.println("UPDATE PROPERTIES");
		app_listprops.removeAll();
		
		List<String> props = app.getGeoParams();
		for (String prop : props) {
			SEProperty<String> pi = new SEProperty<>(prop);
			app_listprops.add(pi);
		}	
	}

	/**
	 * @return The current displayed {@link Application}
	 */
	public Application getCurrentApplication() {
		return this.curr_app;
	}
}
