package fuse.world.errors;

public class WorldException extends Exception {
    private String worldName;

    public WorldException(String worldName, String message) {
        super(message);
        this.worldName = worldName;
    }

    public WorldException(String worldName, Exception child) {
        super(child);
        this.worldName = worldName;
    }

    public String getWorldName() {
        return worldName;
    }
}
