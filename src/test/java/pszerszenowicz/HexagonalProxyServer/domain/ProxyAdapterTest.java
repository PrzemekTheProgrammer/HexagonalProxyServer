package pszerszenowicz.HexagonalProxyServer.domain;

import org.junit.Test;
import pszerszenowicz.HexagonalProxyServer.adapters.ProxyAdapter;
import pszerszenowicz.HexagonalProxyServer.domain.ports.ClientRepository;

import static org.mockito.Mockito.*;

public class ProxyAdapterTest {

    @Test
    public void sendMessageToClientTest(){
        //given
        ClientRepository clientRepository = mock(ClientRepository.class);
        ProxyAdapter proxyAdapter = new ProxyAdapter();
        //when
        doNothing().when(clientRepository).sendMessage(any());
        proxyAdapter.sendMessageToClient(clientRepository, "Wiadomość Testowa");
        //then
        verify(clientRepository, times(1)).sendMessage(any());
    }

}
