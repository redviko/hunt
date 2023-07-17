package test.hunt_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.hunt_test.model.ApplicationResource;
import test.hunt_test.service.ApplicationResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/application-resource")
public class ApplicationResourceController {
    private final ApplicationResourceService applicationResourceService;

    public ApplicationResourceController(ApplicationResourceService applicationResourceService) {
        this.applicationResourceService = applicationResourceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResource>> getAllApplicationResource() {
        return new ResponseEntity<>(applicationResourceService.findAllByOrderByApplicationDateOfCreationAsc(), HttpStatus.OK);
    }

    @GetMapping("/start-distribution")
    public ResponseEntity<String> startDistribution() {
        try {
            applicationResourceService.startDistribution();
            return ResponseEntity.ok("Распределение завершено успешно");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при распределении" + "\n" + e.getMessage());
        }
    }

    @GetMapping("/stop-distribution")
    public ResponseEntity<String> stopDistribution() {
        try {
            applicationResourceService.stopDistribution();
            return ResponseEntity.ok("Остановка распределения");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при остановке распределения");
        }
    }
}
