package app.BinarFudBackend.model;

import app.BinarFudBackend.model.enumeration.OrderPhase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "destination_address", length = 150)
    private String destinationAddress;

    @Enumerated(EnumType.STRING)
    private OrderPhase orderPhase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;


    public void addOrderDetail(OrderDetails orderDetail) {
        if (orderDetails == null) {
            orderDetails = new ArrayList<>();
        }
        orderDetails.add(orderDetail);
        orderDetail.setOrders(this);
    }

}
