package withplanner.domain.calendar.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import withplanner.domain.user.model.User;
import withplanner.global.entity.BaseTimeEntity;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "calendars")
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String year;

    private String month;

    private String day;

    private Long userId;

    private Long communityId;
}