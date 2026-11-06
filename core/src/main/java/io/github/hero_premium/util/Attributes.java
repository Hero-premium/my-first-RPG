package io.github.hero_premium.util;

import io.github.hero_premium.util.vault.Key;
import io.github.hero_premium.util.vault.Vault;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Attributes implements Iterable<Key<Integer>> {

    private final Vault<Key<Integer>> vault = new Vault<>();

    /**
     * Removes the given modifier, keep in mind that it doesn't remove every modifier who shared this number
     * @param damageModifier the modifier to be removed
     * @throws java.util.NoSuchElementException if the modifier is not there.
     */
    public void removeModifier(Key<Integer> damageModifier) {
        vault.remove(damageModifier);
    }

    public void addAll(Set<Key<Integer>> damageModifiersMustNotBeNull) {
        vault.addAll(damageModifiersMustNotBeNull);
    }

    public boolean add(Key<Integer> damageModifier) {
        return vault.add(damageModifier);
    }

    public boolean remove(Key<Integer> damageModifier) {
        return vault.remove(damageModifier);
    }

    public boolean isEmpty() {
        return vault.isEmpty();
    }

    public int size() {
        return vault.size();
    }

    public Stream<Key<Integer>> stream() {
        return vault.stream();
    }

    @Override
    public @NotNull Iterator<Key<Integer>> iterator() {
        return vault.iterator();
    }

    public boolean removeIf(Predicate<? super Key<Integer>> predicate) {
        return vault.removeIf(predicate);
    }


    public int calculateModifier() {
        if (isEmpty()) return 1;

        for (Key<Integer> modifier : vault) {
            if (modifier.contains(0)) return 0;
        }

        int lowestModifier = stream()
            .flatMap(Key::stream)
            .mapToInt(Integer::intValue)
            .filter(value -> value < 0)
            .min()
            .orElse(0);


        int highestModifier = stream()
            .flatMap(Key::stream)
            .mapToInt(Integer::intValue)
            .filter(value -> value > 1)
            .max()
            .orElse(0);


        int modifierCalculated = highestModifier + lowestModifier;
        return modifierCalculated == 0 ? 1 : modifierCalculated;
    }

    @Contract(pure = true)
    public int applyModifierToValue(int value) {
        int modifier = calculateModifier();
        if (modifier == 0) {
            value = 0;
        } else if (modifier > 1) {
            value *= modifier;
        } else {
            value /= Math.abs(modifier);
        }
        return value;
    }
}
