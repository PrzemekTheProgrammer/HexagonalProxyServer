package pszerszenowicz.HexagonalProxyServer.domain;

public interface NewMessagePublisher {
    void notifyNewMessage(Message message);
}
