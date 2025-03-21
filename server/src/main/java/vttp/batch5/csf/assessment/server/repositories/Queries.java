package vttp.batch5.csf.assessment.server.repositories;

public class Queries {

    public static final String CHECK_USER = """
            SELECT COUNT(*) FROM customers WHERE username = ? AND password = SHA2(?, 224)
            """;

    public static final String SAVE_ORDER = """
            INSERT INTO place_orders (order_id, payment_id, order_date, total, username)
                        VALUES (?, ?, ?, ?, ?)
            """;
}
