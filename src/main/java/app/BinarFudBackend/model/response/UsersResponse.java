package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {

    private String dtoUsername;
    private String dtoEmail;
    private String dtoPassword;
    private String dtoRole;

}
