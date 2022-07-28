package withplanner.withplanner_api;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Category;
import withplanner.withplanner_api.domain.Community;
import withplanner.withplanner_api.domain.Type;
import withplanner.withplanner_api.dto.UserRequestDto;
import withplanner.withplanner_api.repository.CommunityRepository;
import withplanner.withplanner_api.repository.UserRepository;
import withplanner.withplanner_api.service.CommunityService;
import withplanner.withplanner_api.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {
    //테스트용 데이터를 입력하는 파일입니다.
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.communityInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final UserRepository userRepository;
        private final UserService userService;
        private final CommunityRepository communityRepository;

        //회원생성
        public void dbInit(){

            String email ="withplanner@sungshin.ac.kr";
            String pw= "qlqjs";
            String name = "위드플래너";
            String zipcode = "12345";
            String baseAddress = "서울시 성북구 성북동 1234번지";
            String detailedAddress="12동 1435호";
            String role = "ROLE_USER";

            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setEmail(email);
            userRequestDto.setPw(pw);
            userRequestDto.setName(name);
            userRequestDto.setZipcode(zipcode);
            userRequestDto.setBaseAddress(baseAddress);
            userRequestDto.setDetailedAddress(detailedAddress);

            userService.join(userRequestDto,role);

        }

        public void communityInit() {
            List<String> days = new ArrayList<>();
            days.add("월");
            days.add("화");
            Community community1 = Community.builder()
                    .name("미라클 모닝 화이팅")
                    .introduce("소개란입니다.")
                    .headCount(10)
                    .category(Category.miracleMorning)
                    .days(days)
                    .time("7시")
                    .type(Type.mapPost)
                    .build();

            Community community2 = Community.builder()
                    .name("독서 화이팅")
                    .introduce("소개란입니다.")
                    .headCount(10)
                    .category(Category.read)
                    .days(days)
                    .time("3시")
                    .type(Type.post)
                    .build();

            communityRepository.save(community1);
            communityRepository.save(community2);
        }


    }


}
