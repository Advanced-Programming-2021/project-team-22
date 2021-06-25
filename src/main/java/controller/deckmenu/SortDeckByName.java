package controller.deckmenu;

import model.Deck;

import java.util.Comparator;

public class SortDeckByName implements Comparator<Deck> {

    @Override
    public int compare(Deck o1, Deck o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
