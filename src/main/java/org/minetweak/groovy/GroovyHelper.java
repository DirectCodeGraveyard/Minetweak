package org.minetweak.groovy;

import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

public class GroovyHelper {
    private static GroovyShell shell = new GroovyShell();

    public static Object runScript(String code) {
        return shell.parse(code).run();
    }

    public static GroovyShell getShell() {
        return shell;
    }

    public static Object runScript(File file) {
        try {
            return shell.parse(file).run();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
