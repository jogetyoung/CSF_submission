package vttp.batch5.csf.assessment.server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vttp.batch5.csf.assessment.server.models.FoodOrder;
import vttp.batch5.csf.assessment.server.models.MenuItems;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.PaymentRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // TODO: Task 2.2
    // You may change the method's signature
    public List<MenuItems> getMenu() {

        List<MenuItems> menuItems = ordersRepository.getMenu();

        return menuItems;
    }

    // TODO: Task 4
    public ResponseEntity<?> postFoodOrder(FoodOrder foodOrder) {

        if (!restaurantRepository.isValidUser(foodOrder.getUsername(), foodOrder.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username and/or password"));
        }

        String orderId = UUID.randomUUID().toString().substring(0, 8);

        double total = foodOrder.getItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        Map<String, Object> paymentReceipt = paymentRepository.processPayment(foodOrder.getUsername(), orderId, total);

        if (paymentReceipt == null) {
            return ResponseEntity.status(500).body(Map.of("message", "Payment failed"));
        }

        String paymentId = (String) paymentReceipt.get("payment_id");
        long timestamp = (long) paymentReceipt.get("timestamp");

        boolean mysqlSaved = restaurantRepository.saveOrder(foodOrder, orderId, paymentId, total);
        boolean mongoSaved = ordersRepository.saveOrderToMongo(foodOrder, orderId, paymentId, total, timestamp);

        if (!mysqlSaved || !mongoSaved) {
            return ResponseEntity.status(500).body(Map.of("message", "Failed to save order"));
        }

        return ResponseEntity.ok(Map.of(
                "orderId", orderId,
                "paymentId", paymentId,
                "total", total,
                "timestamp", timestamp
        ));


    }


}
