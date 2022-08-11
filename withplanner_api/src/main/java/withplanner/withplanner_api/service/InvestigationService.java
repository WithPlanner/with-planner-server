package withplanner.withplanner_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import withplanner.withplanner_api.domain.Investigation;
import withplanner.withplanner_api.domain.User;
import withplanner.withplanner_api.dto.ResultMsgResp;
import withplanner.withplanner_api.dto.investigation.InvestReq;
import withplanner.withplanner_api.exception.BaseException;
import withplanner.withplanner_api.exception.BaseResponseStatus;
import withplanner.withplanner_api.repository.InvestigationRepository;
import withplanner.withplanner_api.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class InvestigationService {
    private final UserRepository userRepository;
    private final InvestigationRepository investigationReposiotory;

    @Transactional
    public ResultMsgResp invest(InvestReq investReq) {
        String url = "http://hongseos.shop/invest?";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        //추천 모델에 전달할 데이터 쿼리 파라미터로 추가(json 오류나서 수정했음 나중에 리팩토링 필요)
        url = url + "q1=" + investReq.getQ1() + "&" + "q2=" + investReq.getQ2() + "&" + "q3=" + investReq.getQ3() + "&" + "q4=" + investReq.getQ4() + "&"
                + "q5=" + investReq.getQ5() + "&" + "q6=" + investReq.getQ6() + "&" + "q7=" + investReq.getQ7() + "&" + "q8=" + investReq.getQ8();

        HttpEntity<String> requestEntity = new HttpEntity<String>("{}", headers);

        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String response = responseEntity.getBody(); //추천 카테고리 결과


        Investigation investigation = Investigation.builder()
                .q1(investReq.getQ1())
                .q2(investReq.getQ2())
                .q3(investReq.getQ3())
                .q4(investReq.getQ4())
                .q5(investReq.getQ5())
                .q6(investReq.getQ6())
                .q7(investReq.getQ7())
                .q8(investReq.getQ8())
                .build();

        User user = userRepository.findById(investReq.getUserIdx()).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        // 설문조사 내용 저장
        investigationReposiotory.save(investigation);
        //연관관계 매핑
        user.addRecommend(response);
        user.addInvestigation(investigation);

        return new ResultMsgResp("설문조사에 응해주셔서 감사합니다.", true);
    }
}
