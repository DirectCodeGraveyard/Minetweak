package org.minetweak.config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * All Credits to samrg472
 */
public class ConfigurationReader {

    private File path;
    private boolean parsed = false;
    private boolean exists = false;

    private String fullFile = "";
    private HashMap<String, String> options = new HashMap<String, String>();

    public ConfigurationReader(File path) {
        this.path = path;
        if (path.exists())
            exists = true;
    }

    public boolean getExists() {
        return exists;
    }

    public String get(String option) {
        return options.get(option);
    }

    public String getFile() {
        String fullFileTemp = fullFile;
        fullFile = null;
        return fullFileTemp;
    }

    public File getLocation() {
        return path;
    }

    public void parse() {
        parse(null);
    }

    public void parse(Map<String, String> forcedOptions) {
        if (!path.isFile())
            parsed = true;
        if (parsed && (forcedOptions == null)) return;
        if (forcedOptions != null) {
            fullFile = "";
            options = null;
            options = new HashMap<String, String>();
        }

        try {
            ArrayList<String> analyzeGroups = new ArrayList<String>();
            BufferedReader br = reader();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    fullFile += line + "\n";
                    continue;
                }
                int index = colonCheck(line);

                String option = line.substring(0, index).trim();
                String value = (line.substring(index + 1)).trim();

                if ((forcedOptions != null) && forcedOptions.containsKey(option)) {
                    value = forcedOptions.get(option).trim();
                    fullFile += option + ": " + value + "\n";
                } else
                    fullFile += line + "\n";

                if (options.containsKey(option))
                    throw new RuntimeException("Duplicate option detected: " + option);

                if (value.contains("group.")) {
                    for (String subArg : value.split(" ")) {
                        if (subArg.startsWith("group."))
                            analyzeGroups.add(option);
                    }
                }

                options.put(option, value);
            }
            br.close();
            addGroups(analyzeGroups);
            parsed = true;
            if (!path.delete())
                throw new RuntimeException("Unable to delete");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing configuration file", e);
        }
    }

    public void add(String option, String value) {
        if (options.containsKey(option))
            options.remove(option);
        options.put(option, value);
    }

    private BufferedReader reader() throws Exception {
        FileInputStream fis = new FileInputStream(path);
        DataInputStream dis = new DataInputStream(fis);
        return new BufferedReader(new InputStreamReader(dis));
    }

    private static int colonCheck(String s) {
        boolean value = s.contains(":");
        if (!value) throw new RuntimeException("Error: Invalid option detected at line " + s);
        return s.indexOf(":");
    }

    private void addGroups(ArrayList<String> analyze) {
        for (String s : analyze) {
            for (String subArg : options.get(s).split(" ")) {
                if (subArg.startsWith("group.")) {
                    String value = options.get(subArg).trim();
                    options.put(s, (value + " " + options.get(subArg)));
                }
            }
        }
    }

}
