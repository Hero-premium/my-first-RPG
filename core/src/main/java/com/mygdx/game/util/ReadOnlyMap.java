package com.mygdx.game.util;

import com.badlogic.gdx.utils.ObjectMap;


public class ReadOnlyMap<T> {
    private final ObjectMap<Class<? extends T>, T> array;

    public ReadOnlyMap(ObjectMap<Class<?  extends T>, T> array) {
        this.array = array;
    }

    public int size() {
        return array.size;
    }

    public <E extends T> E get(Class<E> type) {
        return type.cast(array.get(type));
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }

}
