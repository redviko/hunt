package test.hunt_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.hunt_test.model.Application;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = "SELECT DISTINCT ON (hunt_ticket_series,hunt_ticket_number) * FROM application", nativeQuery = true)
    List<Application> getDistinctByHuntTicketNumberAndHuntTicketSeries();


    @Query("SELECT DISTINCT a.fullName FROM Application a")
    List<String> getDistinctFullName();
}
