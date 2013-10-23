package org.minetweak.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Main System for Plugins
 */
public @interface Plugin {

    /**
     * Injects the Plugin Instance into the static annotated field
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Instance {
    }

    /**
     * Injects the Logger in the annotated field
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Logger {
    }

    /**
     * Injects the PluginInfo in the annotated field
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Info {
    }

    /**
     * Marks a Method for Enable
     *
     * @see org.minetweak.plugins.Plugin.Handler
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Deprecated
    public @interface Enable {
    }

    /**
     * Marks a Method for Disable
     * @see org.minetweak.plugins.Plugin.Handler
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Deprecated
    public @interface Disable {
    }

    /**
     * Marks a Method for Disable
     *
     * @see org.minetweak.plugins.Plugin.Handler
     */
    @Deprecated
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Load {
    }

    /**
     * Marks a Method to handle Plugin Events
     * <p>Valid Event Types</p>
     * <ul>
     *     <li>PluginLoadEvent</li>
     *     <li>PluginEnableEvent</li>
     *     <li>PluginDisableEvent</li>
     * </ul>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Handler {
    }
}
