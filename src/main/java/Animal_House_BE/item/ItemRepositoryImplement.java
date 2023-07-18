package Animal_House_BE.item;
import Animal_House_BE.item.ItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImplement implements ItemRepository{
    private String ITEMS_TABLE_NAME = "items";
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
}
