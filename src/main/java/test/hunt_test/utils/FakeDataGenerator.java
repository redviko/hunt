package test.hunt_test.utils;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import test.hunt_test.dto.FakeStorageDTO;
import test.hunt_test.model.*;
import test.hunt_test.service.ApplicationResourceService;
import test.hunt_test.service.ApplicationService;
import test.hunt_test.service.ResourceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Scope("singleton")
public class FakeDataGenerator {
    private final Faker faker = new Faker();

    private final String[] resourceNames = {"Олень", "Медведь", "Косуля", "Заяц", "Лиса", "Волк", "Кабан", "Соболь", "Выдра", "Бобр", "Суслик", "Сурок", "Барсук", "Енот"};
    private final String[] resourceDistricts = {"Ханты-Мансийский АО", "Ямало-Ненецкий АО", "Тюменская область", "Красноярский край", "Иркутская область", "Томская область", "Новосибирская область", "Кемеровская область", "Алтайский край", "Республика Алтай", "Республика Тыва", "Республика Хакасия", "Курганская область", "Свердловская область", "Челябинская область", "Республика Башкортостан", "Оренбургская область", "Пермский край", "Республика Марий Эл", "Республика Мордовия", "Удмуртская Республика", "Чувашская Республика", "Республика Татарстан", "Самарская область", "Саратовская область", "Пензенская область", "Ульяновская область", "Республика Крым", "Республика Карелия", "Республика Коми", "Архангельская область", "Ненецкий автономный округ", "Вологодская область", "Калининградская область", "Ленинградская область", "Мурманская область", "Новгородская область", "Псковская область", "Республика Адыгея", "Республика Дагестан"};
    private final ApplicationResourceService applicationResourceService;
    private final ApplicationService applicationService;
    private final ResourceService resourceService;


    public FakeDataGenerator(ApplicationResourceService applicationResourceService, ApplicationService applicationService, ResourceService resourceService) {
        this.applicationResourceService = applicationResourceService;
        this.applicationService = applicationService;
        this.resourceService = resourceService;
    }

    private List<String> generateUniqueNames(int count) {
        List<String> existingNames = applicationService.getAllDistinctFullName();
        List<String> generatedNames = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = faker.name().fullName();
            if (!existingNames.contains(name)) {
                generatedNames.add(name);
            }
        }
        return generatedNames;
    }

    private List<Application> generateUniqueApplications(int count, int namesCount) {
        List<Application> applications = new ArrayList<>();
        List<String> names = generateUniqueNames(namesCount);
        for (int i = 0; i < count; i++) {
            Application application = new Application();
            application.setFullName(names.get(i % namesCount));
            application.setDateOfIssue(DateUtils.getRandomDate());
            application.setApplicationType(ApplicationType.values()[faker.number().numberBetween(0, 1)]);
            application.setHuntTicketNumber((long) faker.number().numberBetween(100000, 999999));
            application.setHuntTicketSeries((long) faker.number().numberBetween(1000, 9999));
            applications.add(application);
        }
        return applications;
    }


    private List<ApplicationResource> generateFakeApplicationResources(List<Resource> resources, List<Application> applications, int count) {
        List<ApplicationResource> applicationResources = new ArrayList<>();
//        List<Application> applications = applicationService.getAllDistinctByHuntTicketNumberAndHuntTicketSeries();
//        List<Resource> resources = resourceService.getAllDistinctResources();

        if (applications.size() == 0 || resources.size() == 0) {
            return applicationResources;
        }

        for (int i = 0; i < count; i++) {
            ApplicationResource applicationResource = new ApplicationResource();
            applicationResource.setApplication(applications.get(i % applications.size()));
            applicationResource.setResource(resources.get(i % resources.size()));
            applicationResource.setRequestedQuota(faker.number().numberBetween(1, 100));
            applicationResource.setStatus(ApplicationResourceStatus.NEW);
            applicationResources.add(applicationResource);
        }
        return applicationResources;
    }

    private List<Resource> generateUniqueResources(int count) {
        List<Resource> resources = new ArrayList<>();
        Random random = new Random();
        List<Resource> existingNames = resourceService.getAllDistinctResources();
        List<Resource> newResources = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Resource resource = new Resource();
            resource.setName(resourceNames[i % resourceNames.length]);
            resource.setQuota(faker.number().numberBetween(1, 100));
            resource.setDistrict(resourceDistricts[random.nextInt(resourceDistricts.length)]);
            if (!existingNames.contains(resource)) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public FakeStorageDTO generateFakeData(int applicationsCount, int namesCount, int resourcesCount, int applicationResourcesCount) {
        List<Application> applications = generateUniqueApplications(applicationsCount, namesCount);
        List<Resource> resources = generateUniqueResources(resourcesCount);
        List<ApplicationResource> applicationResources = generateFakeApplicationResources(resources, applications, applicationResourcesCount);
        return new FakeStorageDTO(applications, resources, applicationResources);
        //TODO: Отправлять в сервисы
//        applicationService.saveAll(applications);
//        resourceService.saveAll(resources);
//        applicationResourceService.saveAll(applicationResources);
    }


}

