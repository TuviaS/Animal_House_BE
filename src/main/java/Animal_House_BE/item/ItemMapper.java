package Animal_House_BE.item;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ItemMapper implements RowMapper <Item>{
    @Override
    public Item mapRow (ResultSet rs, int rowNum )throws SQLException{
    return new Item(
            rs.getInt("item_Id"),
            rs.getString("item_name"),
            rs.getString("item_description"),
            rs.getString("item_picture_link"),
            rs.getInt("item_price"),
            rs.getInt("item_quantity")
    );
    }}


