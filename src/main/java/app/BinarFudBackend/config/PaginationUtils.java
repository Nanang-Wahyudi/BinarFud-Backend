package app.BinarFudBackend.config;

import app.BinarFudBackend.model.response.ResultPageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtils {

    public static <T> ResultPageResponse<T> createResultPageDTO(List<T> responses, Long totalElements, Integer pages, Integer pageNumber){
        ResultPageResponse<T> result = new ResultPageResponse<>();
        result.setTotalPages(pages);
        result.setTotalElements(totalElements);
        result.setResult(responses);
        result.setPageNumber(pageNumber);
        return result;
    }

    public static Sort.Direction getSortBy(String sortBy){
        if(sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        }else {
            return Sort.Direction.DESC;
        }
    }

}
