package fr.ensma.lias.jerboa;

import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.exception.JerboaException;

public class JerboaRebuilt extends JerboaModelerGeneric {
	public JerboaRebuilt() throws JerboaException {
		super(3);

		this.registerEbdsAndResetGMAP();
	}
}
