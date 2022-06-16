package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import javax.persistence.criteria.Order;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name="user_idx")
    private Long id;
}
