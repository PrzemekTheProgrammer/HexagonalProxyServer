package pszerszenowicz.HexagonalProxyServer.domain.server;

import pszerszenowicz.HexagonalProxyServer.domain.ports.ServerRepository;

public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public void start(int port){
        try {
            serverRepository.start(port);
        } catch (InterruptedException e) {
            System.err.println("Server didn't start");
        }
    }

    public boolean isRunning(){
        return serverRepository.isRunning();
    }

}
