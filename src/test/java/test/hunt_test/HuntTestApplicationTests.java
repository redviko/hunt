package test.hunt_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import test.hunt_test.dto.FakeStorageDTO;
import test.hunt_test.repository.ApplicationRepository;
import test.hunt_test.repository.ApplicationResourceRepository;
import test.hunt_test.repository.ResourceRepository;
import test.hunt_test.service.ApplicationResourceService;
import test.hunt_test.service.ApplicationService;
import test.hunt_test.service.ResourceService;
import test.hunt_test.utils.FakeDataGenerator;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class HuntTestApplicationTests {


    @Test
    void contextLoads() {
    }


}
