package fuse.utils;

import java.io.File;
import java.util.List;

public class FileUtils {
    public static File getCurrentDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    public static File getCurrentChild(String child) {
        return new File(getCurrentDirectory(), child);
    }

    public static List<String> getFilesName(File path, boolean removeExt) {
        File[] files = path.listFiles();
        String[] names = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();

            if (removeExt) {
                names[i] = names[i].substring(0, names[i].lastIndexOf('.'));
            }
        }

        return List.of(names);
    }
}
