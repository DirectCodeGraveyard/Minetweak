package org.minetweak.test

import org.minetweak.util.StringUtils

class StringUtilsTest extends GroovyTestCase {
    void testDropFirst() {
        def original = ["Hi", "Hello", "Super", "Not"]
        def expected = ["Hello", "Super", "Not"]
        def actual = StringUtils.dropFirstString(original)
        assert expected == actual
    }

    void testArrayToString() {
        def original = ["Hello", "from", "the", "Array", "to", "String!"]
        def expected = "Hello from the Array to String!"
        def actual = StringUtils.toString(original)
        assert expected == actual
    }

    void testIsInteger() {
        assertTrue StringUtils.isInteger("25")
        assertFalse StringUtils.isInteger("NotAnInteger")
    }
}