package test.hunt_test;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import test.hunt_test.model.*;
import test.hunt_test.service.ApplicationResourceService;
import test.hunt_test.utils.DistributionSync;

import java.time.LocalDate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@Transactional
//@DataJpaTest
//@Transactional
public class DistributionTest {
    @Autowired
    private DistributionSync distributionSync;
    @Autowired
    private ApplicationResourceService applicationResourceService;


//    @BeforeEach
//    public void init() {
//        Application application = new Application(1L, "Тестовое Имя Фамилия", ApplicationType.MASS, LocalDate.of(2022, 1, 1), 321L, 123L);
//        entityManager.persist(application);
//    }


    @Test
    public void startDistributionTest() {
        applicationResourceService.startDistribution();
        assert distributionSync.isDistributing();
    }

    @Test
    public void stopDistributionTest() {
        applicationResourceService.startDistribution();
        assert distributionSync.isDistributing();
        applicationResourceService.stopDistribution();
        assert !distributionSync.isDistributing();
    }

    @Test
    public void distributeTest() {
        applicationResourceService.startDistribution();
        assert distributionSync.isDistributing();
    }

    @Test
    public void positiveCheckApplicationDatesTest() {
        ApplicationResource testApplicationResource = new ApplicationResource();
        testApplicationResource.setId(1L);
        testApplicationResource.setCreatedAt(LocalDate.of(2023, 1, 1));
        testApplicationResource.setRequestedQuota(100);

        Resource resourceTest = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 2));

        assert applicationResourceService.checkApplicationDates(resourceTest, testApplicationResource);
    }

    @Test
    public void negativeCheckApplicationDatesTest() {
        ApplicationResource testApplicationResource = new ApplicationResource();
        testApplicationResource.setId(1L);
        testApplicationResource.setCreatedAt(LocalDate.of(2023, 1, 3));
        testApplicationResource.setRequestedQuota(100);

        Resource resourceTest = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 2));

        assert !applicationResourceService.checkApplicationDates(resourceTest, testApplicationResource);
    }

    @Test
    public void positiveCheckApplicationQuotaAndDistrictTest() {
        ApplicationResource testApplicationResource = new ApplicationResource();
        testApplicationResource.setId(1L);
        testApplicationResource.setCreatedAt(LocalDate.of(2023, 1, 1));
        testApplicationResource.setRequestedQuota(100);

        Resource resourceTest = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 2));

        Application applicationTest = new Application(1L, "Абоба Амогусович Абобов", ApplicationType.MASS, LocalDate.of(2023, 1, 1), 23L, 321L);

        assert applicationResourceService.checkApplicationQuotaWithDistrict(applicationTest, resourceTest, testApplicationResource);
    }

    public void negativeCheckApplicationQuotaAndDistrictTest() {
        ApplicationResource testApplicationResource = new ApplicationResource();
        testApplicationResource.setId(1L);
        testApplicationResource.setCreatedAt(LocalDate.of(2023, 1, 1));
        testApplicationResource.setRequestedQuota(110);

        Resource resourceTest = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 2));

        Application applicationTest = new Application(1L, "Абоба Амогусович Абобов", ApplicationType.MASS, LocalDate.of(2023, 1, 1), 23L, 321L);

        assert !applicationResourceService.checkApplicationQuotaWithDistrict(applicationTest, resourceTest, testApplicationResource);
    }


    @Test
    public void positiveCheckApplication() {
        Application testApplication = new Application(1L, "Абоба Амогусович Абобов", ApplicationType.MASS, LocalDate.of(2023, 1, 1), 23L, 321L);
        Resource testResource = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 2));
        ApplicationResource testApplicationResource = new ApplicationResource(1L, testApplication, testResource, 100, ApplicationResourceStatus.NEW, LocalDate.of(2023, 1, 1));

        assert applicationResourceService.checkApplication(testApplication, testResource, testApplicationResource);
    }

    @Test
    public void negativeCheckApplication() {
        Application testApplication = new Application(1L, "Абоба Амогусович Абобов", ApplicationType.MASS, LocalDate.of(2023, 1, 1), 23L, 321L);
        Resource testResource = new Resource(1L, "Косуля", 110, "Москва", LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 2));
        ApplicationResource testApplicationResource = new ApplicationResource(1L, testApplication, testResource, 110, ApplicationResourceStatus.NEW, LocalDate.of(2023, 1, 1));

        assert !applicationResourceService.checkApplication(testApplication, testResource, testApplicationResource);
    }
}
