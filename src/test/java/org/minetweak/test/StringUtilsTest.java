package org.minetweak.test;

import org.junit.Test;
import org.minetweak.util.StringUtils;

import static org.junit.Assert.*;

public class StringUtilsTest {
    @Test
    public void dropFirst() {
        String[] original = new String[] {"Hi", "Hello", "Super", "Not"};
        String[] expect = new String[] {"Hello", "Super", "Not"};
        String[] actual = StringUtils.dropFirstString(original);
        assertArrayEquals(expect, actual);
    }

    @Test
    public void arrayToString() {
        String[] original = new String[] {"Hello", "from", "the", "Array", "to", "String!"};
        String expected = "Hello from the Array to String!";
        String actual = StringUtils.toString(original);
        assertEquals(expected, actual);
    }
}