package pszerszenowicz.HexagonalProxyServer.adapters.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import pszerszenowicz.HexagonalProxyServer.domain.Message;
import pszerszenowicz.HexagonalProxyServer.domain.MessageType;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessagePublisher;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessageSubscriber;

import java.net.SocketException;
import java.util.Set;

class WebSocketServerFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame>
        implements NewMessagePublisher {

    private Set<NewMessageSubscriber> newMessageSubscribers;

    public WebSocketServerFrameHandler(Set<NewMessageSubscriber> newMessageSubscribers) {
        this.newMessageSubscribers = newMessageSubscribers;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            if(request.toLowerCase().startsWith("connect")) {
                notifyNewMessage(new Message(
                        request,
                        ctx.channel(),
                        MessageType.NEW_CONNECTION
                ));
            }
            System.out.println("Server got request: \n" + request + "\n");
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof SocketException && "Connection reset".equals(cause.getMessage())) {
            System.out.println("Connection was closed by client.");
        } else {
            cause.printStackTrace();
        }
        ctx.close();
    }

    @Override
    public void notifyNewMessage(Message message) {
        newMessageSubscribers.forEach((o) -> o.handleNewMessage(message));
    }
}
