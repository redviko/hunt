package test.hunt_test.service;

import org.springframework.stereotype.Service;
import test.hunt_test.model.Application;
import test.hunt_test.repository.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getAllDistinctByHuntTicketNumberAndHuntTicketSeries() {
        return applicationRepository.getDistinctByHuntTicketNumberAndHuntTicketSeries();
    }

    public List<String> getAllDistinctFullName() {
        return applicationRepository.getDistinctFullName();
    }

    public void saveAll(List<Application> applications) {
        applicationRepository.saveAll(applications);
    }

    public void saveAndFlush(Application application) {
        applicationRepository.saveAndFlush(application);
    }
}
