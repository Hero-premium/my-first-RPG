package io.github.hero_premium.util.vault;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

public class Key<T> implements Iterable<T> {

    private final List<T> values = new ArrayList<>();


    public List<T> getValues() {
        return List.copyOf(values);
    }

    public void addValue(T value) {
        values.add(Objects.requireNonNull(value, "value must not be null"));
    }

    public void removeValue(T value) {
        if (!values.remove(Objects.requireNonNull(value, "value must not be null"))) {
            throw new NoSuchElementException("value " + value + " not found");
        }
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }

    public Stream<T> stream() {
        return values.stream();
    }

    public boolean contains(T value) {
        return values.contains(value);
    }


    @Override
    public @NotNull Iterator<T> iterator() {
        return values.iterator();
    }
}
