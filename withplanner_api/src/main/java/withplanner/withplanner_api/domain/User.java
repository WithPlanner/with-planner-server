package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import withplanner.withplanner_api.dto.UserRequestDto;


import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name ="users")
@NoArgsConstructor
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

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MapPost> mapPosts= new ArrayList<>();

    @OneToMany(mappedBy = "createUser")
    private List<Community> communities= new ArrayList<>();

    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "investigation_idx") //일대일관계의 경우 fk가 어디있어도 상관없지만 활용 빈도가 높은곳에 많이 넣음.
    private Investigation investigation;

    @OneToMany(mappedBy = "user")
    private List<CommunityMember> communityMembers= new ArrayList<>();

    public User(UserRequestDto userRequestDto) {
        this.email = userRequestDto.getEmail();
        this.pwd = userRequestDto.getPw();
        this.name = userRequestDto.getName();
        this.nickname = userRequestDto.getNickname();
        this.address = new Address(userRequestDto.getZipcode(), userRequestDto.getBaseAddress(), userRequestDto.getDetailedAddress());
    }

    public User(String email, String pwd, String name, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
    }

}
