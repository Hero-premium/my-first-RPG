package io.github.hero_premium.util.collections;

import com.badlogic.gdx.utils.ObjectMap;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ReadOnlyClassMap<T> {
    protected final ObjectMap<Class<? extends T>, T> map;

    public ReadOnlyClassMap(ObjectMap<Class<? extends T>, T> map) {
        this.map = Objects.requireNonNull(map, "map must not be null");
    }

    public int size() {
        return map.size;
    }

    public <E extends T> E get(@NotNull Class<E> type) {
        return type.cast(map.get(type));
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public boolean containsKey(Class<? extends T> type) {
        return map.containsKey(type);
    }

    public boolean containsValue(T value) {
        return map.containsValue(value, false);
    }

}
