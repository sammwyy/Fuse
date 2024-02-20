package fuse.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fuse.plugins.Plugin;

public class PluginUtils {
    public static List<RAMFile> getTranslationFiles(Plugin plugin) throws IOException {
        if (plugin.getPluginManager().getServer().isHeadless()) {
            return FileUtils.getChildrenInJar(plugin.getFile(), "lang");
        } else {
            File langFolder = new File(plugin.getDataFolder(), "lang");

            if (!langFolder.exists()) {
                langFolder.mkdirs();
                List<RAMFile> files = FileUtils.getChildrenInJar(plugin.getFile(), "lang");

                for (RAMFile file : files) {
                    String name = file.getName().split("/")[1];
                    File newFile = new File(langFolder, name);
                    newFile.createNewFile();
                    FileUtils.writeToFile(newFile, file.getRaw());
                }
            }

            return FileUtils.getChildrenInDir(langFolder);
        }
    }
}
