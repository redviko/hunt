package test.hunt_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.hunt_test.model.Application;
import test.hunt_test.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/all-distinct")
    public ResponseEntity<List<Application>> getAllDistinctByHuntTicketNumberAndHuntTicketSeries() {
        try {
            return new ResponseEntity<>(applicationService.getAllDistinctByHuntTicketNumberAndHuntTicketSeries(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
