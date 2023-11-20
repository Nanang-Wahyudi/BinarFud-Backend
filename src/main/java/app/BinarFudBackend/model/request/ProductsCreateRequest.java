package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ProductsCreateRequest {

    @NotBlank
    private String productCode;

    @NotBlank
    private String productName;

    @NotBlank
    private Double productPrice;

    @NotBlank
    private String imageName;

    @NotBlank
    private String merchantCode;

}
