package withplanner.withplanner_api.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainListResp {
    //회원님을 위한 습관 모임
    List<ListCardResp> recommendList;
    //회원님이 참여하는 습관 모임
    List<ListCardResp> myList;
    //가장 활성화된 습관 모임
    List<ListCardResp> hotList;
    //최신 습관 모임
    List<ListCardResp> newList;
}
