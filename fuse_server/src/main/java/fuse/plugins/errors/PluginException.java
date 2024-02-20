package fuse.plugins.errors;

public class PluginException extends Exception {
    private String pluginName;

    public PluginException(String pluginName, String message) {
        super(message);
        this.pluginName = pluginName;
    }

    public PluginException(String pluginName, String message, Exception ex) {
        super(message, ex);
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }
}
