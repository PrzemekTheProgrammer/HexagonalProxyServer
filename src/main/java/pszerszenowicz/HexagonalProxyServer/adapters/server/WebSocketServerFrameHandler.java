package pszerszenowicz.HexagonalProxyServer.adapters.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import pszerszenowicz.HexagonalProxyServer.domain.ServerChannel;
import pszerszenowicz.HexagonalProxyServer.domain.server.ServerNewConnectionEventHandler;
import pszerszenowicz.HexagonalProxyServer.domain.server.ServerNewConnectionObservator;

import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

public class WebSocketServerFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame>
        implements ServerNewConnectionEventHandler {

    private Set<ServerNewConnectionObservator> newConnectionObservators = new HashSet<>();

    public WebSocketServerFrameHandler(Set<ServerNewConnectionObservator> newConnectionObservators) {
        this.newConnectionObservators = newConnectionObservators;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        notifyNewConnection(new ServerChannelImpl(ctx.channel()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
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
    public void notifyNewConnection(ServerChannel serverChannel) {
        newConnectionObservators.forEach((o) -> {
            o.handleNewConnection(serverChannel);
        });
    }
}
