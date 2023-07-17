package test.hunt_test.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import test.hunt_test.exception.CustomError;
import test.hunt_test.model.Application;
import test.hunt_test.model.ApplicationResource;
import test.hunt_test.model.ApplicationResourceStatus;
import test.hunt_test.model.Resource;
import test.hunt_test.repository.ApplicationResourceRepository;
import test.hunt_test.utils.DistributionSync;

import java.util.List;

@Service
public class ApplicationResourceService {
    private final ApplicationResourceRepository applicationResourceRepository;

    public ApplicationResourceService(ApplicationResourceRepository applicationResourceRepository) {
        this.applicationResourceRepository = applicationResourceRepository;
    }

    public List<ApplicationResource> findAllByOrderByApplicationDateOfCreationAsc() {
        return applicationResourceRepository.findAllByOrderByApplicationDateOfCreationAsc();
    }

    public List<ApplicationResource> findAllByStatusAndApplication_Id(ApplicationResourceStatus status, Long applicationId) {
        return applicationResourceRepository.findAllByStatusAndApplication_Id(status, applicationId);
    }

    public List<String> getAllDistinctResources() {
        return applicationResourceRepository.getAllDistinctName();
    }

    public boolean checkApplicationDates(Resource resource, ApplicationResource applicationResource) {
        try {
            return (applicationResource.getCreatedAt().isAfter(resource.getStartDate()) || applicationResource.getCreatedAt().isEqual(resource.getStartDate())) &&
                    (applicationResource.getCreatedAt().isBefore(resource.getEndDate()) || applicationResource.getCreatedAt().isEqual(resource.getEndDate()));
        } catch (RuntimeException e) {
            throw new CustomError("Ошибка при проверке даты", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkApplicationQuotaWithDistrict(Application application, Resource resource, ApplicationResource applicationResource) {
        try {


            List<ApplicationResource> applicationResourceList = applicationResourceRepository.getAllApprovedByResourceId(resource.getName(), application.getId(), resource.getDistrict());
            if (applicationResourceList.isEmpty()) {
                int quota = resource.getQuota();
                int requestedQuota = applicationResource.getRequestedQuota();
                return requestedQuota <= quota;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw new CustomError("Ошибка при проверке квоты или района", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public boolean checkApplication(Application application, Resource resource, ApplicationResource applicationResource) {
        if (checkApplicationDates(resource, applicationResource) && checkApplicationQuotaWithDistrict(application, resource, applicationResource)) {
            applicationResource.setStatus(ApplicationResourceStatus.APPROVED);
            applicationResourceRepository.saveAndFlush(applicationResource);
            return true;
        } else {
            applicationResource.setStatus(ApplicationResourceStatus.REJECTED);
            applicationResourceRepository.saveAndFlush(applicationResource);
            return false;
        }
    }

    public void startDistribution() {
        try {


            DistributionSync distributionSync = DistributionSync.getInstance();

            if (distributionSync.isDistributing()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    return;
                }
                while (distributionSync.isDistributing()) {
                    try {


                        distributionSync = DistributionSync.getInstance();
                        List<ApplicationResource> applicationResourceList = distributionSync.getApplicationResources();
                        if (applicationResourceList.isEmpty()) {
                            distributionSync.stopDistributing();
                        } else {
                            ApplicationResource applicationResource = distributionSync.getOneApplicationResource();
                            Application application = applicationResource.getApplication();
                            Resource resource = applicationResource.getResource();
                            checkApplication(application, resource, applicationResource);

                        }
                    } catch (RuntimeException e) {
                        throw new CustomError("Ошибка при распределении", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            } else {
                List<ApplicationResource> applicationResourceList = findAllByOrderByApplicationDateOfCreationAsc();
                distributionSync.setApplicationResources(applicationResourceList);
                distributionSync.startDistributing();
                startDistribution();
            }
        } catch (RuntimeException e) {
            throw new CustomError("Ошибка при запуске распределения", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void stopDistribution() {
        DistributionSync distributionSync = DistributionSync.getInstance();
        distributionSync.stopDistributing();
    }

    public void saveAll(List<ApplicationResource> applicationResources) {
        try {
            applicationResourceRepository.saveAll(applicationResources);
        } catch (RuntimeException e) {
            throw new CustomError("Ошибка при сохранении ApplicationResources", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
