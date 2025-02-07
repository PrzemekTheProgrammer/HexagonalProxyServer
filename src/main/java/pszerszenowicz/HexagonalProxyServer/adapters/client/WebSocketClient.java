package pszerszenowicz.HexagonalProxyServer.adapters.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ClientRepository;
import pszerszenowicz.HexagonalProxyServer.domain.NewMessageSubscriber;

import java.net.URI;
import java.util.Set;

public class WebSocketClient implements ClientRepository {

    private Set<NewMessageSubscriber> newMessageSubscribers;

    public WebSocketClient(Set<NewMessageSubscriber> newMessageSubscribers) {
        this.newMessageSubscribers = newMessageSubscribers;
    }

    @Override
    public Channel connect(URI uri) throws InterruptedException {
        Bootstrap bootstrap;
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new WebSocketClientInitializer(uri, newMessageSubscribers));
        ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
        return future.channel();
    }
}
