package fuse_cli.tools;

import java.io.File;
import java.nio.file.Files;

import net.hollowcube.polar.AnvilPolar;
import net.hollowcube.polar.PolarWorld;
import net.hollowcube.polar.PolarWriter;

public class AnvilInteropTool {
    public static void convert(String anvilDirPath, String polarFilePath) throws Exception {
        File anvilDir = new File(anvilDirPath);
        File polarFile = new File(polarFilePath);

        if (!anvilDir.exists()) {
            throw new IllegalArgumentException("Anvil directory does not exist");
        }

        if (!anvilDir.isDirectory()) {
            throw new IllegalArgumentException("Anvil directory path does not point to a directory");
        }

        if (polarFile.exists()) {
            throw new IllegalArgumentException("Polar file already exists");
        }

        PolarWorld world = AnvilPolar.anvilToPolar(anvilDir.toPath());
        byte[] data = PolarWriter.write(world);
        polarFile.createNewFile();
        Files.write(polarFile.toPath(), data);

        System.out.println("Anvil to Polar conversion complete");

    }
}
