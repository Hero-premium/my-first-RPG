package io.github.hero_premium.util.vault;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Key<T> {

    private final List<T> values = new ArrayList<>();


    public List<T> getValues() {
        return List.copyOf(values);
    }

    public void addValue(T value) {
        values.add(Objects.requireNonNull(value, "value must not be null"));
    }

    public void removeValue(T value) {
        if (!values.remove(Objects.requireNonNull(value, "value must not be null"))) {
            throw new NoSuchElementException();
        }
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }
}
