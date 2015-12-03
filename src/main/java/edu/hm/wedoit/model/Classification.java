package edu.hm.wedoit.model;

import java.util.Locale;

/**
 * Enum for the classifications for the suppliers
 */
public enum Classification
{
    TOP("top"), NORMAL("normal"), ONE_OFF("one_off"), NONE("none");

    private final String classification;

    Classification(String classification) {
        this.classification = classification;
    }

    public String getClassification() {
        return classification;
    }
    /**
     * try to find the matching frontend module
     * @param string frontend module string
     * @return FrontendModule or null
     */
    public static Classification fromString(String string) {
        if (string == null) {
            return null;
        }
        string = string.toLowerCase(Locale.ROOT);
        for (Classification module : values()) {
            if (module.name().equalsIgnoreCase(string)) {
                return module;
            }
        }
        return null;
    }


}
