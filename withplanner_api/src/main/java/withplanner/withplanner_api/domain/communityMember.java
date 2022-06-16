package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class communityMember {
    @Id
    @GeneratedValue
    @Column(name="community_member_idx")
    private Long id;

    //fk userIdx
    //fk communityIdx
    //joinAt

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean authority; // true(방장)&false(멤버)


}
