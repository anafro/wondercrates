package ru.anafro.wondercrates.utils.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CircularArray<E> implements Iterable<E> {
    public final List<E> elements;

    public CircularArray(E... elements) {
        this.elements = Arrays.asList(elements);
    }

    public E get(int loopingIndex) {
        return elements.get((loopingIndex % elements.size() + elements.size()) % elements.size());
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    public int size() {
        return elements.size();
    }

    public Stream<E> stream() {
        return elements.stream();
    }
}
