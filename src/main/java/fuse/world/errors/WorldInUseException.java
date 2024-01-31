package fuse.world.errors;

public class WorldInUseException extends Exception {
    public WorldInUseException(String worldName) {
        super("World is in use: " + worldName);
    }
}
