package fr.ensma.lias.jerboa.datastructures;

import up.jerboa.core.JerboaDart;
import up.jerboa.core.util.Pair;
import java.util.List;
import java.util.ArrayList;

public class DartPairs {

    public List<Pair<JerboaDart, JerboaDart>> ctlList =
            new ArrayList<Pair<JerboaDart, JerboaDart>>();

    public DartPairs() {
        System.out.println("Create new data structure");
    }

    public void add(JerboaDart l, JerboaDart r) {
        this.ctlList.add(new Pair<JerboaDart, JerboaDart>(l, r));
    }

    public Pair<JerboaDart, JerboaDart> get(int index) {
        return ctlList.get(index);
    }

    public int size() {
        return this.ctlList.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DataCtrl:\n");
        for (Pair<JerboaDart, JerboaDart> pair : ctlList) {
            sb.append("[left: ").append(pair.l()).append(" right: ").append(pair.r()).append("]\r");
        }
        return sb.toString();
    }
}
