package Animal_House_BE.client;

public interface ClientService {
    void createClient (Client client);
    ClientResponse getClientByEmail (String email);
    void updateClientDetails (Client client);
    void deleteClient (String clientEmail);


}
