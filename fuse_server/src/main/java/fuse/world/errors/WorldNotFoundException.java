package fuse.world.errors;

public class WorldNotFoundException extends WorldException {
    public WorldNotFoundException(String worldName) {
        super(worldName, "World not found.");
    }
}
