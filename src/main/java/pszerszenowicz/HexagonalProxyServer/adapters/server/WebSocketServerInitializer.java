package pszerszenowicz.HexagonalProxyServer.adapters.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import pszerszenowicz.HexagonalProxyServer.domain.server.ServerNewConnectionObservator;

import java.util.HashSet;
import java.util.Set;

class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private Set<ServerNewConnectionObservator> newConnectionObservators = new HashSet<>();

    public WebSocketServerInitializer(Set<ServerNewConnectionObservator> newConnectionObservators) {
        this.newConnectionObservators = newConnectionObservators;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerProtocolHandler("/", null, true));
        pipeline.addLast(new WebSocketServerFrameHandler(newConnectionObservators));
    }
}