package withplanner.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import withplanner.global.entity.BaseTimeEntity;
import withplanner.global.entity.Status;
import withplanner.global.dto.UserRequestDto;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String pwd;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String profileImg;

    private boolean emailAuth;

    private String recommend;

//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Community> communities= new ArrayList<>();

    private Long investigationId;

    //UserDetails 구현체 관련 코드 - 필수
    @ElementCollection(fetch = FetchType.EAGER) // 수정필요!
    @CollectionTable(joinColumns = @JoinColumn(name = "user_idx"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<CommunityMember> communityMembers= new ArrayList<>();

    public User(UserRequestDto userRequestDto, String role) {
        this.email = userRequestDto.getEmail();
        this.pwd = userRequestDto.getPw();
        this.name = userRequestDto.getName();
        this.nickname = userRequestDto.getNickname();
        this.emailAuth = true;
        this.roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void addInvestigation(Long investigationId) {
        this.investigationId = investigationId;
    }

    public void addRecommend(String category) {
        this.recommend = category;
    }

    //    public void addCommunity(Community community) {
//        communities.add(community);
//        //community.addUser(this); //양방향으로 매핑하면 주석 풀면 됩니다. 안할거면 삭제해도 무방
//    }

}
