package Animal_House_BE.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepositoryImplement implements ItemRepository{
    private String ITEMS_TABLE_NAME = "items";
    private String FAVORITE_ITEMS_TABLE_NAME = "favorite_items";
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Item getItemByNumber(int itemNumber) {
        String sql = "SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE item_id=?";
        return jdbcTemplate.queryForObject(sql, new ItemMapper(), itemNumber);


    }

    @Override
    public int getItemPrice(int itemNumber) {
        String sql = "SELECT item_price FROM " + ITEMS_TABLE_NAME + " WHERE item_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemNumber}, Integer.class);

    }

    @Override
    public int getItemQuantity(int itemNumber) {
        String sql = "SELECT item_quantity FROM " + ITEMS_TABLE_NAME + " WHERE item_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemNumber}, Integer.class);

    }

    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM " + ITEMS_TABLE_NAME;
        return jdbcTemplate.query(sql, new ItemMapper());
    }

    @Override
    public void createItem(Item item) {
        String sql = "INSERT INTO " + ITEMS_TABLE_NAME + "(item_name, item_description, item_picture_link, item_price, item_quantity) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                item.getItemName(), item.getItemDescription(), item.getItemPicture_link(), item.getItemPrice(), item.getItemQuantity()
        );
    }

    @Override
    public void reduceItemQunatity(int itemNumber) {
        int newQuantity = getItemQuantity(itemNumber) - 1;
        String sql = "UPDATE " + ITEMS_TABLE_NAME + " SET item_quantity = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, newQuantity, itemNumber);
    }

    @Override
    public void increaseItemQunatity(int itemNumber) {
        int newQuantity = getItemQuantity(itemNumber) + 1;
        String sql = "UPDATE " + ITEMS_TABLE_NAME + " SET item_quantity = ? WHERE item_id = ?";
        jdbcTemplate.update(sql, newQuantity, itemNumber);
    }

    ///FAVORITE ITEMS

    public List<Item> getFavoriteItemsListByClientId(int clientId) {
        List<Item> favoriteItems = new ArrayList<>();
        try {
            String sql = "SELECT item_id FROM " + FAVORITE_ITEMS_TABLE_NAME + " WHERE client_id=?";
            List<Integer> itemIdsList = jdbcTemplate.queryForList(sql, Integer.class, clientId);


            for (Integer itemId : itemIdsList) {
                try {
                    String itemQuery = "SELECT * FROM " + ITEMS_TABLE_NAME + " WHERE item_id=?";
                    Item item = jdbcTemplate.queryForObject(itemQuery, new ItemMapper(), itemId);
                    favoriteItems.add(item);
                } catch (EmptyResultDataAccessException ex) {
                    // No item found with the given itemId, continue to the next itemId
                    // This can happen if the item was deleted or doesn't exist
                    // You can choose to handle this case as needed
                }
            }

            return favoriteItems;
        } catch (EmptyResultDataAccessException ex) {
            // No favorite items found for the given clientId, return an empty list
            return favoriteItems;
        } catch (DataAccessException ex) {
            // Handle other data access exceptions if needed
            throw new RuntimeException("Failed to retrieve favorite items", ex);
        }
    }


    public void addToFavoriteItemList(int clientId, int itemId) {
        String sql = "INSERT INTO " + FAVORITE_ITEMS_TABLE_NAME + " (client_Id, item_Id) VALUES (?,?)";

        try {
            jdbcTemplate.update(sql, clientId, itemId);
        } catch (DataAccessException ex) {
            // Handle the exception or log the error
            throw new RuntimeException("Failed to create a favorite item in table", ex);
        }
    }

    public void deleteItemFromFavoriteItemList(int clientId, int itemId) {
        String sql = "DELETE FROM " + FAVORITE_ITEMS_TABLE_NAME + " WHERE client_id=? AND item_id=?";
        jdbcTemplate.update(sql, clientId, itemId);
    }


}
