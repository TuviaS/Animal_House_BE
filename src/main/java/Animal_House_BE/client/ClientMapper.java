package Animal_House_BE.client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper <Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Client(

                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("address"));

    };
}

