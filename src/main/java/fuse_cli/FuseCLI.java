package fuse_cli;

import fuse.Fuse;
import fuse_cli.tools.AnvilInteropTool;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "server")
class StartServerCommand implements Runnable {
    @Option(names = { "-h", "--headless" }, description = "Run the server in headless mode.")
    private boolean headless;

    public void run() {
        Fuse.safeLaunch(headless);
    }
}

@Command(name = "anvil")
class ConvertWorldCommand implements Runnable {
    @Parameters(index = "0", description = "The world to convert.")
    private String world;

    @Parameters(index = "1", description = "The output world file.")
    private String output;

    public void run() {
        System.out.println("Converting world...");

        try {
            AnvilInteropTool.convert(world, output);
        } catch (Exception e) {
            System.out.println("Failed to convert world: " + e.getMessage());
        }
    }
}

@Command(name = "fuse", subcommands = {
        StartServerCommand.class,
        ConvertWorldCommand.class
})
public class FuseCLI {
    public static void main(String[] args) {
        System.out.println("FuseMC -- High performance Minecraft server.\n");
        new CommandLine(new FuseCLI()).execute(args);
    }
}
