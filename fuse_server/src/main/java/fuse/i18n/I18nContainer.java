package fuse.i18n;

import java.util.HashMap;
import java.util.Map;

public class I18nContainer {
    private Map<String, Map<String, String>> translations;
    private String defaultLang;

    public I18nContainer(String defaultLang) {
        this.translations = new HashMap<>();
        this.defaultLang = defaultLang;
    }

    public void add(String lang, String key, String value) {
        if (!this.translations.containsKey(lang)) {
            this.translations.put(lang, new HashMap<>());
        }

        this.translations.get(lang).put(key, value);
    }

    public String get(String lang, String key) {
        if (!this.translations.containsKey(lang)) {
            if (lang.contains("_")) {
                return this.get(lang.split("_")[0], key);
            }
            return null;
        }

        return this.translations.get(lang).get(key);
    }

    public String getOr(String lang, String key) {
        String value = this.get(lang, key);
        if (value == null) {
            value = this.get(this.defaultLang, key);
        }
        if (value == null) {
            value = "<No translation for \"" + key + "\">";
        }
        return value;
    }
}
