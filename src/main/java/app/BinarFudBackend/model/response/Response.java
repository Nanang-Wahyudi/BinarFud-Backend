package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private T data;
    private String successMessage;
    private Boolean isSuccess;
    private ErrorResponse errorResponse;

}
