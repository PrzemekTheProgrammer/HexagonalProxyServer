package pszerszenowicz.HexagonalProxyServer.adapters;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ClientRepository;

public class ClientWebSocketAdapter implements ClientRepository {

    private Channel channel;

    @Override
    public void sendMessage(String message) {
        TextWebSocketFrame toSend = new TextWebSocketFrame(message);
        channel.writeAndFlush(toSend);
    }
}
