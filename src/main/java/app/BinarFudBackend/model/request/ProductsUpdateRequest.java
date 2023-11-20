package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductsUpdateRequest {

    private String productCode;

    private String productName;

    private Double productPrice;

    private String imageName;

    private String merchantCode;

}
