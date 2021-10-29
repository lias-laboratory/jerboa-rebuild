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

/** Trace
 *
 *   entrée: liste de référence
 *   entrée: un compteur
 *   traitement:
 *       initialiser une liste courante d'orbites
 *       pour chaque orbite d'une gmap
 *           comparer cet orbite avec la liste de référence
 *           si l'orbite et ses brins n'existaient pas
 *           alors
 *               afficher "création"
 *               orbite.id := count
 *               ajouter orbite à la liste courante
 *               count+=1
 *               retour
 *           sinon si l'orbite existe
 *               si ses brins ont changés
 *                   s'il y en a moins avec suppression
 *                   alors
 *                       afficher "diminution"
 *                   s'il y en a moins sans suppression
 *                   alors
 *                       afficher "cission"
 *                   s'il y en a plus sans suppression d'orbite
 *                   alors
 *                       afficher "augmentation"
 *                   sinon
 *                       afficher "fusion"
 *                   orbite.id := count
 *
 */


public class JerboaRebuilt extends JerboaModelerGeneric {

    private static int counter = 0;
    // BEGIN LIST OF EMBEDDINGS
    // END LIST OF EMBEDDINGS

    // BEGIN USER DECLARATION
    // END USER DECLARATION

    public JerboaRebuilt() throws JerboaException {

        super(3);

        this.addEmbedding(new JerboaEmbeddingInfo("Point orbit trace", JerboaOrbit.orbit(1, 2, 3), int.class));

        // BEGIN USER HEAD CONSTRUCTOR TRANSLATION

        // END USER HEAD CONSTRUCTOR TRANSLATION

        this.registerEbdsAndResetGMAP();

        this.registerRule(new CreateDart(this));
        this.registerRule(new ExtrudeIndependantFace(this));
        this.registerRule(new ExtrudeVolumeFace(this));
        this.registerRule(new CreateSquareFace(this));
        this.registerRule(new CreateEdge(this));
        this.registerRule(new InsertVertex(this));

        this.applyRule(this.rules.get(3), new JerboaInputHooksAtomic());

        traceOrbits(this.gmap);
    }

    private void showOrbitDarts(List<JerboaDart> orbit) {
        System.out.println(orbit.toString());
    }

    private void iterateOrbits(JerboaGMap gmap, JerboaOrbit pointOrbit) throws JerboaException {

        int nbNodes = gmap.getLength();
        for (int i = 0; i < nbNodes; i++) {
            var curOrbit = gmap.orbit(gmap.node(i), pointOrbit);
            showOrbitDarts(curOrbit);
        }
    }

    private void traceOrbits(JerboaGMap gmap) throws JerboaException {
        JerboaOrbit pointOrbit = JerboaOrbit.orbit(1, 2, 3);
        iterateOrbits(gmap, pointOrbit);
    }

}
