package Animal_House_BE.client;

import Animal_House_BE.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplement implements ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void createClient(Client client) {
        clientRepository.createClient (client);
    }

    @Override
    public ClientResponse getClientByEmail(String email) {
        return clientRepository.getClientByEmail(email);

    }

    @Override
    public void updateClientDetails(Client client) {
        clientRepository.updateClientDetails(client);

    }

    @Override
    public void deleteClient(String clientEmail) {
        clientRepository.deleteClient(clientEmail);


    }
}
