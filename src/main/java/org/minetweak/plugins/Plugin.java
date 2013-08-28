package org.minetweak.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Main System for Plugins
 */
public @interface Plugin {

    // Marks the Field for Minetweak to set the current instance of your plugin
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Instance {}

    // Injects the Logger into your Plugin
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Logger {}

    // Injects the PluginInfo into your Plugin
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface PluginInformation {}

    // Marks the Method to be called on Enable
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Enable {}

    // Marks the Method to be called on Disable
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Disable {}

    // Marks the Method to be called on Load
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Load {}
}
