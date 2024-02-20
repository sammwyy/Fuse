package fuse.utils;

import java.util.HashMap;
import java.util.Map;

import com.moandjiezana.toml.Toml;

public class TomlUtils {
    public static Map<String, String> getNested(String prefix, Map<String, Object> map) {
        Map<String, String> values = new HashMap<>();

        map.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, String> nested = getNested(prefix + key + ".", (Map<String, Object>) value);
                values.putAll(nested);
            } else {
                values.put(prefix + key, value.toString());
            }
        });

        return values;
    }

    public static Map<String, String> getNested(Toml toml) {
        return getNested("", toml.toMap());
    }
}