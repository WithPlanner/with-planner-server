package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Map {
    @Id
    @GeneratedValue
    @Column(name="map_idx")
    private Long id;
}
