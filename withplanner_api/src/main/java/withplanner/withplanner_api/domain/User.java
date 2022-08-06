package withplanner.withplanner_api.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import withplanner.withplanner_api.dto.UserRequestDto;


import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@Table(name ="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

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

    private String recommend;

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

    public User(UserRequestDto userRequestDto, String role) {
        this.email = userRequestDto.getEmail();
        this.pwd = userRequestDto.getPw();
        this.name = userRequestDto.getName();
        this.nickname = userRequestDto.getNickname();
        this.address = new Address(userRequestDto.getZipcode(), userRequestDto.getBaseAddress(), userRequestDto.getDetailedAddress());
        this.emailAuth = true;
        this.roles.add(role);
    }

    //UserDetails 구현체 관련 코드 - 필수
    @ElementCollection(fetch = FetchType.EAGER) // 수정필요!
    @CollectionTable(joinColumns = @JoinColumn(name = "user_idx"))
    @Column(name="role")
    private List<String> roles = new ArrayList<>();

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

    public void addCommunity(Community community) {
        communities.add(community);
        community.addUser(this);
    }

    public void addPost(Post post) {
        posts.add(post);
        post.addUser(this);
    }


}
