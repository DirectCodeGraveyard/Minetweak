package org.minetweak.language;

public class LanguageObject {

    private Object object;
    private String translatedName;

    public LanguageObject(Object object, String translatedName) {
        this.object = object;
        this.translatedName = translatedName;
    }

    public Object getObject() {
        return object;
    }

    public String getTranslatedName() {
        return translatedName;
    }

}
