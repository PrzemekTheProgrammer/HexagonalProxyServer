package pszerszenowicz.HexagonalProxyServer.domain.server;

import pszerszenowicz.HexagonalProxyServer.domain.ServerChannel;

public interface ServerNewConnectionObservator {
    void handleNewConnection(ServerChannel serverChannel);
}
