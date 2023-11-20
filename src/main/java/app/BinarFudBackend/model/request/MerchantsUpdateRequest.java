package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantsUpdateRequest {

    private String merchantCode;

    private String merchantName;

    private String merchantLocation;

    private String merchantStatus;

}
