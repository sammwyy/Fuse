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

    public String getFileName() {
        if (this.name.contains("/")) {
            String[] parts = this.name.split("/");
            return parts[parts.length - 1];
        }

        return this.name;
    }

    public String getRaw() {
        return this.raw;
    }
}
