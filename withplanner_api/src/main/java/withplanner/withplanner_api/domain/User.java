package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import javax.persistence.criteria.Order;

@Entity
@Getter
@Setter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="user_idx")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String pwd;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String profileImg;

    private boolean emailAuth;

    @Embedded
    private Address address;
}
