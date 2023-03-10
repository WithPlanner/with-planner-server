package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MapPost extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name="map_post_idx")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_idx")
    private Community community;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();


    //--연관관계 편의 메소드--//
    public void connectUser(User user) {
        if(this.user != null) {
            this.user.getMapPosts().remove(this);
        }
        this.user = user;
        if(!user.getMapPosts().contains(this)) {
            user.getMapPosts().add(this);
        }
    }

    public void connectCommunity(Community community){
        if(this.community!=null){
            this.community.getMapPosts().remove(this);
        }
        this.community = community;
        if(!community.getMapPosts().contains(this)){
            community.getMapPosts().add(this);
        }
    }

}
