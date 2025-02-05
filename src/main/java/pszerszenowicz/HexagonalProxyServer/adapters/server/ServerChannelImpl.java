package pszerszenowicz.HexagonalProxyServer.adapters.server;

import io.netty.channel.Channel;
import pszerszenowicz.HexagonalProxyServer.domain.ServerChannel;

public class ServerChannelImpl implements ServerChannel {
    private Channel channel;

    public ServerChannelImpl(Channel channel) {
        this.channel = channel;
    }
}
