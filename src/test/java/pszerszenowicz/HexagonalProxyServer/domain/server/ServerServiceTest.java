package pszerszenowicz.HexagonalProxyServer.domain.server;

import org.junit.Test;
import pszerszenowicz.HexagonalProxyServer.adapters.server.WebSocketServer;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ServerRepository;

import static org.junit.Assert.*;

public class ServerServiceTest {

    @Test
    public void testServerIsRunning() {
        //given
        ServerRepository serverRepository = new WebSocketServer(null);
        ServerService serverService = new ServerService(serverRepository);
        //when
        serverService.start(8080);
        //then
        assertTrue("Server isn't active", serverService.isRunning());
    }
}
