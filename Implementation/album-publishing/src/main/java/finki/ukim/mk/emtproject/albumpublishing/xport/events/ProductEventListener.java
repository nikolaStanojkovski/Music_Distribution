package finki.ukim.mk.emtproject.albumpublishing.xport.events;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductEventListener {

//    private final ProductService productService;
//
//    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_ITEM_CREATED, groupId = "productCatalog")
//    public void consumeOrderItemCreatedEvent(String jsonMessage) {
//        try {
//            OrderItemCreated event = DomainEvent.fromJson(jsonMessage,OrderItemCreated.class);
//            productService.orderItemCreated(ProductId.of(event.getProductId()), event.getQuantity());
//        } catch (Exception e){
//
//        }
//
//    }
//
//    @KafkaListener(topics= TopicHolder.TOPIC_ORDER_ITEM_REMOVED, groupId = "productCatalog")
//    public void consumeOrderItemRemovedEvent(String jsonMessage) {
//        try {
//            OrderItemRemoved event = DomainEvent.fromJson(jsonMessage,OrderItemRemoved.class);
//            productService.orderItemRemoved(ProductId.of(event.getProductId()), event.getQuantity());
//        } catch (Exception e){
//
//        }
//
//    }
}
