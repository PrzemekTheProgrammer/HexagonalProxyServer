package pszerszenowicz.HexagonalProxyServer.domain.ports;

public interface ServerRepository {

    void start(int port) throws InterruptedException;
    boolean isRunning();
}
