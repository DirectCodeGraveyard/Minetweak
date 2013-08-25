package org.minetweak.server;

import org.minetweak.util.Task;

public abstract class TickTask extends Task {
    public abstract int getTickInterval();
}
