package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantsResponse {

    private String merchantCode;

    private String merchantName;

    private String merchantLocation;

    private String merchantStatus;

}
