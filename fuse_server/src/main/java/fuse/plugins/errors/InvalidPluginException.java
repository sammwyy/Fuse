package fuse.plugins.errors;

public class InvalidPluginException extends PluginException {
    public InvalidPluginException(String pluginName, String message, Exception ex) {
        super(pluginName, message, ex);
    }

    public InvalidPluginException(String pluginName, String message) {
        super(pluginName, message);
    }
}
