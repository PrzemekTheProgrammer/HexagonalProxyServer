package pszerszenowicz.HexagonalProxyServer.domain;

public interface NewMessageSubscriber {
    void handleNewMessage(Message message);
}
