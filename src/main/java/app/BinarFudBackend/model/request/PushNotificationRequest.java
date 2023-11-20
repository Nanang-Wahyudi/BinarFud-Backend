package app.BinarFudBackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PushNotificationRequest {

    private String title;

    private String message;

    private String topic;

    private String token;

}
