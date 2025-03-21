package vttp.batch5.csf.assessment.server.models;

import java.util.List;

public class FoodOrder {

    private String username;

    private String password;

    private List<OrderItems> items;

    public FoodOrder() {
    }

    public FoodOrder(String username, String password, List<OrderItems> items) {
        this.username = username;
        this.password = password;
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }
}
