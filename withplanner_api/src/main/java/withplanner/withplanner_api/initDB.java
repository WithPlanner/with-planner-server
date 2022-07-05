package withplanner.withplanner_api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import withplanner.withplanner_api.domain.Post;
import withplanner.withplanner_api.domain.PostImg;
import withplanner.withplanner_api.domain.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDB {
    private final EntityManager em;
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            String pw = passwordEncoder.encode("aaaa");

            User user1 = new User("user1@test.com", pw, "김이름", "닉네임1");
            User user2 = new User("test@sungshin.ac.kr", pw, "손이름", "닉네임2");
            User user3 = new User("user3@test.com", pw, "막이름", "닉네임3");

            em.persist(user1);
            em.persist(user2);
            em.persist(user3);

            em.flush();
            em.clear();
        }
    }
}
