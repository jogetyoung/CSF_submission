package vttp.batch5.csf.assessment.server.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.stereotype.Repository;
import vttp.batch5.csf.assessment.server.models.FoodOrder;
import vttp.batch5.csf.assessment.server.models.MenuItems;

import java.util.Date;
import java.util.List;


@Repository
public class OrdersRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // TODO: Task 2.2
    // You may change the method's signature
    // Write the native MongoDB query in the comment below
    //
    //  Native MongoDB query here
    //

  /*
  db.menus.aggregate([
  {
    $project: {
      _id: 0,
      id: "$_id",
      name: 1,
      description: 1,
      price: 1
    }
  },
  {
    $sort: { name: 1 }
  }
])
  */

    public List<MenuItems> getMenu() {

        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.ASC, "name"));


        ProjectionOperation projectionOperation = Aggregation.project()
                .and("_id").as("id")
                .andInclude("name", "description", "price");

        Aggregation pipeline = Aggregation.newAggregation(sort, projectionOperation);

        return mongoTemplate.aggregate(pipeline, "menus", MenuItems.class).getMappedResults();

    }

    // TODO: Task 4
    // Write the native MongoDB query for your access methods in the comment below
    //
    //  Native MongoDB query here

    /*
    db.orders.insertOne({
    _id: <<id>>,
    order_id: << order id >>,
    payment_id: <<payment id>>,
    username: <<fred>>,
    total: <<total amount>>,
    timestamp: <<date>>,
    items: [
            { id: xxx, price: $xxx, quantity: xx}
            ...
            ]
    })
    */

    public boolean saveOrderToMongo(FoodOrder order, String orderId, String paymentId, double total, long timestamp) {

        try {

            List<Document> itemDocs = order.getItems().stream().map(item -> {
                Document d = new Document();
                d.append("id", item.getId());
                d.append("price", item.getPrice());
                d.append("quantity", item.getQuantity());
                return d;
            }).toList();

            Document doc = new Document()
                    .append("_id", orderId)
                    .append("order_id", orderId)
                    .append("payment_id", paymentId)
                    .append("username", order.getUsername())
                    .append("total", total)
                    .append("timestamp", new Date(timestamp))
                    .append("items", itemDocs);

            mongoTemplate.getCollection("orders").insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
