package fr.ensma.lias.jerboa;

import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.CreateDart;
import fr.ensma.lias.jerboa.ExtrudeIndependantFace;
import fr.ensma.lias.jerboa.ExtrudeVolumeFace;
import fr.ensma.lias.jerboa.CreateSquareFace;
import fr.ensma.lias.jerboa.CreateEdge;
import fr.ensma.lias.jerboa.InsertVertex;



/**
 * 
 */

public class JerboaRebuilt extends JerboaModelerGeneric {

    // BEGIN LIST OF EMBEDDINGS
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public JerboaRebuilt() throws JerboaException {

        super(3);

    // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

    // END USER HEAD CONSTRUCTOR TRANSLATION

        this.registerEbdsAndResetGMAP();

        this.registerRule(new CreateDart(this));
        this.registerRule(new ExtrudeIndependantFace(this));
        this.registerRule(new ExtrudeVolumeFace(this));
        this.registerRule(new CreateSquareFace(this));
        this.registerRule(new CreateEdge(this));
        this.registerRule(new InsertVertex(this));
    }

}
