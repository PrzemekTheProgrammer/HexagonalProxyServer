package pszerszenowicz.HexagonalProxyServer.domain.ports;

public interface ClientRepository {
    void sendMessage(String message);
}
