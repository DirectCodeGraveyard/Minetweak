package org.minetweak.thread;

import org.minetweak.dependencies.DependencyManager;

public class DependencyThread extends Thread {

    private static DependencyThread instance = new DependencyThread();

    public DependencyThread() {
        this.start();
    }

    @Override
    public void run() {
        DependencyManager.createDependenciesFolder();
        DependencyManager.updateList();
        DependencyManager.readJson();
    }

    public static DependencyThread getInstance() {
        return instance;
    }

}
