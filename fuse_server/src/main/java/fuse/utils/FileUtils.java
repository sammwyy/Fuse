package fuse.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    public static String getChildInJar(File file, String child) throws IOException {
        try (JarFile jarFile = new JarFile(file)) {
            JarEntry entry = jarFile.getJarEntry(child);

            if (entry == null) {
                throw new IOException("File not found in jar");
            }

            try (InputStream is = jarFile.getInputStream(entry)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append('\n');
                }

                return builder.toString();
            }
        }
    }
}
