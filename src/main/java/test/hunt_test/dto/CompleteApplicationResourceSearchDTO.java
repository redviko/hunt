package test.hunt_test.dto;

import lombok.Getter;
import test.hunt_test.model.ApplicationResourceStatus;

@Getter
public class CompleteApplicationResourceSearchDTO {
    private Long applicationId;
    private final ApplicationResourceStatus status = ApplicationResourceStatus.APPROVED;

    public CompleteApplicationResourceSearchDTO() {
    }

    public CompleteApplicationResourceSearchDTO(Long applicationId) {
        this.applicationId = applicationId;
    }
}
