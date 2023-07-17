package test.hunt_test.service;

import org.springframework.stereotype.Service;
import test.hunt_test.model.Resource;
import test.hunt_test.repository.ResourceRepository;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> getAllDistinctResources() {
        return resourceRepository.getAllDistinctResources();
    }

    public void saveAll(List<Resource> resources) {
        resourceRepository.saveAll(resources);
    }
}
