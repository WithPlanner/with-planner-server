package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    @Column(name="post_idx")
    private Long id;


    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();


}
