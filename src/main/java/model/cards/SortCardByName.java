package model.cards;

import java.util.Comparator;

public class SortCardByName implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        return o1.name.compareToIgnoreCase(o2.name);
    }
}
