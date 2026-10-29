package io.github.hero_premium.util.vault;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Vault<T extends Key<?>> implements Iterable<T> {

    private final Set<T> keys;

    public Vault() {
        this(new HashSet<>());
    }

    public Vault(Set<T> keys) {
        this.keys = keys;
    }

    public boolean add(T key) {
        return keys.add(key);
    }

    public boolean addAll(Set<T> keys) {
        return this.keys.addAll(keys);
    }

    public void remove(T key) {
        if (!keys.remove(key)) throw new NoSuchElementException();
    }

    public int size() {
        return keys.size();
    }

    public boolean isEmpty() {
        return keys.isEmpty();
    }

    public boolean contains(T key) {
        return keys.contains(key);
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return keys.iterator();
    }
}
