package pszerszenowicz.HexagonalProxyServer.domain.ports;

import io.netty.channel.Channel;

import java.net.URI;

public interface ClientRepository {
    Channel connect(URI uri) throws InterruptedException;
}
