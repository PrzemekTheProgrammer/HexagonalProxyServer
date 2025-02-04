package pszerszenowicz.HexagonalProxyServer.adapters;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ServerRepository;

public class WebSocketServer implements ServerRepository {

    private Channel serverChannel;

    @Override
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            ch.pipeline().addLast(new LoggingHandler());
                        }
                    });

            // Blokujące wywołanie bind() z synchronizacją
            serverChannel = bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Błąd podczas uruchamiania serwera", e);
        }
    }

    @Override
    public boolean isRunning() {
        return serverChannel != null && serverChannel.isActive();
    }

}
