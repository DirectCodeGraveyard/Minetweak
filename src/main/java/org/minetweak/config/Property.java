package org.minetweak.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages Configuration Options
 * All Credits to samrg472
 */
public class Property {

    private ArrayList<String> comments = new ArrayList<String>();

    private String node;
    private String value;

    /**
     * Creates a property with no value
     *
     * @param node option node
     */
    public Property(String node) {
        this.node = node;
        this.value = "";
    }

    /**
     * Option node of the property
     *
     * @param node  option node
     * @param value default option value
     */
    public Property(String node, String value) {
        this.node = node;
        this.value = value;
    }

    /**
     * Comments the node
     *
     * @param comment comment of the node
     * @return this
     */
    public Property addComment(String comment) {
        comments.add(comment);
        return this;
    }

    /**
     * @return comments of the property
     */
    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }

    /**
     * @return node
     */
    public String getNode() {
        return node;
    }

    /**
     * Default value of the property
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

}
