package net.minecraft.server.network.tcp;

@SuppressWarnings("deprecation")
class TcpMasterThread extends Thread {
    final TcpConnection theTcpConnection;

    TcpMasterThread(TcpConnection par1TcpConnection) {
        this.theTcpConnection = par1TcpConnection;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);

            if (TcpConnection.getReadThread(this.theTcpConnection).isAlive()) {
                try {
                    TcpConnection.getReadThread(this.theTcpConnection).stop();
                } catch (Throwable ignored) {

                }
            }

            if (TcpConnection.getWriteThread(this.theTcpConnection).isAlive()) {
                try {
                    TcpConnection.getWriteThread(this.theTcpConnection).stop();
                } catch (Throwable ignored) {

                }
            }
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
    }
}
