package fuse.utils;

public class RAMFile {
    private String name;
    private String raw;

    public RAMFile(String name, String raw) {
        this.name = name;
        this.raw = raw;
    }

    public String getName() {
        return this.name;
    }

    public String getRaw() {
        return this.raw;
    }
}
