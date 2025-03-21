package vttp.batch5.csf.assessment.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vttp.batch5.csf.assessment.server.models.FoodOrder;
import vttp.batch5.csf.assessment.server.models.MenuItems;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping("/menu")
  public ResponseEntity<List<MenuItems>> getMenus() {

    List<MenuItems> menu = restaurantService.getMenu();

    return ResponseEntity.ok(menu);
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping("/food_order")
  public ResponseEntity<?> postFoodOrder(@RequestBody FoodOrder request) {

    return restaurantService.postFoodOrder(request);
  }
}
