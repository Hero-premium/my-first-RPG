package io.github.hero_premium.util;

import com.badlogic.gdx.utils.ObjectMap;


public class ReadOnlyClassMap<T> {
    private final ObjectMap<Class<? extends T>, T> array;

    public ReadOnlyClassMap(ObjectMap<Class<? extends T>, T> array) {
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

    @Override
    public String toString() {
        return array.toString();
    }
}
