package pszerszenowicz.HexagonalProxyServer.adapters.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessageSubscriber;

import java.net.URI;
import java.util.Set;

public class WebSocketClientInitializer extends ChannelInitializer<SocketChannel> {

    private URI uri;
    private Set<NewMessageSubscriber> newMessageSubscribers;

    public WebSocketClientInitializer(URI uri, Set<NewMessageSubscriber> newMessageSubscribers) {
        this.uri = uri;
        this.newMessageSubscribers = newMessageSubscribers;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpClientCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketClientHandler(
                WebSocketClientHandshakerFactory.newHandshaker(
                        uri, WebSocketVersion.V13, null, true, null),
                newMessageSubscribers));
    }
}
