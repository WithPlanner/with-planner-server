package withplanner.withplanner_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonResult<T> {
    private int count;
    private T data;
}
