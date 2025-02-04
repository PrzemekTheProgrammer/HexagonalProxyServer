package pszerszenowicz.HexagonalProxyServer.domain.ports;

public interface ServerRepository {

    void start(int port);
    boolean isRunning();

}
