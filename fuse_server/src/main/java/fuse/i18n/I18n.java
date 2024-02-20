package fuse.i18n;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moandjiezana.toml.Toml;

import fuse.utils.FileUtils;
import fuse.utils.RAMFile;
import fuse.utils.TomlUtils;
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

    public static I18nContainer createContainer(String pluginName, boolean register, File jarFile) {
        I18nContainer container = createContainer(pluginName, true);

        try {
            List<RAMFile> files = FileUtils.getChildrenInJar(jarFile, "lang");

            for (RAMFile file : files) {
                String lang = file.getName().split("/")[1].split("[.]")[0];
                String raw = file.getRaw();

                Toml toml = new Toml().read(raw);
                Map<String, String> values = TomlUtils.getNested(toml);

                for (Map.Entry<String, String> entry : values.entrySet()) {
                    container.add(lang, entry.getKey(), entry.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
