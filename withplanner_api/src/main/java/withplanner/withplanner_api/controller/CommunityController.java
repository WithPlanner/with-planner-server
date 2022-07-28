package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.service.CommunityService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/make/loc")
    public void createMapCommunity(@RequestBody CommunityMakeReq reqDto) {

    }
}
