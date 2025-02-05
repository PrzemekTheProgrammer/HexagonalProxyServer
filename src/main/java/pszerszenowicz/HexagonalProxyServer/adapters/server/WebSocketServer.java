package pszerszenowicz.HexagonalProxyServer.adapters.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ServerRepository;
import pszerszenowicz.HexagonalProxyServer.domain.server.ServerNewConnectionObservator;

import java.util.HashSet;
import java.util.Set;

public class WebSocketServer implements ServerRepository {

    private Channel serverChannel;
    private Set<ServerNewConnectionObservator> newConnectionObservators;

    public WebSocketServer(Set<ServerNewConnectionObservator> newConnectionObservators) {
        this.newConnectionObservators = newConnectionObservators;
    }

    @Override
    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketServerInitializer(newConnectionObservators));
            ChannelFuture f = b.bind(port).sync();
            serverChannel = f.channel();
            serverChannel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public boolean isRunning() {
        return serverChannel != null && serverChannel.isActive();
    }

}
