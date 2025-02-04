package pszerszenowicz.HexagonalProxyServer.domain.server;

import pszerszenowicz.HexagonalProxyServer.domain.ports.ServerRepository;

public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public void start(int port){
        serverRepository.start(port);
    }

    public boolean isRunning(){
        return serverRepository.isRunning();
    }

}
