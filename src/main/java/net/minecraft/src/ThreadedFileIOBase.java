package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable {
    /**
     * Instance of ThreadedFileIOBase
     */
    public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
    private List<IThreadedFileIO> threadedIOQueue = Collections.synchronizedList(new ArrayList<IThreadedFileIO>());
    private volatile long writeQueuedCounter;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;

    private ThreadedFileIOBase() {
        Thread thread = new Thread(this, "File IO Thread");
        thread.setPriority(1);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            this.processQueue();
        }
    }

    /**
     * Process the items that are in the queue
     */
    private void processQueue() {
        for (int i = 0; i < this.threadedIOQueue.size(); ++i) {
            IThreadedFileIO var2 = this.threadedIOQueue.get(i);
            boolean didWrite = var2.writeNextIO();

            if (!didWrite) {
                this.threadedIOQueue.remove(i--);
                ++this.savedIOCounter;
            }

            try {
                Thread.sleep(this.isThreadWaiting ? 0L : 10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.threadedIOQueue.isEmpty()) {
            try {
                Thread.sleep(25L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * threaded io
     */
    public void queueIO(IThreadedFileIO threadedFileIO) {
        if (!this.threadedIOQueue.contains(threadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(threadedFileIO);
        }
    }

    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;

        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }

        this.isThreadWaiting = false;
    }
}
