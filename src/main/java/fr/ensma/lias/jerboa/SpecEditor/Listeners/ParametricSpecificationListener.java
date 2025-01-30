package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import fr.ensma.lias.jerboa.datastructures.Application;
import fr.ensma.lias.jerboa.datastructures.ApplicationType;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;
import up.jerboa.exception.JerboaException;

/**
 * Listen modification on the parametric specification
 * 
 * @author Tom BOIREAU
 */
public interface ParametricSpecificationListener {

	/**
	 * 
	 * @return Index (not an application ID) of selected application
	 * @throws JerboaException 
	 */
	public int getCurrentIndex() throws JerboaException;

	/**
	 * Trigger by the {@link ParametricSpecification} on creation of an Init
	 * {@link Application}
	 * 
	 * @param specEntry New {@link Application} created
	 * @param index     Index of the {@link Application} in
	 *                  {@link ParametricSpecification}
	 */
	public void onInitApplication(Application specEntry, int index);

	/**
	 * Trigger by the {@link ParametricSpecification} on creation of an Added
	 * {@link Application}
	 * 
	 * @param specEntry New application created
	 * @param index     Index of the application in {@link ParametricSpecification}
	 */
	public void onAddApplication(Application specEntry, int index);

	/**
	 * Trigger by the {@link ParametricSpecification} on change
	 * {@link ApplicationType} of an {@link Application} to DELETE
	 * 
	 * @param specEntry {@link Application} used
	 * @param index     Index of the {@link Application} in
	 *                  {@link ParametricSpecification}
	 */
	public void onDeleteApplication(Application specEntry, int index);

	/**
	 * Trigger by the {@link ParametricSpecification} on deletion of an
	 * {@link Application}
	 * 
	 * @param specEntry {@link Application} deleted
	 */
	public void onDefinitiveDeleteApplication(Application specEntry);

	/**
	 * Trigger by the {@link ParametricSpecification} on move of an
	 * {@link Application} from oldindex to newindex
	 * 
	 * @param specEntry {@link Application} moved
	 * @param oldindex  Index of the {@link Application} before the moved
	 * @param newindex  New index of the {@link Application} in
	 *                  {@link ParametricSpecification}
	 */
	public void onMoveApplication(Application specEntry, int oldindex, int newindex);

	/**
	 * @return selected {@link Application}
	 * @throws JerboaException 
	 */
	public Application getCurrentApplication() throws JerboaException;

	/**
	 * Trigger by the {@link ParametricSpecification} after each modification of an
	 * application status
	 * @throws JerboaException 
	 */
	public void update() throws JerboaException;

}
