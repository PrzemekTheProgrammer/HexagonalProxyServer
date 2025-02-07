package pszerszenowicz.HexagonalProxyServer;

import io.netty.channel.Channel;
import pszerszenowicz.HexagonalProxyServer.adapters.client.WebSocketClient;
import pszerszenowicz.HexagonalProxyServer.adapters.server.WebSocketServer;
import pszerszenowicz.HexagonalProxyServer.domain.Message;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessageSubscriber;
import pszerszenowicz.HexagonalProxyServer.domain.client.ClientService;
import pszerszenowicz.HexagonalProxyServer.domain.server.ServerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Proxy implements NewMessageSubscriber {
    int port = 8080;

    private Map<Channel, Channel> serverChannels = new HashMap<>();
    private Map<Channel, Channel> clientChannels = new HashMap<>();

    private ClientService clientService
            = new ClientService(new WebSocketClient(Set.of(this)));
    private ServerService serverService
            = new ServerService(new WebSocketServer(Set.of(this)));

    public Proxy() {
        serverService.start(port);
    }

    @Override
    public void handleNewMessage(Message message) {
        switch (message.getMessageType()) {
            case NEW_CONNECTION -> {
                Channel serverChannel = message.getChannel();
                Channel clientChannel = clientService.connect(message.getMessage());
                if(serverChannel != null && clientChannel != null) {
                    serverChannels.put(clientChannel,serverChannel);
                    clientChannels.put(serverChannel,clientChannel);
                }
            }
            case MESSAGE_FROM_CLIENT -> {
                Channel serverChannel = serverChannels.get(message.getChannel());
                if (serverChannel != null) {
                    serverChannel.writeAndFlush(message.getMessage());
                }
            }
            case MESSAGE_FROM_SERVER -> {
                Channel clientChannel = clientChannels.get(message.getChannel());
                if(clientChannel != null) {
                    clientChannel.writeAndFlush(message.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
    }
}
