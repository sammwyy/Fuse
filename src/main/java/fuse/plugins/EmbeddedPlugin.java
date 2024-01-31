package fuse.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This annotation is used to mark a plugin as embedded.
// Embedded plugins are loaded automatically by the server.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EmbeddedPlugin {
    public String name();

    public String version() default "0.0.0-internal";

    public String description() default "";

    public String[] authors() default { "NoBody" };

    public String[] dependencies() default {};

    public String[] softDependencies() default {};
}
