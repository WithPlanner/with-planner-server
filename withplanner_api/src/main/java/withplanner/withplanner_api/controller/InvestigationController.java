package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import withplanner.withplanner_api.dto.ResultMsgResp;
import withplanner.withplanner_api.dto.investigation.InvestReq;
import withplanner.withplanner_api.exception.BaseResponse;
import withplanner.withplanner_api.service.InvestigationService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InvestigationController {

    private final InvestigationService investigationService;
    @PostMapping("/investigation")
    public BaseResponse<ResultMsgResp> invest(@RequestBody InvestReq investReq) {
        ResultMsgResp result = investigationService.invest(investReq);
        return new BaseResponse<>(result);
    }
}
