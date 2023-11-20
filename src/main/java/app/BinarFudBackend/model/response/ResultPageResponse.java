package app.BinarFudBackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultPageResponse<T> {

    private List<T> result;

    private Integer pageNumber;

    private Integer totalPages;

    private Long totalElements;

}
