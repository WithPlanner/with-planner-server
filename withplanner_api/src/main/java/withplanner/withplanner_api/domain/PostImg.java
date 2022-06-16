package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PostImg extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name="photo_idx")
    private Long id;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx")
    private Post post;
}
