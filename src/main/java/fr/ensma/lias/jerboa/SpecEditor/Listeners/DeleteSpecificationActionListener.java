package fr.ensma.lias.jerboa.SpecEditor.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.ensma.lias.jerboa.SpecEditor.SESpecsSelector;
import fr.ensma.lias.jerboa.datastructures.ParametricSpecification;


public class DeleteSpecificationActionListener implements ActionListener {

    private final SESpecsSelector spec_selector;

    public DeleteSpecificationActionListener (SESpecsSelector spec_selector){

    this.spec_selector = spec_selector;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Thread t = new Thread() {
                public void run() {
                    /*TODO: Ask spec UID and delete from list */
                };
        };
        t.start();
    }
}
