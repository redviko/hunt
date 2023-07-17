package test.hunt_test.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "application_type")
    @Enumerated(EnumType.STRING)
    private ApplicationType applicationType;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "hunt_ticket_series")
    private Long huntTicketSeries;

    @Column(name = "hunt_ticket_number")
    private Long huntTicketNumber;

   /* @Column(name = "created_at")
    private LocalDate dateOfCreation;*/
}


