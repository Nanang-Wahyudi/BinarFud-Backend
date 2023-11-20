package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse {

    private String username;

    private String productName;

    private String destinationAddress;

    private int quantity;

    private double totalPrice;

    private LocalDateTime orderTime;

    private String orderPhase;

}
