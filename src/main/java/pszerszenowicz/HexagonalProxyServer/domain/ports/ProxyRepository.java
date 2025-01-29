package pszerszenowicz.HexagonalProxyServer.domain.ports;

public interface ProxyRepository {
    default void sendMessageToClient(ClientRepository clientRepository, String message) {
        clientRepository.sendMessage(message);
    }
}
