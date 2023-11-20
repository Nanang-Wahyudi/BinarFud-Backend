package app.BinarFudBackend.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MerchantsCreateRequest {

    @NotBlank
    private String merchantCode;

    @NotBlank
    private String merchantName;

    @NotBlank
    private String merchantLocation;

    @NotBlank
    private String merchantStatus;


}
