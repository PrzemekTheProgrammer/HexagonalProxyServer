package pszerszenowicz.HexagonalProxyServer.domain.client;

import io.netty.channel.Channel;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ClientRepository;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Channel connect(String path) {
        URI uri = null;
        try {
            uri = new URI(path);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            return clientRepository.connect(uri);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
