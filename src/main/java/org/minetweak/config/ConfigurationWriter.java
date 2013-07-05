package org.minetweak.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * All Credits to samrg472
 */
public class ConfigurationWriter {

    private File path;
    private boolean locked = false;
    private boolean existed;

    public ConfigurationWriter(File path, boolean exists) {
        this.path = path;
        this.existed = exists;
    }

    public void setLock(boolean lock) {
        locked = lock;
    }

    public boolean getLock() {
        return locked;
    }

    public void writeHeader(String header) {
        if (existed) return;
        write("### " + header + " ###");
        newLine();
    }

    public void writeComment(String comment, boolean force) {
        if (force && !locked) {
            write("# " + comment);
            newLine(true);
            return;
        }
        if (existed) return;
        write("# " + comment);
        newLine();
    }

    public void writeOption(String option, String value) {
        write(option + ": " + value);
        newLine();
    }

    public void newLine() {
        newLine(false);
    }

    public void newLine(boolean force) {
        if (existed && force) {
            write("\n");
            return;
        } else if (existed) return;
        write("\n");
    }

    public void write(String data) {
        if (locked) return;
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fw, 32768);
            writer.write(data);
            writer.close();
            fw.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to write to configuration", e);
        }
    }


}