package Animal_House_BE.item;

import Animal_House_BE.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class itemServiceImplement implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    ClientRepository clientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String CLIENTS_TABLE_NAME = "clients";

    @Override
    public Item getItemByNumber(int itemNumber) {
        return itemRepository.getItemByNumber(itemNumber);

    }

    @Override
    public int getItemPrice(int itenNumber) {
        return itemRepository.getItemPrice(itenNumber);
    }

    @Override
    public int getItemQuantity(int itemNumber) {
        return itemRepository.getItemQuantity(itemNumber);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    @Override
    public void createItem(Item item) {
        itemRepository.createItem(item);
    }

    @Override
    public void reduceItemQunatity(int itemNumber) {
        itemRepository.reduceItemQunatity(itemNumber);
    }

    @Override
    public void increaseItemQunatity(int itemNumber) {
        itemRepository.increaseItemQunatity(itemNumber);
    }

    @Override
    public List<Item> getFavoriteItemsListByClientEmail(String email) {
        String sql = "SELECT client_id FROM " + CLIENTS_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);

        return itemRepository.getFavoriteItemsListByClientId(clientId);
    }

    @Override
    public void addToFavoriteItemList(String email, int itemId) {
        String sql = "SELECT client_id FROM " + CLIENTS_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        itemRepository.addToFavoriteItemList(clientId, itemId);

    }

    public void deleteItemFromFavoriteItemList(String email, int itemId) {
        String sql = "SELECT client_id FROM " + CLIENTS_TABLE_NAME + " WHERE email=?";
        int clientId = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        itemRepository.deleteItemFromFavoriteItemList(clientId, itemId);

    }


}
