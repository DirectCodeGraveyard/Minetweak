package org.minetweak.language;

import org.minetweak.plugins.Plugin;

public class LanguageRegistry {

    /**
     * Register a language entry using an object
     *
     * @param targetPlugin   Plugin to register object to
     * @param languageObject LanguageObject that contains the object and translation
     */
    public static void addName(Plugin targetPlugin, LanguageObject languageObject) {
        // targetPlugin.registerLanguageObject(languageObject);
    }

    public static void getTranslatedName(Plugin targetPlugin, LanguageObject languageObject) {
        // targetPlugin.getTranslatedName(languageObject);
    }
}
