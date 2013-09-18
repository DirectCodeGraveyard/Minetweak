package org.minetweak.util;

public class Tuple {
    /**
     * First Object in the Tuple
     */
    private Object first;

    /**
     * Second Object in the Tuple
     */
    private Object second;

    public Tuple(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first Object in the Tuple
     */
    public Object getFirst() {
        return this.first;
    }

    /**
     * Get the second Object in the Tuple
     */
    public Object getSecond() {
        return this.second;
    }
}
