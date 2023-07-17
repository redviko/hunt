package test.hunt_test.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import test.hunt_test.model.Application;
import test.hunt_test.model.ApplicationResource;
import test.hunt_test.model.Resource;

import java.util.List;

@AllArgsConstructor
@Getter
public class FakeStorageDTO {
    private final List<Application> applications;
    private final List<Resource> resources;
    private final List<ApplicationResource> applicationResources;
}