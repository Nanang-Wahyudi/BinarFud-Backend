package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class OrderDetailsCreateRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String productName;

    @NotBlank
    private String destinationAddress;

    @NotBlank
    private Integer quantity;

}
