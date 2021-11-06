package fly.factions.impl.registries;

import fly.factions.api.registries.Registry;

import java.util.Collection;
import java.util.Map;

/**
 * A wrapper to allow for a case insensitive string registry
 *
 * @param <V> the value type
 */
public class StringRegistryImpl<V> implements Registry<V, String> {
    private RegistryImpl<V, String> registry;

    public StringRegistryImpl(Class<V> clazz) {
        registry = new RegistryImpl<>(clazz);
    }

    @Override
    public V get(String key) {
        return registry.get(key.toLowerCase());
    }

    @Override
    public void set(String key, V value) {
        registry.set(key.toLowerCase(), value);
    }

    @Override
    public Collection<V> list() {
        return registry.list();
    }

    @Override
    public Map<String, V> map() {
        return registry.map();
    }
}
