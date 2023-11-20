package app.BinarFudBackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_code", length = 15, unique = true)
    private String productCode;

    @Column(name = "product_name", length = 50)
    private String productName;

    @Column(name = "product_price")
    private Double productPrice;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchants merchants;

    @ManyToOne
    @JoinColumn(name = "id_image")
    private ImageData imageData;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;

}
