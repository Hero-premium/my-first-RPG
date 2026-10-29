package io.github.hero_premium.components.health;

import io.github.hero_premium.util.vault.Key;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {

    @Test
    void getHp() {
        Health health;

        health = new Health(10);
        // most useless test ever?
        assertEquals(10, health.getHp());
    }

    @Test
    void setHp() {
        Health health;

        health = new Health(10);
        health.setHp(20);
        assertEquals(10, health.getHp());

        health = new Health(10);
        health.setHp(5);
        assertEquals(5, health.getHp());

        health = new Health(5, 10);
        health.setHp(9);
        assertEquals(9, health.getHp());
    }


    @Test
    void modifyHp() {
        Health health;
        Key<Integer> key;

        health = new Health(10, 25);
        health.modifyHp(5);
        assertEquals(15, health.getHp());

        key = new Key<>();
        key.addValue(0);
        health = new Health(10, 10, key);
        health.modifyHp(5);
        assertEquals(10, health.getHp());

        key = new Key<>();
        key.addValue(-2);
        health = new Health(10, 10, key);
        health.modifyHp(-6);
        assertEquals(7, health.getHp());


        key = new Key<>();
        key.addValue(2);
        health = new Health(1, 10, key);
        health.modifyHp(2);
        assertEquals(5, health.getHp());

        key = new Key<>();
        key.addValue(2);
        health = new Health(1, 10, key);
        health.modifyHp(-2);
        assertEquals(0, health.getHp());

        key = new Key<>();
        key.addValue(2);
        health = new Health(10, 10, key);
        health.modifyHp(-2);
        assertEquals(6, health.getHp());

        key = new Key<>();
        key.addValue(-2);
        health = new Health(10, 10, key);
        health.modifyHp(-2);
        assertEquals(9, health.getHp());

        key = new Key<>();
        key.addValue(3);
        health = new Health(1, 10, key);
        health.modifyHp(3);
        assertEquals(10, health.getHp());
    }

    @Test
    void resetHp() {
        Health health;

        health = new Health(10);
        health.setHp(1);
        health.resetHp();
        assertEquals(10, health.getHp());
    }

    @Test
    void positiveGetHighest() {
        Health health = new Health(10);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.addValue(5);
        key.addValue(9);
        key.addValue(2);
        assertEquals(9, health.calculateDamageModifier());
    }

    @Test
    void hasZero() {
        Health health = new Health(3);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(0);
        key.addValue(-5);
        key.addValue(5);
        key.addValue(-9);
        key.addValue(2);
        assertEquals(0, health.calculateDamageModifier());
    }

    @Test
    void negativeGetHighest() {
        Health health = new Health(7);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(-5);
        key.addValue(-5);
        key.addValue(-9);
        key.addValue(-2);
        assertEquals(-9, health.calculateDamageModifier());
    }

    @Test
    void isEmpty() {
        Health health = new Health(10);
        assertEquals(1, health.calculateDamageModifier());
    }

    @Test
    void mixPositiveAndNegativeWithResultEquals0() {
        Health health = new Health(938);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.addValue(5);
        key.addValue(9);
        key.addValue(-5);
        key.addValue(-9);
        key.addValue(-2);
        assertEquals(1, health.calculateDamageModifier());
    }

    @Test
    void mixNegativeAndPositiveWithNegativeResult() {
        Health health = new Health(0);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.addValue(-5);
        key.addValue(7);
        key.addValue(-5);
        key.addValue(-9);
        key.addValue(-2);
        assertEquals(-2, health.calculateDamageModifier());
    }

    @Test
    void mixNegativeAndPositiveWithPositiveResult() {
        Health health = new Health(33);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.addValue(5);
        key.addValue(9);
        key.addValue(-33);
        key.addValue(-5);
        key.addValue(9);
        key.addValue(35);
        assertEquals(2, health.calculateDamageModifier());
    }

    @Test
    void removing() {
        Health health = new Health(33);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.removeValue(5);
        key.addValue(9);
        key.addValue(-33);
        key.addValue(-5);
        key.removeValue(9);
        key.removeValue(-33);
        assertEquals(-5, health.calculateDamageModifier());
    }

    @Test
    void removingWhichDoesNotExist() {
        Health health = new Health(33);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        assertThrows(NoSuchElementException.class, () -> key.removeValue(0));
    }

    @Test
    void dontRemoveTwice() {
        Health health = new Health(33);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(5);
        key.addValue(5);
        key.removeValue(5);
        assertEquals(5, health.calculateDamageModifier());
    }

    @Test
    void onlyOneAdded() {
        Health health = new Health(33);
        Key<Integer> key = new Key<>();
        health.addModifier(key);
        key.addValue(1);
        assertEquals(1, health.calculateDamageModifier());
    }
}
