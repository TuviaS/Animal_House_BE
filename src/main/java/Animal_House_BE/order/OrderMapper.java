package Animal_House_BE.order;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements  RowMapper <Order>{
    @Override
    public Order mapRow (ResultSet rs, int rowNum) throws SQLException {
        return new Order (
                rs.getString("order_date"),
                rs.getInt("client_id"),
                rs.getString("item_id_list"),
                rs.getString("delivery_address"),
                rs.getInt("total_price"),
                OrderStatus.valueOf(rs.getString("order_status")));

    }
}
