package io.github.hero_premium.entities.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
