package app.BinarFudBackend.model;

import app.BinarFudBackend.model.enumeration.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "merchant_code", length = 15)
    private String merchantCode;

    @Column(name = "merchant_name", length = 50)
    private String merchantName;

    @Column(name = "merchant_location", length = 150)
    private String merchantLocation;

    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;

    @OneToMany(mappedBy = "merchants", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Products> products;

}
