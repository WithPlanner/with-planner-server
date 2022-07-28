package withplanner.withplanner_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import withplanner.withplanner_api.dto.community.CommunityMakeReq;
import withplanner.withplanner_api.service.CommunityService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping(value = "/make/loc", consumes = {"multipart/form-data"})
    public Long createMapCommunity(@ModelAttribute CommunityMakeReq reqDto) {
        return communityService.createMapCoummunity(reqDto);
    }
}
