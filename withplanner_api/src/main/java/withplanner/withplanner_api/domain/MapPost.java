package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
