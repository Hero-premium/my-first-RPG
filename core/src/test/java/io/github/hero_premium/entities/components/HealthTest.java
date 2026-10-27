package io.github.hero_premium.entities.components;

import io.github.hero_premium.components.health.Health;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        health = new Health(10, 25);
        health.modifyHp(5);
        assertEquals(15, health.getHp());

        health = new Health(10, 10, 0);
        health.modifyHp(5);
        assertEquals(10, health.getHp());

        health = new Health(10, 10, -2);
        health.modifyHp(-6);
        assertEquals(7, health.getHp());

        health = new Health(1, 10, 2);
        health.modifyHp(2);
        assertEquals(5, health.getHp());

        health = new Health(1, 10, 2);
        health.modifyHp(-2);
        assertEquals(0, health.getHp());

        health = new Health(10, 10, 2);
        health.modifyHp(-2);
        assertEquals(6, health.getHp());

        health = new Health(10, 10, -2);
        health.modifyHp(-2);
        assertEquals(9, health.getHp());

        health = new Health(1, 10, 3);
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
        Health health;
        health = new Health(10);
        health.addModifier(5);
        health.addModifier(5);
        health.addModifier(9);
        health.addModifier(2);
        assertEquals(9, health.calculateDamageModifier());
    }

    @Test
    void hasZero() {
        Health health;
        health = new Health(3);
        health.addModifier(0);
        health.addModifier(-5);
        health.addModifier(5);
        health.addModifier(-9);
        health.addModifier(2);
        assertEquals(0, health.calculateDamageModifier());
    }

    @Test
    void negativeGetHighest() {
        Health health;
        health = new Health(7);
        health.addModifier(-5);
        health.addModifier(-5);
        health.addModifier(-9);
        health.addModifier(-2);
        assertEquals(-9, health.calculateDamageModifier());
    }

    @Test
    void isEmpty() {
        Health health;
        health = new Health(10);
        assertEquals(1, health.calculateDamageModifier());
    }

    @Test
    void mixPositiveAndNegativeWithResultEquals0() {
        Health health;
        health = new Health(938);
        health.addModifier(5);
        health.addModifier(5);
        health.addModifier(9);
        health.addModifier(-5);
        health.addModifier(-9);
        health.addModifier(-2);
        assertEquals(1, health.calculateDamageModifier());
    }

    @Test
    void mixNegativeAndPositiveWithNegativeResult() {
        Health health;
        health = new Health(0);
        health.addModifier(5);
        health.addModifier(-5);
        health.addModifier(7);
        health.addModifier(-5);
        health.addModifier(-9);
        health.addModifier(-2);
        assertEquals(-2, health.calculateDamageModifier());
    }

    @Test
    void mixNegativeAndPositiveWithPositiveResult() {
        Health health;
        health = new Health(33);
        health.addModifier(5);
        health.addModifier(5);
        health.addModifier(9);
        health.addModifier(-33);
        health.addModifier(-5);
        health.addModifier(9);
        health.addModifier(35);
        assertEquals(2, health.calculateDamageModifier());
    }

    @Test
    void removing() {
        Health health;
        health = new Health(33);
        health.addModifier(5);
        health.removeModifier(5);
        health.addModifier(9);
        health.addModifier(-33);
        health.addModifier(-5);
        health.removeModifier(9);
        health.removeModifier(-33);
        assertEquals(-5, health.calculateDamageModifier());
    }

    @Test
    void removingWhichDoesNotExist() {
        Health health;
        health = new Health(33);
        health.addModifier(5);
        assertThrows(NoSuchElementException.class, () -> health.removeModifier(0));
    }

    @Test
    void dontRemoveTwice() {
        Health health;
        health = new Health(33);
        health.addModifier(5);
        health.addModifier(5);
        health.removeModifier(5);
        assertEquals(5, health.calculateDamageModifier());
    }
}
