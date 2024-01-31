package fuse.world.errors;

public class UnknownWorldException extends Exception {
    public UnknownWorldException(String worldName) {
        super("No such world: " + worldName);
    }
}
