package test.hunt_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.hunt_test.model.ApplicationResource;
import test.hunt_test.model.ApplicationResourceStatus;

import java.util.List;

@Repository
public interface ApplicationResourceRepository extends JpaRepository<ApplicationResource, Long> {
    @Query("SELECT ar FROM ApplicationResource ar WHERE ar.status = 'NEW' OR ar.status = 'PENDING' ORDER BY ar.createdAt ASC")
    List<ApplicationResource> findAllByOrderByApplicationDateOfCreationAsc();

    List<ApplicationResource> findAllByStatusAndApplication_Id(ApplicationResourceStatus status, Long applicationId);

    @Query("SELECT DISTINCT ar.resource.name FROM ApplicationResource ar")
    List<String> getAllDistinctName();


    @Query("SELECT DISTINCT ar.resource.district FROM ApplicationResource ar")
    List<String> getAllDistinctDistrict();

    @Query("SELECT ar FROM ApplicationResource ar WHERE ar.resource.name = ?1 AND ar.status = 'APPROVED' AND ar.application.id = ?2 AND ar.resource.district = ?3")
    List<ApplicationResource> getAllApprovedByResourceId(String resourceName, Long applicationId, String resourceDistrict);
}