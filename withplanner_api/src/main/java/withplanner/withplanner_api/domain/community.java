package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class community {
    @Id
    @GeneratedValue
    @Column(name="community_idx")
    private Long id;

    //createdAt
    //updatedAt


    private String name;
    private String introduce;
    private String communityImg;

    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer headCount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private List<String> day = new ArrayList();

    private String time; //인증시간

    @Enumerated(EnumType.STRING)
    private Type type;




}
