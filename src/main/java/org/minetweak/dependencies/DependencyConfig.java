package org.minetweak.dependencies;

import java.util.ArrayList;

/**
 * Dependency Configurations
 */
public class DependencyConfig {
    public boolean verbose = false;
    public ArrayList<Dependency> dependencies;

    class Dependency {
        public String name;
        public String groupId;
        public String version;
        public String sha1;
        public String url;
    }
}