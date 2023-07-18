package Animal_House_BE.client;

public interface ClientRepository {
    void createClient(Client client);
    ClientResponse getClientByEmail (String email);
    void updateClientDetails (Client client);
    void deleteClient (String email);
}
