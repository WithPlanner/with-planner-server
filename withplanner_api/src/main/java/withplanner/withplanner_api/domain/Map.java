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

    private String x ; //경도
    private String y ; //위도

    private Address address; //주소




}
