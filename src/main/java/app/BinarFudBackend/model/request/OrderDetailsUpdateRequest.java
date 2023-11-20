package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class OrderDetailsUpdateRequest {

    private String username;

    private String productName;

    private String destinationAddress;

    private int quantity;

    private String orderPhase;

}
