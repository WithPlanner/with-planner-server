package withplanner.withplanner_api.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Location {
    private String roadAddress; //도로명 주소
    private String address; //지번 주소

    public Location(String roadAddress ,  String address){
        this.roadAddress = roadAddress;
        this.address = address;
    }

}
