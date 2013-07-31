package org.minetweak.dependencies;

import java.util.ArrayList;

public class DependencyConfig {
    public ArrayList<Dependency> dependencies;

    class Dependency {
        public String name;
        public String currentVersion;
        public String url;
    }
}