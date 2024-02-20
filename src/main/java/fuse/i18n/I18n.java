package fuse.i18n;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.entity.Player;

public class I18n {
    private static Map<String, I18nContainer> containers = new HashMap<>();

    public static I18nContainer createContainer(String pluginName, boolean register) {
        I18nContainer container = new I18nContainer("en");

        if (register) {
            containers.put(pluginName.toLowerCase(), container);
        }
        return container;
    }

    public static I18nContainer getContainer(String pluginName) {
        return containers.get(pluginName);
    }

    public static String translate(String lang, String key) {
        String[] data = key.split(":", 2);
        String pluginName = data[0];
        key = data[1];

        I18nContainer container = containers.get(pluginName.toLowerCase());
        if (container == null) {
            return "<No container for \"" + pluginName + "\">";
        }

        return container.getOr(lang, key);
    }

    public static String translate(Player player, String key) {
        return translate(player.getLocale().toString().toLowerCase(), key).replace("&", "§");
    }

    public static void sendMessage(Player player, String key) {
        player.sendMessage(translate(player, key));
    }
}
