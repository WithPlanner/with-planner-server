package withplanner.withplanner_api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {
    private static final Long MAX_EXPIRE_TIME = 5L;//5분

    @Id
    private String email;
    private int authNumber;
    private Boolean expired;
    private LocalDateTime expireDate;

    @Builder
    public EmailAuth(String email, int authNumber, Boolean expired) {
        this.email = email;
        this.authNumber = authNumber;
        this.expired = expired;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken() {
        this.expired = true;
    }
}
