package withplanner.withplanner_api.service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import withplanner.withplanner_api.dto.ResultMsgResp;
import withplanner.withplanner_api.dto.investigation.InvestReq;

@Service
@RequiredArgsConstructor
public class InvestigationService {

    public ResultMsgResp invest(InvestReq investReq) {
        String url = "http://localhost:5000/invest";

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        JsonObject json = new JsonObject();
        json.addProperty("q1", investReq.getQ1());
        json.addProperty("q2", investReq.getQ2());
        json.addProperty("q3", investReq.getQ3());
        json.addProperty("q4", investReq.getQ4());
        json.addProperty("q5", investReq.getQ5());
        json.addProperty("q6", investReq.getQ6());
        json.addProperty("q7", investReq.getQ7());
        json.addProperty("q8", investReq.getQ8());

        String body = json.toString();

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET, requestEntity, String.class);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();

        String response = responseEntity.getBody(); //빋은 키테고리로 랜덤한 방 리스트를 저장해줌(메인에서 리스팅)
        System.out.println("Response status: " + status);
        System.out.println("response = " + response);

        return new ResultMsgResp("설문조사에 응해주셔서 감사합니다.", true);
    }
}
