package pszerszenowicz.HexagonalProxyServer.domain.ports;

import io.netty.channel.Channel;

public interface ServerRepository {

    void start(int port);
    boolean isRunning();

}
