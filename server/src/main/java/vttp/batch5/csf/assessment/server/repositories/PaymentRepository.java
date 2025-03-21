package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class PaymentRepository {

    private RestTemplate restTemplate = new RestTemplate();


    @Value("${payment.url}")
    private String PAYMENT_URL;

    public Map<String, Object> processPayment(String username, String orderId, double total) {
        Map<String, Object> paymentRequest = Map.of(
                "order_id", orderId,
                "payer", username,
                "payee", "Joel Young",
                "payment", total
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Authenticate", username);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentRequest, headers);

        System.out.println("Sending payment request: " + paymentRequest);

        try{
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(PAYMENT_URL, request, Map.class);

            System.out.println("Payment API Response: " + responseEntity.getBody());

            return responseEntity.getStatusCode().is2xxSuccessful() ? responseEntity.getBody() : null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
