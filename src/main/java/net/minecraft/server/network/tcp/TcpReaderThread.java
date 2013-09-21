package net.minecraft.server.network.tcp;

import org.minetweak.util.ThreadUtils;

class TcpReaderThread extends Thread {
    final TcpConnection theTcpConnection;

    TcpReaderThread(TcpConnection par1TcpConnection, String par2Str) {
        super(par2Str);
        this.theTcpConnection = par1TcpConnection;
    }

    @Override
    public void run() {
        TcpConnection.field_74471_a.getAndIncrement();

        try {
            while (TcpConnection.isRunning(this.theTcpConnection) && !TcpConnection.isServerTerminating(this.theTcpConnection)) {
                while (true) {
                    if (!TcpConnection.readNetworkPacket(this.theTcpConnection)) {
                        ThreadUtils.sleepSafe(2L);
                    }
                }
            }
        } finally {
            TcpConnection.field_74471_a.getAndDecrement();
        }
    }
}
