package pszerszenowicz.HexagonalProxyServer.adapters.client;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import pszerszenowicz.HexagonalProxyServer.domain.Message;
import pszerszenowicz.HexagonalProxyServer.domain.MessageType;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessagePublisher;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessageSubscriber;

import javax.net.ssl.SSLException;
import java.net.URISyntaxException;
import java.util.Set;

public class WebSocketClientHandler
        extends SimpleChannelInboundHandler<Object>
        implements NewMessagePublisher {
    private final WebSocketClientHandshaker handshaker;
    private Set<NewMessageSubscriber> newMessageSubscribers;

    public WebSocketClientHandler(WebSocketClientHandshaker handshaker, Set<NewMessageSubscriber> newMessageSubscribers) {
        this.handshaker = handshaker;
        this.newMessageSubscribers = newMessageSubscribers;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws InterruptedException, URISyntaxException, SSLException {
        System.out.println("WebSocketClient connection closed.");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                System.out.println("WebSocketClient connected to server.");
            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocketClient  failed to connect.");
            }
            return;
        }
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame textFrame) {
            String msgReceived = textFrame.text();
            System.out.println("WebSocketClient received message: " + msgReceived);
            notifyNewMessage(new Message(
                    msgReceived,
                    ctx.channel(),
                    MessageType.MESSAGE_FROM_CLIENT
            ));
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void notifyNewMessage(Message message) {
        newMessageSubscribers.forEach((o) -> o.handleNewMessage(message));
    }
}
