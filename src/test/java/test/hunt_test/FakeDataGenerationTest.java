package test.hunt_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.hunt_test.dto.FakeStorageDTO;
import test.hunt_test.model.Application;
import test.hunt_test.utils.FakeDataGenerator;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FakeDataGenerationTest {
    @Autowired
    private FakeDataGenerator fakeDataGenerator;

    @Test
    public void testGenerateFakeData() {
        FakeStorageDTO fakeStorage = fakeDataGenerator.generateFakeData(100, 100, 100, 100);
        assert fakeStorage.getApplications().size() == 100;
        assert fakeStorage.getResources().size() == 100;
        assert fakeStorage.getApplicationResources().size() == 100;

    }


}
