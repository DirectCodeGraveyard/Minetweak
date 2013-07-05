package org.minetweak.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * All Credits to samrg472
 * This is the new config type for Minetweak
 * Note: This should not be used to store data
 * JSON is the recommended storage type
 */
public class Configuration {

    private ConfigurationReader reader;
    private ConfigurationWriter writer;

    /**
     * @param path location of the configuration file
     */
    public Configuration(File path) {
        reader = new ConfigurationReader(path);
        writer = new ConfigurationWriter(path, reader.getExists());

        reader.parse();
        writer.write(reader.getFile());
    }

    /**
     * @param path location of the configuration file
     */
    public Configuration(String path) {
        File file = new File(path);
        reader = new ConfigurationReader(file);
        writer = new ConfigurationWriter(file, reader.getExists());

        reader.parse();
        writer.write(reader.getFile());
    }

    /**
     * Prevent further writing to the configuration file
     * use once your default values are written, or face possible multi-channel spam
     */
    public void lock() {
        writer.setLock(true);
    }

    /**
     * Allow writing to the configuration file
     * In case you need to write to the configuration via the get method
     */
    public void unlock() {
        writer.setLock(false);
    }

    /**
     * Write a header in the configuration
     *
     * @param header string to go into the header
     */
    public void header(String header) {
        writer.writeHeader(header);
    }

    /**
     * Write a comment in the configuration
     *
     * @param comment string to go into the comment
     */
    public void comment(String comment) {
        writer.writeComment(comment, false);
    }

    /**
     * Creates a new line in the configuration if unlocked
     * Useful for formatting purposes
     *
     */
    public void newLine() {
        writer.newLine();
    }

    /**
     * @param node specified option in the configuration
     * @return the full value of the option, returns {@code value} if it is not found in the configuration
     */
    public String get(String node) {
        return get(new Property(node, ""));
    }

    /**
     * @param node  specified option in the configuration
     * @param value default value if the config doesn't exist, will automatically write to configuration if unlocked
     * @return the full value of the option, returns {@code value} if it is not found in the configuration
     */
    public String get(String node, String value) {
        return get(new Property(node, value));
    }

    /**
     * @param property option to get
     * @return the full value of the option, returns {@code property.getValue()} if it is not found in the configuration
     */
    public String get(Property property) {
        String fullOption = property.getNode();
        String node = property.getValue();

        String option = reader.get(fullOption);
        if (option == null) {
            for (String comments : property.getComments())
                writer.writeComment(comments, true);
            writer.writeOption(fullOption, node);
            writer.newLine(true);
            reader.add(fullOption, node);
            if (reader.getExists())
                writer.newLine(true);
            return node.trim();
        }

        return option.trim();
    }

    /**
     * Force set a value on a specified option
     *
     * @param node  Configuration node
     * @param value value to force set in the configuration
     */
    public void set(String node, String value) {
        Map<String, String> forcedOption = new HashMap<String, String>();
        forcedOption.put(node, value);
        set(forcedOption);
    }

    /**
     * Forces set values on specified option(s)
     *
     * @param forced full node to value mapping of options to force a changed value
     */
    public void set(Map<String, String> forced) {
        boolean previousLock = writer.getLock();
        writer.setLock(false);

        for (String s : forced.keySet())
            get(s, forced.get(s));

        reader.parse(forced);
        reader.getLocation().delete();

        writer.write(reader.getFile());
        writer.setLock(previousLock);
    }

}
