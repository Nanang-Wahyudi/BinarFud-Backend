package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {

    private String productCode;

    private String productName;

    private double productPrice;

    private String merchantName;

    private String imageName;

}
