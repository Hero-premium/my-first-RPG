package io.github.hero_premium.util.vault;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class Vault<T extends Key<?>> extends HashSet<T> {

    public Vault() {
        super();
    }

    public Vault(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public boolean remove(Object key) {
        if (!super.remove(key)) throw new NoSuchElementException("key " + key + " not found");
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        Set<?> unique = new HashSet<>(c);
        for (Object key : unique) {
            if (!contains(key)) throw new NoSuchElementException("key " + key + " not found");
        }
        return super.removeAll(unique);
    }
}
