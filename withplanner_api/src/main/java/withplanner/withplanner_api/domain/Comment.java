package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name="comment_idx")
    private Long id;
}
