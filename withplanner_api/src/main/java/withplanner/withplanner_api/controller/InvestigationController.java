package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import withplanner.withplanner_api.dto.investigation.InvestReq;
import withplanner.withplanner_api.service.InvestigationService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InvestigationController {

    private final InvestigationService investigationService;
    @PostMapping("/investigation")
    public String invest(@RequestBody InvestReq investReq) {
        return investigationService.invest(investReq);
    }
}
