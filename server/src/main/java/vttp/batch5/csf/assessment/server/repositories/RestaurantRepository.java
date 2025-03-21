package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp.batch5.csf.assessment.server.models.FoodOrder;

import java.sql.Date;
import java.time.LocalDate;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isValidUser(String username, String password) {
        Integer count = jdbcTemplate.queryForObject(Queries.CHECK_USER, Integer.class, username, password);
        return count != null && count > 0;
    }

    public boolean saveOrder(FoodOrder order, String orderId, String paymentId, double total) {
        int rowsAffected = jdbcTemplate.update(Queries.SAVE_ORDER, orderId, paymentId, Date.valueOf(LocalDate.now()), total, order.getUsername());
        return rowsAffected == 1;
    }

}
