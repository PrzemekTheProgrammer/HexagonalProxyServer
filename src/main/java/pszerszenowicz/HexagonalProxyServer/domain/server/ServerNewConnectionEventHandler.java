package pszerszenowicz.HexagonalProxyServer.domain.server;

import pszerszenowicz.HexagonalProxyServer.domain.ServerChannel;

public interface ServerNewConnectionEventHandler {
    void notifyNewConnection(ServerChannel serverChannel);
}
