package Animal_House_BE.item;

import Animal_House_BE.item.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper <Item>{
    @Override
    public Item mapRow (ResultSet rs, int rowNum )throws SQLException{
    return new Item (
            rs.getString("item_name"),
            rs.getString("item_description"),
            rs.getString("item_picture_link"),
            rs.getInt("item_price"),
            rs.getInt("item_quantity")
    );
    }}


