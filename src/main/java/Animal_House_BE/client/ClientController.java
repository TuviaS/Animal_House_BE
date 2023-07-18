package Animal_House_BE.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;
    @PostMapping(value = "client/create")
    public void createClient (@RequestBody Client client)throws  Exception {
        clientService.createClient(client);
    }

    @GetMapping(value = "/client/get/{email}")
    public ClientResponse getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }

    @PutMapping (value = "client/update")
    public void updateClientDetails (@RequestBody Client client) throws Exception {
        clientService.updateClientDetails(client);
    }

    @DeleteMapping (value = "/client/delete/{clientEmail}")
    public void deleteClient (@PathVariable String clientEmail)throws Exception{
        clientService.deleteClient (clientEmail);
    }


}
